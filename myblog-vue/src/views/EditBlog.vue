<template>
    <Header></Header>
    <a-layout>
    <a-layout-content style="padding: 10px 50px;">
        <div :style="{ background: '#fff', padding: '32px', minHeight: '580px', width: '75%', textAlign: 'left', margin: '0 auto'}">
            <a-row type="flex" justify="start">
                <a-col :span="12" :offset="3">
                    <a-upload
                            list-type="picture"
                            accept="image/*"
                            v-model:file-list="fileList"
                            :custom-request="customRequest"
                            :remove="removeImg"
                    >
                        <a-button :disabled="isDiabled">
                            <upload-outlined></upload-outlined>
                            上传封面
                        </a-button>
                    </a-upload>
                </a-col>

            </a-row>

            <a-form
                    ref="formRef"
                    :model="formState"
                    :rules="rules"
                    :label-col="labelCol"
                    :wrapper-col="wrapperCol"
                    :style="{marginTop: '20px'}"
            >
                <a-form-item ref="title" label="标题" name="title">
                    <a-input v-model:value="formState.title" />
                </a-form-item>
                <a-form-item ref="description" label="描述" name="description">
                    <a-input v-model:value="formState.description" />
                </a-form-item>
                <a-form-item label="内容" name="content">
                    <v-md-editor v-model="formState.content" height="400px" :disabled-menus="[]" @upload-image="handleUploadImg"></v-md-editor>
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 18, offset: 10 }">
                    <a-button type="primary" @click="onSubmit">创建</a-button>
                    <a-button style="margin-left: 10px" @click="resetForm">重置</a-button>
                </a-form-item>
            </a-form>
        </div>
    </a-layout-content>
    </a-layout>
</template>

<script>
    import Header from '../components/Header'
    import { PlusOutlined, LoadingOutlined, UploadOutlined } from '@ant-design/icons-vue';
    import {defineComponent, ref, getCurrentInstance, reactive, onMounted, toRaw} from 'vue'
    import {Modal, message} from "ant-design-vue";
    function getBase64(img, callback) {
        const reader = new FileReader();
        reader.addEventListener('load', () => callback(reader.result));
        reader.readAsDataURL(img);
    }
    export default defineComponent({
        name: "EditBlogs",
        components:{
            Header,
            PlusOutlined,
            LoadingOutlined,
            UploadOutlined,
        },
        setup(){
            const {proxy} = getCurrentInstance()
            const formRef = ref();
            const isDiabled = ref(false)
            const formState = reactive({
                id: undefined,
                userId: undefined,
                title: '',
                description: '',
                content: '',
                cover: '',
            });
            let text = ref('')
            const rules = {
                title: [
                    {
                        min: 1,
                        message: '标题最少1字符',
                        trigger: 'blur',
                    },
                    {
                        validator: (rule, value, callback) => {
                            if(value.trim() === ''){
                                return Promise.reject('标题不能为空')
                            }else{
                                return Promise.resolve()
                            }
                        },
                        trigger: 'blur',
                    }
                ],
                description: [
                    {
                        required: true,
                        message: '描述不能为空',
                        trigger: 'blur',
                    },
                ],
                content: [
                    {
                        required: true,
                        message: '内容不能为空',
                        trigger: 'blur',
                    },
                ],
            };
            const onSubmit = () => {
                formRef.value
                    .validate()
                    .then(() => {
                        proxy.$axios.post('/edit', toRaw(formState), {
                            headers: {
                                "Authorization": localStorage.getItem('token')
                            }
                        }).then(res => {
                            if(res.data.status === 200){
                                message.success('博客创建成功', 2)
                                proxy.$router.go(-1)

                            }else{
                                message.error('博客创建失败', 2)
                            }
                        }).catch(error => {
                            message.error(error.message)
                        })
                    })
                    .catch(error => {
                        console.log('error', error);
                    });
            };

            const resetForm = () => {
                location.reload()
            };
            const getBlog = () =>{
                const blogId = proxy.$route.params.blogId
                if(blogId !== null && blogId !== undefined){
                    console.log(blogId)
                    proxy.$axios.get('/detail/' + blogId).then(res => {

                        const blog = res.data.data
                        formState.id = blog.id
                        formState.userId = blog.userId
                        formState.cover = blog.cover
                        formState.title = blog.title
                        formState.content = blog.content
                        formState.description = blog.description
                        console.log(formState.id)

                    })
                }
            };
            let fileList = ref([])
            const baseUrl = proxy.$axios.defaults.baseURL

            const customRequest = (options) => {
                let formData = new FormData()
                formData.append('file', options.file)
                proxy.$axios.post('/upload/cover', formData, {headers:{"Content-Type": "multipart/form-data"}}).then(res => {
                    fileList.value = [
                        {
                            uid: '1',
                            name: options.file.name,
                            thumburl: res.data.data,
                        }
                    ]
                    formState.cover = res.data.data
                    options.onSuccess(res, options.file)
                    isDiabled.value = true
                })
            }

            const removeImg = () => {
                console.log('删除图片执行')
                proxy.$axios.get('/removeImg?img=' + formState.cover).then(res => {
                    console.log(res)
                    isDiabled.value = false
                })
            }

            const handleUploadImg = (event, insertImage, files) => {
                let formData = new FormData()
                formData.append("file", files[0])
                console.log(files[0])
                proxy.$axios.post('/upload/content', formData, {headers: {
                        "Content-Type": "multipart/form-data"
                    }}).then(res => {
                    insertImage({
                        url:
                            baseUrl + res.data.data,
                        desc: '文章图片',
                    });
                })
            }

            onMounted(() => {
                getBlog()
            })

            return {
                formRef,
                labelCol: {
                    span: 3,
                },
                wrapperCol: {
                    span: 18,
                },
                other: '',
                formState,
                rules,
                onSubmit,
                resetForm,
                baseUrl,
                fileList,
                customRequest,
                isDiabled,
                removeImg,
                handleUploadImg,
            };
        }
    })
</script>

<style scoped>
    .avatar-uploader > .ant-upload {
        width: 128px;
        height: 128px;
    }
    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>