import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/tests',
    method: 'get',
    params
  })
}

export function add(Form) {
  return request({
    url: '/tests',
    method: 'post',
    data: Form
  })
}

export function update(Form) {
  return request({
    url: '/tests',
    method: 'put',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/tests/' + Id,
    method: 'delete'
  })
}
