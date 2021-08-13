<template>
    <a-back-top />
    <Header></Header>
    <a-layout class="layout">
        <a-layout-content style="padding: 10px 50px;">

            <a-row type="flex" justify="center" style="width: 70%;margin: 0 auto;">
                <a-col :span="24" >
                    <div :style="{ background: '#fff', padding: '32px', minHeight: '580px', width: 'auto', textAlign: 'left'}" v-show="!showContent">
                        <a-skeleton active :paragraph="{rows: 5, width: 'auto'}"/>
                        <a-skeleton active :paragraph="{rows: 5, width: 'auto'}"/>
                    </div>
                    <div :style="{ background: '#fff', padding: '32px', minHeight: '580px', width: 'auto', textAlign: 'left'}" v-show="showContent">
                        <a-typography-title>{{blog.blog.title}}</a-typography-title>
                        <div style="width:auto">
                            <a-card style="width: auto; background: ghostwhite;" size="small">

                                <a-row>
                                    <a-col :span="24">

                                        <a-space :size="30">
                                            <span>
                                                发布于:{{blog.blog.created}}
                                            </span>
                                            <span>
                                                作者:{{username}}
                                            </span>
                                        <span>
                                            {{blog.blog.scans}}
                                            <eye-outlined/>
                                        </span>
                                        <span>
                                            <a :style="{color: isLike? 'blue':'gray'}" @click="giveLike">{{blog.blog.likes}}
                                            <like-outlined /></a>
                                        </span>
                                            <span>
                                                <router-link :to="{path: '/edit/' + blog.blog.id}" v-show="userId === blog.blog.userId"><edit-outlined key="edit"/>编辑</router-link>
                                            </span>
                                        </a-space>
                                    </a-col>
                                </a-row>
                            </a-card>
                        </div>
                        <a-divider />
                        <div style="margin-top: 10px">

                                    <a-typography-paragraph strong="true">
                                        <blockquote>{{blog.blog.description}}</blockquote>
                                    </a-typography-paragraph>


                        </div>
                        <a-divider />
                        <div class="markdown-body" v-html="blog.blog.content" v-highlight>

                        </div>
                    </div>
                </a-col>
            </a-row>
        </a-layout-content>
    </a-layout>
</template>

<script>
    import Header from '../components/Header'
    import {EyeOutlined, LikeOutlined, EditOutlined} from '@ant-design/icons-vue'
    import {defineComponent, ref, reactive, getCurrentInstance, onMounted, onBeforeMount} from 'vue'
    import {message} from 'ant-design-vue'
    // import MarkdownIt from 'markdown-it'
    // import hljs from 'markdown-it-highlightjs'
    import marked from 'marked'
    import 'github-markdown-css'
    export default defineComponent({
        name: "ViewBlogContent",
        components: {
            Header,
            EyeOutlined,
            LikeOutlined,
            EditOutlined,
        },
        setup(){
            const {proxy} = getCurrentInstance()
            let blog = reactive({blog:{}})
            let username = ref('')
            let isLike = ref(false)
            let showContent = ref(false)
            const blogId = proxy.$route.params.blogId
            const userInfo = proxy.$store.getters.getUser
            let userId = ref(0)
            console.log(userId)
            if(userInfo !== '' && userInfo !== null){
                userId.value = userInfo.id
            }
            console.log(userId)

            const getBlog = () => {
                proxy.$axios.get('/detail/' + blogId).then(res => {
                    const blogInfo = res.data.data
                    blog.blog = blogInfo
                    username.value = res.data.msg
                    // console.log(localStorage.getItem('token'))
                    if(localStorage.getItem('token') !== null && localStorage.getItem('token') !== ''){
                        getLiked()
                    }

                    blog.blog.content = marked(blog.blog.content)
                    showContent.value = true

                })
            }
            const giveLike = () => {
                        console.log(blog.blog.id)
                        proxy.$axios.get('/like?userId=' + userId.value + '&blogId=' + blog.blog.id , {
                            headers:{
                                "Authorization": localStorage.getItem('token')
                            }
                        }).then(res => {
                            if(res.data.data === 1){
                                isLike.value = true
                                blog.blog.likes += 1
                            }else{
                                isLike.value = false
                                blog.blog.likes -= 1
                            }
                        }).catch(reason => {
                            message.error('请先登录')
                        })
                }
            const getLiked = () => {
                    proxy.$axios.get('/getLike?userId=' + userId.value + '&blogId=' + blog.blog.id,
                        {headers: {
                                "Authorization": localStorage.getItem('token')
                            }}
                    ).then(res => {
                        const resLiked = res.data.data
                        console.log(resLiked)
                        if(resLiked === 0){
                            isLike.value = false
                        }else{
                            isLike.value = true
                        }
                    })
            }


            onMounted(() => {
              getBlog()
            })
            return {
                blog,
                username,
                isLike,
                showContent,
                giveLike,
                userId,

            }
        }
    })
</script>

<style>
    img{
        width: 60%;
        height: auto;
    }
</style>