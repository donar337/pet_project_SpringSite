<template>
    <div class="comments">
        <section>
            <div class="header">
                Comments
            </div>
            <div class="body">
                <div class="form-1" v-if="user">
                    <div class="header">Write Comment</div>
                    <div class="body">
                        <form @submit.prevent="onWriteComment">
                            <div class="field">
                                <div class="name">
                                    <label for="text">Text:</label>
                                </div>
                                <div class="value">
                                    <textarea id="text" name="text" v-model="text"></textarea>
                                </div>
                            </div>
                            <div class="error">{{ error }}</div>
                            <div class="button-field">
                                <input type="submit" value="post">
                            </div>
                        </form>
                    </div>
                </div>
                <ul>
                    <li v-for="comment in comments" :key="comment.id">
                        <div class="comment">
                            <div class="information">By {{ comment.user.login }}, at {{ timeToString(comment.creationTime) }}</div>
                            <div v-for="line in comment.text.split('\n')" :key="line.key">
                                {{ line }}
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
    </div>
</template>

<script>

import axios from "axios";

export default {
    name: "Comments",
    data: function () {
        return {
            comments: [],
            text: "",
            error: ""
        }
    },
    methods: {
        onWriteComment: function () {
            this.error = ""
            this.$root.$emit("onWriteComment", this.text, this.postId)
        },
        timeToString: function (time) {
            return time.substr(0, 10) + ' ' + time.substr(11, 8)
        }
    },
    beforeMount() {
        axios.get("/api/1/comment/by/post", {
            params: {
                "postId": this.postId
            }
        }).then(response => {
            this.comments = response.data;
        });

        this.$root.$on("onWriteCommentValidationError", error => {
            this.error = error
            if (!error) {
                this.text = ""
                this.error = ""
                axios.get("/api/1/comment/by/post", {
                    params: {
                        "postId": this.postId
                    }
                }).then(response => {
                    this.comments = response.data;
                });
            }
        });
    },
    props: ["postId", "user"]
}
</script>

<style scoped>
section {
    margin-bottom: 1rem;
}
</style>