import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/student',
    method: 'get',
    params
  })
}

export function add(Form) {
  return request({
    url: '/student',
    method: 'post',
    data: Form
  })
}

export function update(Form) {
  return request({
    url: '/student',
    method: 'put',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/student/' + Id,
    method: 'delete'
  })
}

export function ByUid(Id) {
  return request({
    url: '/doctor/uid/' + Id,
    method: 'get'
  })
}
