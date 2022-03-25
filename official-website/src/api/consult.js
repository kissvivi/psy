import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/consult',
    method: 'get',
    params
  })
}

export function add(Form) {
  return request({
    url: '/consult',
    method: 'post',
    data: Form
  })
}

export function update(Form) {
  return request({
    url: '/consult',
    method: 'patch',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/consult/' + Id,
    method: 'delete'
  })
}

export function listByDid(params) {
  return request({
    url: '/consult/listByDid',
    method: 'get',
    params
  })
}

export function listBySid(params) {
  return request({
    url: '/consult/listBySid',
    method: 'get',
    params
  })
}

export function updateStatus(params) {
  return request({
    url: '/consult/status',
    method: 'put',
    params
  })
}

export function upload(id, params) {
  return request({
    url: '/consult/upload/' + id,
    method: 'post',
    params
  })
}
