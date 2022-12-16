<template>
  <div class="DevelopersContent">
    <p id="Title">かいはつしゃ</p>
    <div class="DeveloperList">
      <div class="Developer">
        <img src="@/assets/images/funeral.png" alt="ふねらる">
        <p class="name">ふねらる</p>
        <p class="position">バックエンドエンジニア</p>
        <p>主に使用した技術 : Go</p>
      </div>
      <div class="Developer">
        <img src="@/assets/images/daimu.png" alt="だいむ">
        <p class="name">だいむ</p>
        <p class="position">Androidエンジニア</p>
        <p>主に使用した技術 : Kotlin</p>
      </div>
    </div>
    <div class="DeveloperList">
      <div class="Developer">
        <img src="@/assets/images/akicth.png" alt="あきっち">
        <p class="name">あきっち</p>
        <p class="position">デザイナー / Webエンジニア</p>
        <p>主に使用した技術 : Vue</p>
      </div>
      <div class="Developer">
        <img src="@/assets/images/ayumu.png" alt="あゆむ">
        <p class="name">あゆむ</p>
        <p class="position">Androidエンジニア</p>
        <p>主に使用した技術 : Kotlin</p>
      </div>
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
.DevelopersContent {
  margin: 0 0 0 400px;
  padding: 70px 0 0 0;
  overflow: hidden;
}

#Title {
  text-align: left;
  font-size: 40px;
  font-weight: bold;
  margin: 20px 0;
}

.DeveloperList {
  display: flex;
  margin: 100px 0 50px 0;
}

.Developer {
  margin: 0;
  width: 300px;
}

.Developer>img {
  width: 300px;
  border-radius: 200px;
  border: 1px solid #b8b8b8;
  filter: drop-shadow(-2px 2px 3px #666666);
}

.Developer:nth-child(1) {
  margin: 0px 100px;
}

.Developer:nth-child(2) {
  margin: 0px 100px;
}

p {
  text-align: center;
  white-space: nowrap;
}

.name {
  font-size: 30px;
  font-weight: bold;
  margin: 50px 0 0 0;
}

.position {
  margin: 0;
  font-size: 20px;
  font-weight: bold;
}

@media screen and (max-width: 500px) {

  .DevelopersContent {
    margin: 0;
    padding: 100px 0 0 0;
    overflow: hidden;
  }

  #Title {
    text-align: left;
    font-size: 8vmin;
    font-weight: bold;
    margin: 20px 0 0 20px;
  }

  .DeveloperList {
    /* display: flex; */
    display:block;
    margin: 100px 0 50px 0;
  }

  .Developer>img {
    display:inline-block;
    width: 60vmin;
    border-radius: 200px;
    border: 1px solid #b8b8b8;
    filter: drop-shadow(-2px 2px 3px #666666);
  }

  .Developer:nth-child(n) {
    width: 80vmin;
    text-align: center;
    margin: 0px 10vmin 60px 10vmin;
  }

  p {
    white-space: nowrap;
  }

  .name {
    font-size: 30px;
    font-weight: bold;
    margin: 20px 0 0 0;
  }

  .position {
    margin: 0;
    font-size: 20px;
    font-weight: bold;
  }

}
</style>
