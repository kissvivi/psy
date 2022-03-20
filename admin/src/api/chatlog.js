import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/chat/log',
    method: 'get',
    params
  })
}

export function add(Form) {
  return request({
    url: '/chat/log',
    method: 'post',
    data: Form
  })
}

export function update(Form) {
  return request({
    url: '/chat/log',
    method: 'put',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/chat/log/' + Id,
    method: 'delete'
  })
}

export function listByConsultId(params) {
  return request({
    url: '/chat/log/listByConsultId',
    method: 'get',
    params
  })
}