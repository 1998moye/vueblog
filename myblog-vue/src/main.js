import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "axios";
import Antd from 'ant-design-vue'
import "ant-design-vue/dist/antd.css"
import { Quasar } from 'quasar'
import quasarUserOptions from './quasar-user-options'
import ElementPlus from 'element-plus'
import 'element-plus/lib/theme-chalk/index.css'
import './axios'
import './permission'
import hljs from 'highlight.js'
import 'highlight.js/styles/stackoverflow-light.css'
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

VMdEditor.use(githubTheme, {
    hljs,
});

const app = createApp(App).use(Quasar, quasarUserOptions)
app.config.productionTip=false
app.config.globalProperties.$axios = axios
app.use(router)
app.use(store)
app.use(Antd)
app.use(ElementPlus)
// app.use(Menu)
// app.use(axios)
app.use(Quasar)
app.use(VMdEditor)
app.directive("highlight", el => {
    let blocks = el.querySelectorAll('pre code');
    blocks.forEach(block => {
        hljs.highlightBlock(block)
    })
})
// app.component("Quasar",QAvatar)
app.mount('#app')
