import request from '@/utils/request'
//分页查询
export function pageCom(comName,comCode1,comCode2,comType1,page,pageSize) {
    return request({
      url: '/commodities?comName='+comName+'&comCode1='+comCode1+'&comCode2='+comCode2+'&comType1='+comType1+'&page='+page+'&pageSize='+pageSize,
      method: 'get'
    })
  }

//增加一个商品
export function addCom(commodity) {
    return request({
      url: '/commodities',
      method: 'post',
      data: commodity
    })
  }

//更新商品信息
export function updateCom(commodity) {
    return request({
      url: '/commodities',
      method: 'put',
      data:commodity
    })
  }

//根据id删除商品
export function deleteCom(id) {
    return request({
      url: '/commodities/'+id,
      method: 'delete',
    })
}

//选择一个商品
export function selectCom(id) {
    return request({
      url: '/commodities/'+id,
      method: 'get',
    })
  }
