import request from '@/utils/request'
//分页查询
export function pageComType(typeName,typeFlag,typeBig,page,pageSize) {
    return request({
      url: '/type?typeFlag='+typeFlag+'&typeName='+typeName+'&typeBig='+typeBig+'&page='+page+'&pageSize='+pageSize,
      method: 'get'
    })
  }

//增加一个商品分类
export function addComType(Type) {
    return request({
      url: '/type',
      method: 'post',
      data: Type
    })
  }

//更新商品类别信息
export function updateComType(Type) {
    return request({
      url: '/type',
      method: 'put',
      data:Type
    })
  }

//根据id删除商品类别
export function deleteComType(id) {
    return request({
      url: '/type/'+id,
      method: 'delete',
    })
}

//选择一个商品类别
export function selectComType(id) {
    return request({
      url: '/type/'+id,
      method: 'get',
    })
  }

//计算大类个数
export function countBigType() {
    return request({
        url: '/type/big',
        method: 'get',
    })
    }

//计算小类个数
export function countSmallType() {
    return request({
        url: '/type/small',
        method: 'get',
    })
    }

//计算每个大类中小类个数
export function countSmallTypeInBig() {
    return request({
        url: '/type/smallinbig',
        method: 'get',
    })
    }

//计算每个小类中含有的商品数
export function countComInSmall() {
    return request({
        url: '/type/cominsmall',
        method: 'get',
    })
    }