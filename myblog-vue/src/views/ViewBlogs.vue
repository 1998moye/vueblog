<template>
    <Header></Header>
    <a-layout>
    <a-layout-content style="padding: 10px 50px;width:80%;margin: 0 auto;">
        <div :style="{ background: '#fff', padding: '48px', minHeight: '240px' }" >
            <a-empty v-if="isEmpty.isEmpty" description="暂无数据"/>
            <a-row :gutter="[48, 48]" type="flex" justify="start" align="top">
            <a-col :span="8" v-for="blog in blogs.list">
            <a-card hoverable style="width: auto;" :loading="blog.id === null">
                <template #cover>
                    <img
                            alt="example"
                            style="width: 100%;height: 180px"
                            :src="blog.cover?baseUrl + blog.cover:'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png'"
                    />
                </template>
                <template class="ant-card-actions" #actions>
                    <a-tooltip>
                        <template #title>编辑内容</template>
                        <router-link :to="{path:'/edit/' +blog.id}"><edit-outlined key="edit" v-if="userId.userId === blog.userId"/></router-link>
                    </a-tooltip>
                    <span>
                        <a-tooltip>
                            <template #title>浏览数</template>
                            {{blog.scans}}
                            <eye-outlined/>
                        </a-tooltip>
                    </span>
                    <span>
                        <a-tooltip>
                            <template #title>点赞数</template>
                            {{blog.likes}}
                            <like-outlined />
                        </a-tooltip>
                    </span>
                </template>
                <a-tooltip>
                    <template #title>查看博客</template>
                    <router-link :to="{path: '/detail/' + blog.id}">
                    <a-card-meta :title="blog.title" :description="blog.description">
                    </a-card-meta>
                    </router-link>
                </a-tooltip>
            </a-card>
            </a-col>
            </a-row>


        </div>
        <div style="margin-top: 40px;float: right">
            <a-pagination show-quick-jumper v-model:page-size="page.size" v-model:current="page.current" :total="page.total" @change="pageChange" />
        </div>
    </a-layout-content>
    </a-layout>
</template>

<script>
    import Header from "../components/Header"
    import { SettingOutlined, EditOutlined, EllipsisOutlined, EyeOutlined, LikeOutlined } from '@ant-design/icons-vue';
    import { defineComponent, ref, getCurrentInstance, reactive, onMounted } from 'vue';
    export default defineComponent({
        name: "ViewBlogs",
        components:{
            Header,SettingOutlined,EditOutlined,EllipsisOutlined,
            EyeOutlined, LikeOutlined
        },

        setup(){
            let userId = reactive({userId:undefined})
            let page = reactive({
                current: 1,
                size: 6,
                total: 0,
            })
            let blogs = reactive({list:{}})
            let isEmpty = reactive({isEmpty: true})
            const {proxy} = getCurrentInstance()
            const pageChange = (current) => {
                console.log(current)
                proxy.$axios.get('/blogs?current=' + page.current + '&size=' + page.size).then(res => {
                    const resBlogs = res.data.data.records
                    blogs.list = resBlogs
                    const user = JSON.parse(sessionStorage.getItem('userInfo'))
                    if(user !== null && user.id !== null){
                        userId.userId = user.id
                    }
                    console.log(blogs)
                    isEmpty.isEmpty = false
                    page.current = res.data.data.current;
                    page.size = res.data.data.size;
                    page.total = res.data.data.total;

                })
            };

            onMounted(() => {
                pageChange(1)
            });
            return {
                page,
                userId,
                blogs,
                isEmpty,
                pageChange,
                baseUrl: proxy.$axios.defaults.baseURL
            };
        },

    })
</script>

<style scoped>

</style>