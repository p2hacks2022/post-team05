<template>
    <div class="footer">
        <p>2022 p2Hacks@HickyKomori</p>
    </div>
</template>

<script>
export default {
    name: 'SiteFooter',
    props: {
    }
}
</script>


<style scoped>
.footer {
    overflow:hidden;
    display: inline-block;
    position: absolute;
    width: 100vw;
    height: 50px;
    background: #333333;
}

p {
    text-align: center;
    font-size: 16px;
    color: #fff;
}

@media screen and (max-width: 500px) {
    .footer {
        display: inline-block;
        position: absolute;
        width: 100vw;
        height: 50px;
        background: #333333;
    }

    p {
        text-align: center;
        font-size: 16px;
        color: #fff;
    }
}
</style>