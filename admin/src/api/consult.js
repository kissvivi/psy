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
    method: 'put',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/consult/' + Id,
    method: 'delete'
  })
}

