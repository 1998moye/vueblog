import { createStore } from 'vuex'

export default createStore({
  state: {
    token: localStorage.getItem('token'),
    userInfo: JSON.parse(sessionStorage.getItem('userInfo')),
  },
  mutations: {
    set_token(state, token){
      state.token = token;
      localStorage.setItem('token', token)
    },
    set_userinfo(state, userInfo){
      state.userInfo = userInfo;
      sessionStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    remove_info(state){
      state.token = '';
      state.userInfo = {};
      localStorage.setItem('token', '');
      sessionStorage.setItem('userInfo', JSON.stringify(''));
    },
  },
  getters: {
    getUser(state){
      return state.userInfo;
    },
  },
  actions: {
  },
  modules: {
  }
})
