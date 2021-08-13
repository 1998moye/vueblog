<template>
    <div>

            <a-layout>
                <div class="login-header">
                <a-layout-header style="background: white"><img :src="require('@/assets/login.png')"/>myblog</a-layout-header>
                </div>
                <a-layout-content style="padding: 0px 50px;margin-top: 10px;min-height: 600px">
                    <div :style="{ background: '#fff', padding: '140px', minHeight: '400px' }">
                        <div style="margin: 0 auto;max-width: 400px;background: white">
                            <a-form :label-col="labelCol" :wrapper-col="wrapperCol">
                                <a-form-item label="用户名" v-bind="validateInfos.username">
                                    <a-input
                                            v-model:value="modelRef.username"
                                            @blur="validate('username', { trigger: 'blur' }).catch(() => {})"
                                    />
                                </a-form-item>
                                <a-form-item label="密码" v-bind="validateInfos.password">
                                    <a-input-password
                                            v-model:value="modelRef.password" type="password" placeholder="请输入密码"
                                            @blur="validate('password', { trigger: 'blur' }).catch(() => {})"
                                    />

                                </a-form-item>
                                <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                                    <a-button type="primary" @click.prevent="onSubmit">登录</a-button>
                                    <a-button style="margin-left: 10px" @click="resetFields">重置</a-button>
                                </a-form-item>
                            </a-form>
                        </div>
                    </div>
                </a-layout-content>
            </a-layout>

        </div>



</template>

<script>
    import { defineComponent, reactive, toRaw, getCurrentInstance, onMounted} from 'vue';
    import { Form } from 'ant-design-vue';
    import {message} from 'ant-design-vue'
    const useForm = Form.useForm;
    export default defineComponent({
        name: "Login",
        setup(){
            const {ctx, proxy} = getCurrentInstance();
            const modelRef = reactive({
                username: '',
                password: '',
            });
            const rulesRef = reactive({
                username: [
                    {
                        required: true,
                        message: '请输入用户名',
                    },
                    {
                        min: 1,
                        max: 10,
                        message: '用户名长度为1到10个字符',
                        trigger: 'blur',
                    },
                ],
                password: [
                    {
                        required: true,
                        message: '请输入密码',
                    },
                    {
                        min: 5,
                        max: 16,
                        message: '密码长度为6-16的字符串',
                        trigger: 'blur',
                    },
                ],
            });
            const { resetFields, validate, validateInfos } = useForm(modelRef, rulesRef);
            const onSubmit = () => {
                validate()
                    .then(() => {
                        console.log(toRaw(modelRef));
                        const user = toRaw(modelRef)
                        console.log(user.name)
                        // console.log(axios.defaults.baseURL)
                        proxy.$axios.post('/login', user).then(res => {
                            const user = res.data.data;
                            proxy.$store.commit('set_userinfo', user)
                            proxy.$store.commit('set_token', res.headers['authorization'])
                            // console.log(proxy.$store.getters.getUser)
                            proxy.$router.go(-1)
                        }).catch(error => {
                            message.error(error.message, 2)
                        })
                    })

                    .catch(err => {
                        console.log('error', err);
                    });
            };
            document.onkeydown = function (e) {
                var keycode = window.event.keyCode;
                if(keycode === 13){
                    onSubmit()
                }
            }
            return {
                labelCol: { span: 4 },
                wrapperCol: { span: 14 },
                validate,
                validateInfos,
                resetFields,
                modelRef,
                onSubmit,
            };
        }
    })
</script>

<style scoped>
    .login-content{
        margin: 0 auto;
        text-align: center;
        max-width: 90%;
    }
    .login-header{
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        font-size: 20px;
    }
</style>