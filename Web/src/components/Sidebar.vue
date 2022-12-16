<template>
    <div class="sideBar">
        <router-link to="/SiteHome">
            <div class="sideBar_transPages">
                <img src="@/assets/images/home.svg" alt="ホーム">
                <li>ほぉ～む</li>
            </div>
        </router-link>
        <router-link to="/HowToPlay">
            <div class="sideBar_transPages">
                <img src="@/assets/images/user.svg" alt="ユーザー">
                <li>あそびかた</li>
            </div>
        </router-link>
        <router-link to="/AboutDevelopers">
            <div class="sideBar_transPages">
                <img src="@/assets/images/gear.svg" alt="ギア">
                <li>かいはつしゃ</li>
            </div>
        </router-link>
    </div>
</template>
<script>
export default {
    name: 'SiteSidebar',
    props: {
    }
}
</script>


<style scoped>
.sideBar {
    display: block;
    position: fixed;
    width: 300px;
    height: 100vh;
    filter: drop-shadow(1px 0px 2px #d4d4d4);
    background: #ffffff;
    z-index:2000;
}

.sideBar_transPages {
    display: flex;
    margin: 40px 50px;
    padding: 8px 10px;
    cursor: pointer;
    border-radius: 20px;
    background: #ffffff;
    transition: 0.3s;
}

.sideBar_transPages:hover {
    filter: drop-shadow(4px 4px 3px #d4d4d4);
    transition: 0.3s;
}

li {
    display: inline-block;
    margin: 2px 10px 0px 10px;
}

a {
    text-decoration: none;
    color: #474747;
}

@media screen and (max-width: 500px) {
    .sideBar {
        display: block;
        position: fixed;
        width: 100vw;
        height: 80px;
        filter: drop-shadow(1px 0px 2px #d4d4d4);
        background: #ffffff;
        z-index: 1000;
    }

    .sideBar_transPages {
        text-align: center;
        display: inline-block;
        width: 33vw;
        margin: 0px;
        padding: 22px 0px 10px 0px;
        cursor: pointer;
        border-radius: 20px;
        background: #ffffff;
        transition: 0.3s;
    }

    .sideBar_transPages:hover {
        filter: drop-shadow(4px 4px 3px #d4d4d4);
        transition: 0.3s;
    }

    .sideBar_transPages>img {
        display: inline-block;
        width: 40px;
    }

    li {
        display:none;
    }

    a {
        text-decoration: none;
        color: #474747;
    }


}
</style>