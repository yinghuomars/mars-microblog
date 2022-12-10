## 博客介绍

<center>
  <a href="https://th.yinghuomars.xyz">
    <img src="https://oos.yinghuomars.xyz/avatar/adce3fe8eadf84abffed6b1c5fb870b3.png" alt="荧惑Mars" style="border-radius: 50%">
  </a>
</center>

<h1 align="center">
   荧惑Mars
</h1>

<center>
   <a href="https://github.com/yinghuomars/mars-microblog" target="_blank">
      <img src="https://img.shields.io/hexpm/l/plug.svg" alt="l"/>
      <img src="https://img.shields.io/badge/JDK-11-green.svg" alt="JDK-11"/>
      <img src="https://img.shields.io/badge/springboot-2.4.1-green" alt="springboot-2.4.1"/>
      <img src="https://img.shields.io/badge/vue-2.5.17-green" alt="vue-2.5.17"/>
      <img src="https://img.shields.io/badge/mysql-8.0.31-green" alt="mysql-8.0.31"/>
      <img src="https://img.shields.io/badge/mybatis--plus-3.4.0-green" alt="mybatis--plus-3.4.0"/>
      <img src="https://img.shields.io/badge/redis-6.2.7-green" alt="redis-6.2.7"/>
      <img src="https://img.shields.io/badge/elasticsearch-7.9.3-green" alt="elasticsearch-7.9.3"/>
      <img src="https://img.shields.io/badge/rabbitmq-3.11.4-green" alt="rabbitmq-3.11.4"/>
   </a>
</center>

[在线地址](#在线地址) | [目录结构](#目录结构) | [项目特点](#项目特点) | [技术介绍](#技术介绍) | [运行环境](#运行环境) | [开发环境](#开发环境) | [注意事项](#注意事项)
| [项目部署](#项目部署)

## 在线地址

**项目链接：** <a href="https://th.yinghuomars.xyz" target="_blank">荧惑Mars</a>

**Github地址：** <a href="https://github.com/yinghuomars/mars-microblog" target="_blank">荧惑Mars</a>

## 目录结构

前端项目位于blog-vue下，blog为前台，admin为后台。

后端项目位于blog-springboot下。

SQL文件位于根目录下的blog-mysql8.sql，需要MYSQL8以上版本。

可直接导入该项目于本地，修改后端配置文件中的数据库等连接信息，项目中使用到的关于阿里云功能和第三方授权登录等需要自行开通。

## 项目特点

- 前台参考"Hexo"的"Butterfly"设计，美观简洁，响应式体验好。
- 后台参考"element-admin"设计，侧边栏，历史标签，面包屑自动生成。
- 采用Markdown编辑器，写法简单。
- 评论支持表情输入回复等，样式参考Valine。
- 添加音乐播放器，支持在线搜索歌曲。
- 前后端分离部署，适应当前潮流。
- 接入第三方登录，减少注册成本。
- 支持发布说说，随时分享趣事。
- 留言采用弹幕墙，更加炫酷。
- 支持代码高亮和复制，图片预览，深色模式等功能，提升用户体验。
- 搜索文章支持高亮分词，响应速度快。
- 新增文章目录、推荐文章等功能，优化用户体验。
- 新增在线聊天室，支持撤回、语音输入、统计未读数量等功能。
- 新增aop注解实现日志管理。
- 支持动态权限修改，采用RBAC模型，前端菜单和后台权限实时更新。
- 后台管理支持修改背景图片，博客配置等信息，操作简单，支持上传相册。
- 代码支持多种搜索模式（Elasticsearch或MYSQL），支持多种上传模式（OSS或本地），可支持配置。
- 代码遵循阿里巴巴开发规范，利于开发者学习。

## 技术介绍

**前端：** `vue`+`vuex`+`vue-router`+`axios`+`vuetify`+`element`+`echarts`

**后端：** `SpringBoot`+`nginx`+`docker`+`SpringSecurity`+`Swagger2`+`MyBatisPlus`+`Mysql`+`Redis`+`elasticsearch`
+`RabbitMQ`+`MaxWell`+`Websocket`

## 运行环境

**服务器：** 腾讯云2核4G CentOS7.6

**CDN：** 阿里云全站加速

**对象存储：** 阿里云OSS

**最低配置：** 1核2G服务器（关闭ElasticSearch）

## 开发环境

| 开发环境           | 版本         |
|----------------|------------|
| JDK            | openjdk-11 |
| MySQL          | 8.0.31     |
| Redis          | 6.2.7      |
| Elasticsearch  | 7.9.3      |
| RabbitMQ       | 3.11.4     |

## 注意事项

- 接入QQ，微博第三方登录，接入腾讯云人机验证
- 项目拉下来运行后，可到后台管理页面网站配置处修改博客相关信息.
- 邮箱配置，第三方授权配置需要自己申请。
- ElasticSearch需要自己先创建索引，项目运行环境教程中有介绍。

## 项目部署

**部署文档：** <a href="https://github.com/yinghuomars/mars-microblog/DEPLOY.md" target="_blank">项目部署（docker）</a>