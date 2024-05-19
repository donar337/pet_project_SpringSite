<template>
    <div>
        <PostView v-for="post in posts" :post="post" :key="post.id" :short="true" :user="user"/>
    </div>
</template>

<script>
import PostView from "@/components/Utils/PostView.vue";

export default {
    name: "Index",
    components: {
        PostView: PostView,
    },
    props: ["posts", "user"]
}
</script>

<style scoped>

</style>