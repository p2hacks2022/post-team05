<template>
    <div class="HomeMainContent">
        <p id="catchCopy">時を超えて、隠れろ</p>
        <div class="firstView">
            <img src="@/assets/images/SP_mockup02.png" alt="モックアップ" id="mainMockup">
            <div class="firstView_align_subMocks">
                <div class="alignImages">
                    <img src="@/assets/images/main_catchFlag.png" alt="サブのモックアップ">
                    <img src="@/assets/images/main_catchNotification.png" alt="サブのモックアップ">
                    <img src="@/assets/images/main_timeUp.png" alt="サブのモックアップ">
                </div>
                <div class="alignImages">
                    <img src="@/assets/images/main_clear.png" alt="サブのモックアップ">
                    <img src="@/assets/images/main_result.png" alt="サブのモックアップ">
                    <img src="@/assets/images/main_skill_attacked.png" alt="サブのモックアップ">
                </div>
                <div class="alignImages">
                    <img src="@/assets/images/main_viewMode.png" alt="サブのモックアップ">
                    <img src="@/assets/images/main.png" alt="サブのモックアップ">
                    <img src="@/assets/images/title.png" alt="サブのモックアップ">
                </div>
            </div>
        </div>
        <div class="PV">
            <p>PV</p>
            <iframe width="560" height="315" src="https://www.youtube.com/embed/Y4kvGNOtfuo" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
        </div>
        <router-link to="/HowToPlay">
            <div class="intrDiffpage">
                <p>遊び方はここから</p>
                <img src="@/assets/images/play.svg" alt="遊び方">
            </div>
        </router-link>
        <router-link to="/AboutDevelopers">
            <div class="intrDiffpage">
                <p>開発者のことはここから</p>
                <img src="@/assets/images/teams.svg" alt="遊び方">
            </div>
        </router-link>
    </div>
</template>

<script>

export default {
    name: 'HomeContent',
    props: {
    }
}
</script>


<style scoped>
.HomeMainContent {
    margin: 0 0 0 400px;
    overflow: hidden;
}

#catchCopy {
    display: block;
    width: 370px;
    font-size: 40px;
    font-weight: bold;
    margin: 0 auto 0 65px;
    padding: 195px 0 0 0;
}

.firstView {
    display: flex;
    height: 1000px;
}

.firstView_align_subMocks {
    position: relative;
    white-space: nowrap;
    top: -380px;
}

#mainMockup {
    width: 485px;
    height: 495px;
    margin: 0 100px 0 0;
    filter: drop-shadow(-2px 2px 3px #666666);
}

.alignImages:nth-child(2) {
    margin: 0 0 0 100px;
}

.alignImages:nth-child(3) {
    margin: 0 0 0 200px;
}

.alignImages>img {
    width: 180px;
    margin: 0 40px 20px 0;
    filter: drop-shadow(-2px 2px 3px #666666);
    transition: all 0.3s;
}

.alignImages>img:hover {
    scale: 1.5;
    transition: all 0.3s;
}

.PV {
    position: relative;
    width: 560px;
    margin: 0 auto 180px auto;
    right: 2vw;
}

.PV>p {
    font-size: 40px;
    font-weight: bold;
    margin: 20px 0;
}

.PV>iframe {
    filter: drop-shadow(-2px 2px 3px #666666);
}

.intrDiffpage {
    position: relative;
    width: 560px;
    margin: 0 auto 180px auto;
    right: 2vw;
    cursor: pointer;
}

.intrDiffpage>p {
    font-size: 40px;
    font-weight: bold;
    margin: 20px 0;
}

a {
    text-decoration: none;
    color: #474747;
}


@media screen and (max-width: 500px) {
    .HomeMainContent {
        width: 100vw;
        margin: 0;
        overflow: hidden;
    }

    #catchCopy {
        display: block;
        width: 100vw;
        font-size: 8vmin;
        font-weight: bold;
        margin: 0 0 0 2vmin;
        padding: 195px 0 0 0;
    }

    .firstView {
        display: block;
        height: 1300px;
    }

    .firstView_align_subMocks {
        position: relative;
        white-space: nowrap;
        top: 0px;
    }

    #mainMockup {
        display: inline-block;
        text-align: center;
        margin: 0 0 20px 8vmin;
        width: 80vmin;
        height: 85vmin;
        filter: drop-shadow(-2px 2px 3px #666666);
    }

    .alignImages:nth-child(n) {
        margin: 0 5vmin;
    }

    .alignImages>img {
        width: 26vmin;
        margin: 0 2vmin 20px 2vmin;
        filter: drop-shadow(-2px 2px 3px #666666);
        transition: all 0.3s;
    }

    .alignImages>img:hover {
        scale: 1.5;
        transition: all 0.3s;
    }

    .PV {
        position: relative;
        width: 100vmin;
        margin: 0 auto 180px auto;
    }

    .PV>p {
        font-size: 6vmin;
        font-weight: bold;
        margin: 20px 0;
    }

    .PV>iframe{
        position: relative;
        width: 80vmin;
        margin: 0 11.5vmin;
        filter: drop-shadow(-2px 2px 3px #666666);
    }

    .intrDiffpage {
        position: relative;
        width: 100vmin;
        margin: 0 auto 180px auto;
        right: 2vw;
        cursor: pointer;
    }

    .intrDiffpage>p {
        font-size: 6vmin;
        font-weight: bold;
        margin: 20px 0;
    }

    .intrDiffpage>img {
        position: relative;
        width: 80vmin;
        margin: 0 11.5vmin;
    }

    a {
        text-decoration: none;
        color: #474747;
    }
}
</style>
