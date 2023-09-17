import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/example',
    component: Layout,
    redirect: '/example',
    name: 'Example',
    meta: { title: '收银员操作', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'vip',
        name: 'Vip',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '商品收银', icon: 'el-icon-shopping-cart-1' }
      },
      {
        path: 'vip1',
        name: 'Vip1',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '订单查看', icon: 'el-icon-menu' }
      },
      {
        path: 'vip2',
        name: 'Vip2',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '退货', icon: 'el-icon-shopping-cart-2' }
      },      
      {
        path: 'vip3',
        name: 'Vip3',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '挂单管理', icon: 'el-icon-bank-card' }
      },
      {
        path: 'vip4',
        name: 'Vip4',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '热键管理', icon: 'el-icon-finished' }
      },
    ]
  }, 
  {
    path: '/example2',
    component: Layout,
    redirect: '/example2',
    name: 'Example2',
    meta: { title: '系统管理员操作', icon: 'el-icon-s-custom' },
    children: [

      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '商品信息管理', icon: 'el-icon-film' }
      },
      {
        path: 'user1',
        name: 'User1',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '商品分类管理', icon: 'el-icon-files' }
      },
     
      {
        path: 'user8',
        name: 'User8',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '用户管理', icon: 'el-icon-setting' },
        children:[          
        {
          path: 'user9',
          name: 'User9',
          component: () => import('@/views/commodityinfo'),
          meta: { title: '权限管理', icon: 'el-icon-user-solid' }
        },
        {
          path: 'user10',
          name: 'User10',
          component: () => import('@/views/commodityinfo'),
          meta: { title: '用户组管理', icon: 'el-icon-s-grid' }
        },
        {
          path: 'user11',
          name: 'User11',
          component: () => import('@/views/commodityinfo'),
          meta: { title: '权限分配', icon: 'el-icon-bangzhu' }
        },]
      },
    ]
  }, 
  
  {
    path: '/example3',
    component: Layout,
    redirect: '/example3',
    name: 'Example3',
    meta: { title: '库存管理员操作', icon: 'el-icon-s-unfold' },
    children: [
      {
        path: 'user2',
        name: 'User2',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '库存管理', icon: 'el-icon-receiving' }
      },
      {
        path: 'user6',
        name: 'User6',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '结转管理', icon: 'el-icon-sort' }
      },
    ]
  }, 

  {
    path: '/example4',
    component: Layout,
    redirect: '/example4',
    name: 'Example3',
    meta: { title: '店长操作', icon: 'el-icon-star-on' },
    children: [
      {
        path: 'user3',
        name: 'User3',
        component: () => import('@/views/commodityinfo'),
        meta: { title: '统计分析', icon: 'el-icon-notebook-1' },
        children:[
          {
            path: 'user4',
            name: 'User4',
            component: () => import('@/views/commodityinfo'),
            meta: { title: '销售查询', icon: 'el-icon-s-data' }
          },
          {
            path: 'user5',
            name: 'User5',
            component: () => import('@/views/commodityinfo'),
            meta: { title: '进销存报表', icon: 'el-icon-check' }
          },

          {
            path: 'user7',
            name: 'User7',
            component: () => import('@/views/commodityinfo'),
            meta: { title: '销售汇总', icon: 'el-icon-s-marketing' }
          },
        ]
      },
    ]
  }, 

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
