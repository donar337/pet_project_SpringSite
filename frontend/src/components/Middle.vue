<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts" :user="user"/>
            <Users v-if="page === 'Users'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <MyPosts v-if="page === 'MyPosts'" :user="user"/>
            <Post v-if="page === 'Post'" :user="user" :post="parameters['post']"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "@/components/main/Users.vue";
import WritePost from "@/components/main/WritePost.vue";
import MyPosts from "@/components/main/MyPosts.vue";
import Post from "@/components/main/Post.vue";

export default {
    name: "Middle",
    components: {
        Register,
        Enter,
        Index,
        Sidebar,
        Users,
        WritePost,
        MyPosts,
        Post
    },
    props: ["posts", "users", "page", "user", "parameters"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }
}
</script>

<style scoped>

</style>
