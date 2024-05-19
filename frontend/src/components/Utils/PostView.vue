<template>
    <div>
        <article>
            <template v-if="!short">
                <div class="title">{{ post.title }}</div>
            </template>
            <template v-else>
                <div class="title title-link" @click.prevent="showAll()">{{ post.title }}</div>
            </template>
            <div class="information">By {{ post.user.login }}, {{ timeToString(post.creationTime) }}, translation</div>
            <div class="body">
                <template v-if="post.text.length > 300 && short">
                    {{ post.text.substr(0, 300) }}<a href="#">...</a>
                </template>
                <template v-else>
                    {{ post.text }}
                </template>
            </div>
            <ul class="attachment">
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up" @click.prevent="upVote"/>
                    <span :class="{'positive-score' : post.score + diff >= 0, 'negative-score' : post.score + diff < 0}"> {{ post.score + diff}} </span>
                    <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down" @click.prevent="downVote"/>
                </div>
                <div class="right">
                    <img src="../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    {{ timeToString(post.creationTime) }}
                    <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#" @click.prevent="showAll">{{ post.commentCount }}</a>
                </div>
            </div>
        </article>
        <Comments :postId="post.id" :user="user" v-if="!short"/>
    </div>
</template>

<script>
import Comments from "@/components/Utils/Comments.vue";

export default {
    name: "PostView",
    components: {
        Comments
    },
    props: ["post", "short", "user"],
    data: function () {
        return {
            diff: 0
        }
    },
    methods: {
        upVote: function () {
            if (!this.user) {
                this.$notify({
                    type: 'error',
                    title: 'First login',
                    text: 'You should be authorized to upvote posts'
                });
            } else if (this.user.id === this.post.user.id) {
                this.$notify({
                    type: 'error',
                    title: 'Denied :)',
                    text: 'You can`t upvote your own posts'
                });
            } else {
                // this.$root.$emit("onChangePostScore", true, this.post.id)
                this.diff++
            }
        },
        downVote: function () {
            if (!this.user) {
                this.$notify({
                    type: 'error',
                    title: 'First login',
                    text: 'You should be authorized to downvote posts'
                });
            } else if (this.user.id === this.post.user.id) {
                this.$notify({
                    type: 'error',
                    title: 'Denied :(',
                    text: 'You can`t downvote your own posts'
                });
            } else {
                // this.$root.$emit("onChangePostScore", false, this.post.id)
                this.diff--
            }
        },
        timeToString: function (time) {
            return time.substr(0, 10) + ' ' + time.substr(11, 8)
        },
        showAll: function () {
            this.$root.$emit("onChangePageWithParameters", "Post", {"post": this.post})
        }
    }
}
</script>

<style scoped>
section {
    margin-bottom: 1rem;
}
</style>