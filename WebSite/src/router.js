import { createRouter, createWebHistory } from 'vue-router'
import HomeContent from './pages/HomeContent.vue';
import HowToPlay from './pages/HowToPlay.vue';
import AboutDevelopers from './pages/AboutDevelopers.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/SiteHome' },
        { name: 'HomeContet', path: '/SiteHome', component: HomeContent },
        { name: 'HowToPlay', path: '/HowToPlay', component: HowToPlay },
        { name: 'AboutDevelopers', path: '/AboutDevelopers', component: AboutDevelopers }
    ],
    linkActiveClass: 'example-active',
    linkExactActiveClass: 'example-exact-active',
});

export default router;