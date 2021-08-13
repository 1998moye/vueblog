import axios from 'axios'
import router from "./router";
import { message } from 'ant-design-vue';
axios.defaults.baseURL = 'http://' + window.location.hostname + ":8082";
axios.interceptors.request.use(config =>{
    return config;
})
axios.interceptors.response.use(res => {
    const d = res.data;
    if(d.status === 200){
        return res;
    }else{
        message.error('密码错误');
        return Promise.reject(d.msg);
    }
},error => {
    if(error.response.data){
        error.message = error.response.data.msg
    }
    if(error.response.data === 401){
        store.commit('remove_info');
        router.push('/login');
    }
    return Promise.reject(error);
}
)