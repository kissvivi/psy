import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/doctor',
    method: 'get',
    params
  })
}

export function add(Form) {
  return request({
    url: '/doctor',
    method: 'post',
    data: Form
  })
}

export function update(Form) {
  return request({
    url: '/doctor',
    method: 'put',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/doctor/' + Id,
    method: 'delete'
  })
}

export function ByUid(Id) {
  return request({
    url: '/doctor/uid/' + Id,
    method: 'get'
  })
}
