<template>
    <header>
        <a href="#" @click.prevent="changePage('Index')">
            <img class="logo" src="../assets/img/logo.png" alt="Codeforces" title="Codeforces"/>
        </a>
        <div class="languages">
            <a href="#"><img class="flag" src="../assets/img/gb.png" alt="In English" title="In English"/></a>
            <a href="#"><img class="flag" src="../assets/img/ru.png" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <template v-if="user">
                {{ user.login }} (<span class="name">{{ user.name }}</span>)
                |
                <a href="#" @click.prevent="onLogout">Logout</a>
            </template>
            <template v-else>
                <a href="#" @click.prevent="changePage('Enter')">Enter</a>
                |
                <a href="#" @click.prevent="changePage('Register')">Register</a>
            </template>
        </div>
        <nav>
            <ul>
                <li><a href="#" @click.prevent="changePage('Index')" :class="{ active : page === 'Index' }">Home</a></li>
                <li><a href="#" @click.prevent="changePage('Users')" :class="{ active : page === 'Users' }">Users</a></li>
                <li><a href="#" @click.prevent="changePage('WritePost')" :class="{ active : page === 'WritePost' }" v-if="user">Write Post</a></li>
                <li><a href="#" @click.prevent="changePage('MyPosts')" :class="{ active : page === 'MyPosts' }" v-if="user">My Posts</a></li>
            </ul>
        </nav>
    </header>

</template>

<script>
export default {
    name: "Header",
    props: ["user", "page"],
    methods: {
        changePage: function (page) {
            this.$root.$emit("onChangePage", page);
        },
        onLogout: function () {
            this.$root.$emit("onLogout");
            this.changePage("Index");
        }
    }
}
</script>

<style scoped>
  .flag {
    margin-left: 0.3rem;
  }
  .logo {
    max-width: 15rem;
  }
</style>
