# Spring Boot Vue Admin 在线校园心理咨询平台

![stars](https://img.shields.io/github/stars/kissvivi/psy.svg?style=flat-square&label=Stars)
![license](https://img.shields.io/github/license/kissvivi/psy.svg?style=flat-square)

## 简介

基于Spring Boot Vue Admin上扩充功能，作为校园心理咨询平台

## 预览


## Demo

在线 Demo：[暂无]()

## 依赖版本

前端依赖 | 版本
--------|------
node    | 8.16.1
npm     | 6.4.1

后端依赖    | 版本
-----------|------
SpringBoot | 2.1.6

其他依赖版本没有测试，请自行测试，如有问题请提 issues。

## 快速开始

```markdown
# 克隆项目
git clone https://github.com/kissvivi/psy.git

# 进入项目
cd psy

# 后端
cd api

# 导入数据库文件（记得修改数据库信息）
sudo chmod a+x resetDB.sh && ./resetDB.sh

# 启动后端服务...

# 前端
cd app

# 安装依赖
npm install

# 启动前端服务
npm run dev
```

欢迎小伙伴 star 和 issues ~ 谢谢 :）

## 问题解决

### no such file/ansi-styles/css-loader

如果出现以下错误，请先单独安装 `npm install css-loader`，再安装项目依赖 `npm install`。

```bash
npm ERR! enoent ENOENT: no such file or directory, rename '/workspace/spring-boot-vue-admin/app/node_modules/.staging/css-loader-b931fe48/node_modules/ansi-styles' -> '/workspace/spring-boot-vue-admin/app/node_modules/.staging/ansi-styles-6535fafb'
```

## 更新日志
