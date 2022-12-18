<template>
  <div class="HowToPlayContent">
    <p id="Title">あそびかた</p>
    <div class="stepContainer">
      <p class="stepText">STEP01</p>
      <img src="@/assets/images/people.svg" alt="遊び" class="playImage">
      <p class="stepIntrText">人を集めよう。</p>
    </div>
    <div class="stepContainer">
      <p class="stepText">STEP02</p>
      <img src="@/assets/images/step02.png" alt="タイトル画面" class="playImage">
      <p class="stepIntrText">スマホを起動しよう。</p>
    </div>
    <div class="stepContainer">
      <p class="stepText">STEP03</p>
      <img src="@/assets/images/step03.png" alt="部屋を作る" class="playImage">
      <p class="stepIntrText">部屋を作ろう。</p>
    </div>
    <div class="stepContainer">
      <p class="stepText">STEP04</p>
      <img src="@/assets/images/step04.png" alt="ゲーム開始" class="playImage">
      <p class="stepIntrText">ゲーム開始だ。<br>鬼と隠れる側で戦おう。</p>
    </div>
    <div class="stepContainer">
      <p class="stepText">STEP05</p>
      <img src="@/assets/images/run.svg" alt="走る" class="playImage">
      <p class="stepIntrText">相対時間で画面の見え方が変わるよ。<br>多彩な戦術が可能に。</p>
    </div>
    <div class="stepContainer">
      <p class="stepText">STEP06</p>
      <img src="@/assets/images/step06.png" alt="罠" class="playImage">
      <p class="stepIntrText">罠に気を付けよう。<br>視界が奪われるぞ。</p>
    </div>
    <div class="stepContainer">
      <p class="stepText">STEP07</p>
      <img src="@/assets/images/step07.png" alt="鬼に捕まる様子" class="playImage">
      <p class="stepIntrText">鬼から逃げよう。</p>
    </div>
    <div class="stepContainer">
      <p class="stepText">STEP08</p>
      <img src="@/assets/images/step08.png" alt="クリア" class="playImage">
      <p class="stepIntrText">クリアを目指そう。</p>
    </div>
  </div>
</template>

<script>

export default {
  name: 'HowToPlay',
  components: {
  }
}
</script>

<style>
.HowToPlayContent {
  margin: 0 0 0 400px;
  padding: 70px 0 100px 0;
  overflow: hidden;
}

#Title {
  text-align: left;
  font-size: 40px;
  font-weight: bold;

}

.stepContainer {
  margin: 0 0 0 40px;
}

.stepText {
  text-align: left;
  font-size: 30px;
  font-weight: bold;
  margin: 60px 0 10px 40px;
}

.playImage {
  position: relative;
  width: 250px;
  margin: 0 0 5px 40px;
  filter: drop-shadow(-2px 2px 3px #666666);
}

.stepIntrText {
  position: relative;
  margin: 0 0 0 40px;
  text-align: left;
  font-size: 20px;
  font-weight: bold;
}

@media screen and (max-width: 500px) {
  .HowToPlayContent {
  margin: 0;
  padding: 100px 0 100px 0;
  overflow: hidden;
}

#Title {
  text-align: left;
  font-size: 40px;
  font-weight: bold;

}

.stepContainer {
  margin: 0 0 0 40px;
}

.stepText {
  text-align: left;
  font-size: 7vmin;
  font-weight: bold;
  margin: 60px 0 10px 40px;
}

.playImage {
  position: relative;
  width: 60vmin;
  margin: 0 0 5px 10vmin;
  filter: drop-shadow(-2px 2px 3px #666666);
}

.stepIntrText {
  position: relative;
  margin: 0 0 0 10vmin;
  text-align: left;
  font-size: 4vmin;
  font-weight: bold;
}
}
</style>
