import { createRouter, createWebHistory } from 'vue-router'
import ViewBlogs from "../views/ViewBlogs.vue";
import ViewBlogContent from "../views/ViewBlogContent.vue";
import Login from "../views/Login.vue";
import EditBlog from "../views/EditBlog.vue";
const routes = [
  {
    path: '/',
    name: 'Index',
    redirect:{name:"ViewBlogs"}
  },
  {
    path: '/blogs',
    name: 'ViewBlogs',
    component:ViewBlogs
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/detail/:blogId',
    name: 'Detail',
    component: ViewBlogContent
  },
  {
    path: '/edit/:blogId',
    name: 'Edit',
    component: EditBlog,
    meta:{
      requireAuth: true,
    }
  },
  {
    path: '/add',
    name: 'Add',
    component: EditBlog,
    meta:{
      requireAuth: true,
    },
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
