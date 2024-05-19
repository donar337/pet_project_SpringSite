<template>
    <div>
        <PostView v-for="post in this.posts" :post="post" :key="post.id" :short="true" :user="user"/>
    </div>
</template>

<script>
import PostView from "@/components/Utils/PostView.vue";
import axios from "axios";

export default {
    name: "MyPosts",
    components: {
        PostView: PostView,
    },
    props: ["user"],
    data: function () {
        return {
            posts: []
        }
    },
    beforeMount() {
        axios.get("/api/1/posts/by/user", {
            params: {
                "userId": this.user.id
            }
        }).then(response => {
            this.posts = response.data;
        });
    }
}
</script>

<style scoped>

</style>