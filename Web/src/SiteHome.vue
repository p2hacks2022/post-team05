<template>
  <SiteSidebar />
  <router-view></router-view>
  <SiteFooter />
</template>

<script>
import SiteSidebar from './components/Sidebar.vue'
import SiteFooter from './components/Footer.vue'


export default {
  name: 'SiteHome',
  components: {
    SiteSidebar,
    SiteFooter
  }
}
</script>

<style>
#home {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #474747;
  position: absolute;
  z-index: 100;
}
</style>
