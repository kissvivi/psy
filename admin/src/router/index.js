import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

/* layout */
import Layout from '../views/layout/Layout'

const _import = require('./_import_' + process.env.NODE_ENV)

Vue.use(Router)

/**
 * icon : the icon show in the sidebar
 * hidden : if `hidden:true` will not show in the sidebar
 * redirect : if `redirect:noRedirect` will not redirect in the levelBar
 * noDropDown : if `noDropDown:true` will not has submenu in the sidebar
 * meta : `{ permission: ['a:xx'] }`  will control the page permission
 **/
export const constantRouterMap = [
  { path: '/index', component: _import('index'), hidden: true },
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  // { path: '/toChat/:id', component: _import('chat/chat2'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    icon: 'dashboard',
    noDropDown: true,
    children: [{
      path: 'dashboard',
      name: '控制台',
      component: _import('dashboard/index'),
      meta: { title: 'dashboard', noCache: true }
    }]
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/account',
    component: Layout,
    redirect: '/account/list',
    icon: 'name',
    noDropDown: true,
    children: [{
      path: 'list',
      name: '账户管理',
      component: _import('account/list'),
      meta: { permission: ['account:list'] }
    }]
  },

  {
    path: '/account',
    component: Layout,
    redirect: '/account/detail',
    hidden: true,
    children: [{
      path: 'detail',
      name: '账户中心',
      component: _import('account/detail')
    }]
  },
  {
    path: '/toChat/:consultId/:sid',
    component: Layout,
    redirect: '/toChat/:consultId/:sid',
    hidden: true,
    children: [{
      path: '/toChat/:consultId/:sid',
      name: '咨询界面',
      component: _import('chat/chat2')
    }]
  },

  {
    path: '/role',
    component: Layout,
    redirect: '/role/list',
    icon: 'role',
    noDropDown: true,
    children: [{
      path: 'list',
      name: '角色管理',
      component: _import('role/list'),
      meta: { permission: ['role:list'] }
    }]
  },
  {
    path: '/student',
    component: Layout,
    redirect: '/student/list',
    icon: 'name',
    noDropDown: true,
    children: [{
      path: 'list',
      name: '学生管理',
      component: _import('student/list'),
      meta: { permission: ['student:list'] }
    }]
  },
  {
    path: '/doctor',
    component: Layout,
    redirect: '/doctor/list',
    icon: 'name',
    noDropDown: true,
    children: [{
      path: 'list',
      name: '医生（教师）管理',
      component: _import('chat/list'),
      meta: { permission: ['doctor:list'] }
    }]
  },
  {
    path: '/chat',
    component: Layout,
    redirect: '/chat/list',
    icon: 'name',
    noDropDown: true,
    children: [{
      path: 'list',
      name: '咨询管理',
      component: _import('chat/list'),
      meta: { permission: ['account:list'] }
    }]
  },
  {
    path: '/tests',
    component: Layout,
    redirect: '/tests/list',
    icon: 'name',
    noDropDown: true,
    children: [{
      path: 'list',
      name: '问卷管理',
      component: _import('tests/list'),
      meta: { permission: ['account:list'] }
    }]
  }
]
