import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '*',
      redirect: '/'
    }, {
      path: '/',
      name: 'PageView',
      component: resolve => require(['@/view/PageView'], resolve),
      children: [
        {
          path: '/',
          redirect: '/home'
        }, 
        {
          path: '/login',
          name: 'login',
          component: resolve => require(['@/view/Login'], resolve),
          meta: {
            title: '登录'
          }
        },
        {
          path: '/home',
          name: 'home',
          component: resolve => require(['@/view/HomePage'], resolve),
          meta: {
            title: '首页'
          }
        }, {
          path: '/self',
          name: 'self',
          component: resolve => require(['@/view/Self'], resolve),
          meta: {
            title: '个人中心'
          },
          children: [
            {
              path: '/self',
              redirect: '/self/selfInfo'
            },
            {
              path: '/self/selfInfo',
              name: 'self',
              component: resolve => require(['@/view/SelfInfo'], resolve),
              meta: {
                title: '个人中心丨个人介绍'
              }
            },
            {
              path: '/self/selfConsult',
              name: 'selfConsult',
              component: resolve => require(['@/view/SelfConsult'], resolve),
              meta: {
                title: '个人中心丨咨询预约'
              }
            }
          ]
        }, {
          path: '/service',
          name: 'service',
          component: resolve => require(['@/view/Service'], resolve),
          meta: {
            title: '相关服务'
          }
        }, {
          path: '/newsinformation',
          name: 'newsinformation',
          component: resolve => require(['@/view/NewsInformation'], resolve),
          meta: {
            title: '新闻动态'
          }
        }, {
          path: '/companyintroduction',
          name: 'companyintroduction',
          component: resolve => require(['@/view/CompanyIntroduction'], resolve),
          meta: {
            title: '公司介绍'
          }
        }, {
          path: '/jobchance',
          name: 'jobchance',
          component: resolve => require(['@/view/JobChance'], resolve),
          meta: {
            title: '工作机会'
          }
        }, {
          path: '/contactus',
          name: 'contactus',
          component: resolve => require(['@/view/ContactUs'], resolve),
          meta: {
            title: '联系我们'
          }
        },
        {
          path: '/servicedetail',
          name: 'servicedetail',
          component: resolve => require(['@/view/ServiceDetail'],resolve),
          meta: {
            title: '相关服务'
          }
        },
        {
          path: '/toChat/:consultId/:did',
          name: 'chat',
          component: resolve => require(['@/view/Chat'],resolve),
          meta: {
            title: '聊天界面'
          }
        },
        {
          path: '/chat2',
          name: 'chat2',
          component: resolve => require(['@/view/Chat2'],resolve),
          meta: {
            title: '聊天界面2'
          }
        }
      ]
    }
  ]
})
