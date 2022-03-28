import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/test/student',
    method: 'get',
    params
  })
}

export function add(Form) {
  return request({
    url: '/test/student',
    method: 'post',
    data: Form
  })
}

export function update(Form) {
  return request({
    url: '/test/student',
    method: 'put',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/test/student/' + Id,
    method: 'delete'
  })
}
