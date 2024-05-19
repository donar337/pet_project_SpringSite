<template>
    <div>
      <PostView :post="post" :user="user" :short="false"/>
    </div>
</template>

<script>
import PostView from "@/components/Utils/PostView.vue";

export default {
    name: "Post",
    components: {
      PostView: PostView,
    },
    props: ["post", "user"],

}
</script>

<style scoped>

</style>
