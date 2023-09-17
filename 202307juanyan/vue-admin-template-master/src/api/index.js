import request from '@/utils/request'
//前端完成
export function getIndexData() {
  return request({
    url: '/index',
    method: 'get'
  })
}