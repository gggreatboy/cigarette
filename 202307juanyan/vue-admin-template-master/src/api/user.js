import request from '@/utils/request'
//登录请求
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}
//在前端完成该逻辑
export function getInfo(token) {
  return request({
    url: '/users/info',
    method: 'get',
    params: { token }
  })
}
//在前端完成该逻辑
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}
