import { createApp } from 'vue'
import SiteHome from './SiteHome.vue'
import router from './router.js' // ルーターの設定を追加'

const app=createApp(SiteHome);
app.use(router);
app.mount('#home');