<template>
    <a-layout>
        <a-layout-header class="header">
            <div style="float: left">
                <a-avatar :size="{ xs: 24, sm: 32, md: 40, lg: 50}"
                    :src="user.avatar"
                >
                </a-avatar>
                <span style="font-size: 18px">{{user.isUser && user.username !== undefined?'欢迎,' + user.username:'请先登录'}}</span>
            </div>
            <div style="float: right;">
                <a-button class="header-font" type="link"><router-link :to="{path: '/blogs'}"><HomeFilled/>&nbsp;首页</router-link></a-button>
                <a-button :style="{color: '#8B4513', }" class="header-font" type="link" @click="editBlog"><EditOutlined />写博客</a-button>
                <router-link :to="'/login'">
                <a-button class="header-font" type="link" :style="{color:'green'}" v-if="!user.isUser || user.username === undefined">
                    <UserAddOutlined/>
                    {{'登录'}}
                </a-button>
                </router-link>
                <a-button class="header-font" type="link" :style="{color:'red'}" v-if="user.isUser && user.username !== undefined" @click="logout">
                    <UserDeleteOutlined/>
                    {{'退出'}}
                </a-button>
            </div>
        </a-layout-header>
    </a-layout>
</template>

<script>
    import {message, Modal} from 'ant-design-vue'
    import { AntDesignOutlined, HomeFilled, UserDeleteOutlined, UserAddOutlined, EditOutlined, ExclamationCircleOutlined } from '@ant-design/icons-vue';
    import {defineComponent, ref, getCurrentInstance, createVNode} from 'vue'
    import axios from 'axios'
    export default defineComponent({
        name: "Header",
        data(){
            return{
                user:{
                   username: '',
                   avatar: '',
                   isUser:false,
                },
                circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",

            }
        },
        components:{
          AntDesignOutlined,
            HomeFilled,
            UserDeleteOutlined,
            UserAddOutlined,
            EditOutlined,
            ExclamationCircleOutlined,
        },
        methods:{
            logout(){
                const _this = this
                Modal.confirm({
                    title: '退出提示',
                    icon: createVNode(ExclamationCircleOutlined),
                    content: '您当前可能有正在活动的内容，确定要退出吗?',

                    onOk() {
                        return new Promise((resolve, reject) => {
                            setTimeout(Math.random() > 0.5 ? resolve : reject, 200);
                            console.log('执行此处')
                            axios.get('/logout').then(res => {
                                console.log(res)
                                _this.user.isUser = false;
                                _this.user.username = '';
                                _this.user.avatar = ''
                                _this.$store.commit('remove_info')
                                _this.$router.go(0)
                            })
                        }).catch((error) => console.log('Oops errors!' + error.message));
                    },
                    onCancel() {},
                });

            },
            editBlog(){
                if(this.user.isUser === false && this.user.username === ''){
                    message.error('请先登录')
                    return;
                }else{
                    this.$router.push('/add')
                }
            }
        },
        created() {
            var userInfo = this.$store.getters.getUser;
            if(userInfo !== null && userInfo !== '' && userInfo.username !== undefined){
                this.user.username = userInfo.username;
                this.user.avatar = this.$axios.defaults.baseURL + userInfo['avatar'];
                console.log(this.user.avatar)
                this.user.isUser = true;
            }
        },

    })
</script>

<style scoped>
    .header{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        max-height: 200px;
        background: white;
    }
    .header-font{
        font-size: 16px;
    }
</style>