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
  console.log("idddd:",Id)
  return request({
    
    url: '/student/uid/' + Id,
    method: 'get'
  })
}
