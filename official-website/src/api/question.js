import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/question',
    method: 'get',
    params
  })
}

export function add(Form) {
  return request({
    url: '/question',
    method: 'post',
    data: Form
  })
}

export function update(Form) {
  return request({
    url: '/question',
    method: 'put',
    data: Form
  })
}

export function remove(Id) {
  return request({
    url: '/question/' + Id,
    method: 'delete'
  })
}

export function testsList(qid) {
  return request({
    url: '/question/testsList/'+qid,
    method: 'get'
  })
}
