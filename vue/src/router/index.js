import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
	mode: "history", // history 模式去除地址栏 # 号
	routes: [
        {
            path: '/',
            name: 'login',
            component: () => import("@/components/login.vue"),
            meta: { requireAuth: false },
          },
          {
            path: '/chat',
            name: 'chat',
            component: () => import("@/components/chat.vue"),
            meta: { requireAuth: false },
          },
		
	],
});
