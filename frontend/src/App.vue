<template>
    <div id="app">
        <Header :user="user" :page="page"/>
        <Middle :user="user" :posts="posts" :page="page" :parameters="parameters"/>
        <Footer/>
        <notifications position="bottom right"/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return {
            user: null,
            posts: [],
            page: "Index",
            parameters: {}
        }
    },
    beforeMount() {
        if (localStorage.getItem("jwt") && !this.user) {
            this.$root.$emit("onJwt", localStorage.getItem("jwt"));
        }

        axios.get("/api/1/posts").then(response => {
            this.posts = response.data;
        });
    },
    beforeCreate() {
        this.$root.$on("onChangePage", (page) => {
            this.page = page;
            if (['Index'].includes(page)) {
                axios.get("/api/1/posts").then(response => {
                    this.posts = response.data;
                });
            }
        });

        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/jwt", {
                    login, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onJwt", response.data);
                this.$notify({
                    type: 'success',
                    title: 'Welcome to codeforces!',
                    text: 'You have successfully enter'
                });
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            });
        });

        this.$root.$on("onRegister", (login, name, password) => {
            if (login === "") {
                this.$root.$emit("onRegisterValidationError", "Login is required");
                return;
            } else if (name === "") {
                this.$root.$emit("onRegisterValidationError", "Name is required");
                return;
            } else if (password === "") {
                this.$root.$emit("onRegisterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/register", {
                login, name, password
            }).then(() => {
                axios.post("/api/1/jwt", {
                    login, password
                }).then(response => {
                    localStorage.setItem("jwt", response.data);
                    this.$root.$emit("onJwt", response.data);
                })
                this.$notify({
                    type: 'success',
                    title: 'Welcome to codeforces!',
                    text: 'You have successfully register'
                });
                this.$root.$emit("onChangePage", "Index")
            }).catch(error => {
                this.$root.$emit("onRegisterValidationError", error.response.data);
            });
        });

        this.$root.$on("onJwt", (jwt) => {
            localStorage.setItem("jwt", jwt);

            axios.get("/api/1/users/auth", {
                params: {
                    jwt
                }
            }).then(response => {
                this.user = response.data;
                this.$root.$emit("onChangePage", "Index");
            }).catch(() => this.$root.$emit("onLogout"))
        });

        this.$root.$on("onLogout", () => {
            localStorage.removeItem("jwt");
            this.$notify({
                title: 'Goodbye!',
                text: 'You have been successfully logout'
            });
            this.user = null;
        });

        this.$root.$on("onWritePost", (title, text) => {
            if (!title.trim()) {
                this.$root.$emit("onWritePostValidationError", "Title is required")
                return
            }
            if (!text.trim()) {
                this.$root.$emit("onWritePostValidationError", "Text is required")
                return;
            }

            axios.post("/api/1/posts/write", {
                title, text, "user": this.user
            }).then(() => {
                this.$notify({
                    type: 'success',
                    title: 'Something go right!',
                    text: 'Your post have been successfully published'
                });
                this.$root.$emit("onChangePage", "Index")
            }).catch(error => {
                this.$root.$emit("onWritePostValidationError", error.response.data);
            });
        });

        // this.$root.$on("onChangePostScore", (up, postId) => {
        //     this.$notify("sending")
        //     axios.put("api/1/posts/change/score", {up, postId})
        // })

        this.$root.$on("onChangePageWithParameters", (page, parameters) => {
            this.page = page;
            this.parameters = parameters;
        });

        this.$root.$on("onWriteComment", (text, postId) => {
            if (!text.trim()) {
                this.$root.$emit("onWriteCommentValidationError", "Text is required")
                return;
            } else if (text.length > 10000) {
                this.$root.$emit("onWriteCommentValidationError", "Text is too large")
                return;
            }

            axios.post("/api/1/comment/write", {
                text, postId, "userId": this.user.id
            }).then(() => {
                this.$notify({
                    type: 'success',
                    title: 'Something go right!',
                    text: 'Your comment have been successfully published'
                });
                this.$root.$emit("onWriteCommentValidationError")
            }).catch(error => {
                this.$root.$emit("onWriteCommentValidationError", error.response.data);
            });
        });
    }
}
</script>

<style>
#app {

}
</style>
