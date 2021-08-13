import router from "./router";
import {Modal} from "ant-design-vue";
var flag = false;
router.beforeEach((to, from, next) => {
    if(to.matched.some(record => record.meta.requireAuth)){
        const token = localStorage.getItem("token");
        const userInfo = sessionStorage.getItem('userInfo')
        if(token && userInfo !== null){
            flag = true
            next()
        }else{
            next({
                path: '/login'
            })
        }
    }else{
        if(to.path === '/login'){
            next()
        }else {
            if (flag) {
                Modal.confirm({
                    title: '请查看你是否保存内容',
                    content: '你确定要跳转到其它网页？',
                    onOk() {
                        return new Promise((resolve, reject) => {
                            setTimeout(Math.random() > 0.5 ? resolve : reject, 200);
                            flag = false
                            next()
                        }).catch(() => console.log('Oops errors!'));
                    },

                    // eslint-disable-next-line @typescript-eslint/no-empty-function
                    onCancel() {
                        next(false)
                    },
                });
            }else{
                next()
            }
        }
    }

    if(to.meta.title){
        document.title = '个人博客-' + to.meta.title
    }else{
        document.title = '个人博客'
    }
})