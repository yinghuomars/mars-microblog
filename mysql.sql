start transaction;

create database microblog charset utf8mb4 collate utf8mb4_unicode_ci;

use microblog;

drop table if exists tb_article;
create table if not exists tb_article
(
    id              int auto_increment
        primary key,
    user_id         int                  not null comment '作者',
    category_id     int                  null comment '文章分类',
    article_cover   varchar(1024)        null comment '文章缩略图',
    article_title   varchar(50)          not null comment '标题',
    article_content longtext             not null comment '内容',
    type            tinyint(1) default 0 not null comment '文章类型 1原创 2转载 3翻译',
    original_url    varchar(255)         null comment '原文链接',
    is_top          tinyint(1) default 0 not null comment '是否置顶 0否 1是',
    is_delete       tinyint(1) default 0 not null comment '是否删除  0否 1是',
    status          tinyint(1) default 1 not null comment '状态值 1公开 2私密 3评论可见',
    create_time     datetime             not null comment '发表时间',
    update_time     datetime             null comment '更新时间'
);

drop table if exists tb_article_tag;
create table if not exists tb_article_tag
(
    id         int auto_increment
        primary key,
    article_id int not null comment '文章id',
    tag_id     int not null comment '标签id'
);

create index fk_article_tag_1
    on tb_article_tag (article_id);

create index fk_article_tag_2
    on tb_article_tag (tag_id);


drop table if exists tb_category;
create table if not exists tb_category
(
    id            int auto_increment
        primary key,
    category_name varchar(20) not null comment '分类名',
    create_time   datetime    not null comment '创建时间',
    update_time   datetime    null comment '更新时间'
);

drop table if exists tb_chat_record;
create table if not exists tb_chat_record
(
    id          int auto_increment comment '主键'
        primary key,
    user_id     int           null comment '用户id',
    nickname    varchar(50)   not null comment '昵称',
    avatar      varchar(255)  not null comment '头像',
    content     varchar(1000) not null comment '聊天内容',
    ip_address  varchar(50)   not null comment 'ip地址',
    ip_source   varchar(255)  not null comment 'ip来源',
    type        tinyint       not null comment '类型',
    create_time datetime      not null comment '创建时间',
    update_time datetime      null comment '更新时间'
);

drop table if exists tb_comment;
create table if not exists tb_comment
(
    id              int auto_increment comment '主键'
        primary key,
    user_id         int                  not null comment '评论用户Id',
    topic_id        int                  null comment '评论主题id',
    comment_content text                 not null comment '评论内容',
    reply_user_id   int                  null comment '回复用户id',
    parent_id       int                  null comment '父评论id',
    type            tinyint              not null comment '评论类型 1.文章 2.友链 3.说说',
    is_delete       tinyint    default 0 not null comment '是否删除  0否 1是',
    is_review       tinyint(1) default 1 not null comment '是否审核',
    create_time     datetime             not null comment '评论时间',
    update_time     datetime             null comment '更新时间'
);

create index fk_comment_parent
    on tb_comment (parent_id);

create index fk_comment_user
    on tb_comment (user_id);

drop table if exists tb_friend_link;
create table if not exists tb_friend_link
(
    id           int auto_increment
        primary key,
    link_name    varchar(20)  not null comment '链接名',
    link_avatar  varchar(255) not null comment '链接头像',
    link_address varchar(50)  not null comment '链接地址',
    link_intro   varchar(100) not null comment '链接介绍',
    create_time  datetime     not null comment '创建时间',
    update_time  datetime     null comment '更新时间'
);

create index fk_friend_link_user
    on tb_friend_link (link_name);

drop table if exists tb_menu;
create table if not exists tb_menu
(
    id          int auto_increment comment '主键'
        primary key,
    name        varchar(20)          not null comment '菜单名',
    path        varchar(50)          not null comment '菜单路径',
    component   varchar(50)          not null comment '组件',
    icon        varchar(50)          not null comment '菜单icon',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间',
    order_num   tinyint(1)           not null comment '排序',
    parent_id   int                  null comment '父id',
    is_hidden   tinyint(1) default 0 not null comment '是否隐藏  0否1是'
);

drop table if exists tb_message;
create table if not exists tb_message
(
    id              int auto_increment comment '主键id'
        primary key,
    nickname        varchar(50)       not null comment '昵称',
    avatar          varchar(255)      not null comment '头像',
    message_content varchar(255)      not null comment '留言内容',
    ip_address      varchar(50)       not null comment '用户ip',
    ip_source       varchar(255)      not null comment '用户地址',
    time            tinyint(1)        null comment '弹幕速度',
    is_review       tinyint default 1 not null comment '是否审核',
    create_time     datetime          not null comment '发布时间',
    update_time     datetime          null comment '修改时间'
);

drop table if exists tb_operation_log;
create table if not exists tb_operation_log
(
    id             int auto_increment comment '主键id'
        primary key,
    opt_module     varchar(20)  not null comment '操作模块',
    opt_type       varchar(20)  not null comment '操作类型',
    opt_url        varchar(255) not null comment '操作url',
    opt_method     varchar(255) not null comment '操作方法',
    opt_desc       varchar(255) not null comment '操作描述',
    request_param  longtext     not null comment '请求参数',
    request_method varchar(20)  not null comment '请求方式',
    response_data  longtext     not null comment '返回数据',
    user_id        int          not null comment '用户id',
    nickname       varchar(50)  not null comment '用户昵称',
    ip_address     varchar(255) not null comment '操作ip',
    ip_source      varchar(255) not null comment '操作地址',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     null comment '更新时间'
);

drop table if exists tb_page;
create table if not exists tb_page
(
    id          int auto_increment comment '页面id'
        primary key,
    page_name   varchar(10)  not null comment '页面名',
    page_label  varchar(20)  null comment '页面标签',
    page_cover  varchar(255) not null comment '页面封面',
    create_time datetime     not null comment '创建时间',
    update_time datetime     null comment '更新时间'
)
    comment '页面';

drop table if exists tb_photo;
create table if not exists tb_photo
(
    id          int auto_increment comment '主键'
        primary key,
    album_id    int                  not null comment '相册id',
    photo_name  varchar(20)          not null comment '照片名',
    photo_desc  varchar(50)          null comment '照片描述',
    photo_src   varchar(255)         not null comment '照片地址',
    is_delete   tinyint(1) default 0 not null comment '是否删除',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间'
)
    comment '照片';

drop table if exists tb_photo_album;
create table if not exists tb_photo_album
(
    id          int auto_increment comment '主键'
        primary key,
    album_name  varchar(20)          not null comment '相册名',
    album_desc  varchar(50)          not null comment '相册描述',
    album_cover varchar(255)         not null comment '相册封面',
    is_delete   tinyint(1) default 0 not null comment '是否删除',
    status      tinyint(1) default 1 not null comment '状态值 1公开 2私密',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间'
)
    comment '相册';

drop table if exists tb_resource;
create table if not exists tb_resource
(
    id             int auto_increment comment '主键'
        primary key,
    resource_name  varchar(50)          not null comment '资源名',
    url            varchar(255)         null comment '权限路径',
    request_method varchar(10)          null comment '请求方式',
    parent_id      int                  null comment '父权限id',
    is_anonymous   tinyint(1) default 0 not null comment '是否匿名访问 0否 1是',
    create_time    datetime             not null comment '创建时间',
    update_time    datetime             null comment '修改时间'
);

drop table if exists tb_role;
create table if not exists tb_role
(
    id          int auto_increment comment '主键id'
        primary key,
    role_name   varchar(20)          not null comment '角色名',
    role_label  varchar(50)          not null comment '角色描述',
    is_disable  tinyint(1) default 0 not null comment '是否禁用  0否 1是',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间'
);

drop table if exists tb_role_menu;
create table if not exists tb_role_menu
(
    id      int auto_increment comment '主键'
        primary key,
    role_id int null comment '角色id',
    menu_id int null comment '菜单id'
);

drop table if exists tb_role_resource;
create table if not exists tb_role_resource
(
    id          int auto_increment
        primary key,
    role_id     int null comment '角色id',
    resource_id int null comment '权限id'
);

drop table if exists tb_tag;
create table if not exists tb_tag
(
    id          int auto_increment
        primary key,
    tag_name    varchar(20) not null comment '标签名',
    create_time datetime    not null comment '创建时间',
    update_time datetime    null comment '更新时间'
);

drop table if exists tb_talk;
create table if not exists tb_talk
(
    id          int auto_increment comment '说说id'
        primary key,
    user_id     int                  not null comment '用户id',
    content     varchar(2000)        not null comment '说说内容',
    images      varchar(2500)        null comment '图片',
    is_top      tinyint(1) default 0 not null comment '是否置顶',
    status      tinyint(1) default 1 not null comment '状态 1.公开 2.私密',
    create_time datetime             not null comment '创建时间',
    update_time datetime             null comment '更新时间'
);

drop table if exists tb_unique_view;
create table if not exists tb_unique_view
(
    id          int auto_increment
        primary key,
    views_count int      not null comment '访问量',
    create_time datetime not null comment '创建时间',
    update_time datetime null comment '更新时间'
);

drop table if exists tb_user_auth;
create table if not exists tb_user_auth
(
    id              int auto_increment
        primary key,
    user_info_id    int          not null comment '用户信息id',
    username        varchar(50)  not null comment '用户名',
    password        varchar(100) not null comment '密码',
    login_type      tinyint(1)   not null comment '登录类型',
    ip_address      varchar(50)  null comment '用户登录ip',
    ip_source       varchar(50)  null comment 'ip来源',
    create_time     datetime     not null comment '创建时间',
    update_time     datetime     null comment '更新时间',
    last_login_time datetime     null comment '上次登录时间',
    constraint username
        unique (username)
);

drop table if exists tb_user_info;
create table if not exists tb_user_info
(
    id          int auto_increment comment '用户ID'
        primary key,
    email       varchar(50)              null comment '邮箱号',
    nickname    varchar(50)              not null comment '用户昵称',
    avatar      varchar(1024) default '' not null comment '用户头像',
    intro       varchar(255)             null comment '用户简介',
    web_site    varchar(255)             null comment '个人网站',
    is_disable  tinyint(1)    default 0  not null comment '是否禁用',
    create_time datetime                 not null comment '创建时间',
    update_time datetime                 null comment '更新时间'
);

drop table if exists tb_user_role;
create table if not exists tb_user_role
(
    id      int auto_increment
        primary key,
    user_id int null comment '用户id',
    role_id int null comment '角色id'
);

drop table if exists tb_website_config;
create table if not exists tb_website_config
(
    id          int auto_increment
        primary key,
    config      varchar(1000) null comment '配置信息',
    create_time datetime      not null comment '创建时间',
    update_time datetime      null comment '更新时间'
);

insert into tb_menu (id, name, path, component, icon, create_time, update_time, order_num, parent_id, is_hidden)
values (1, '首页', '/', '/home/Home.vue', 'el-icon-myshouye', '2021-01-26 17:06:51', '2021-01-26 17:06:53', 1, null, 0),
       (2, '文章管理', '/article-submenu', 'Layout', 'el-icon-mywenzhang-copy', '2021-01-25 20:43:07',
        '2021-01-25 20:43:09', 2, null, 0),
       (3, '消息管理', '/message-submenu', 'Layout', 'el-icon-myxiaoxi', '2021-01-25 20:44:17', '2021-01-25 20:44:20', 3,
        null, 0),
       (4, '系统管理', '/system-submenu', 'Layout', 'el-icon-myshezhi', '2021-01-25 20:45:57', '2021-01-25 20:45:59', 5,
        null, 0),
       (5, '个人中心', '/setting', '/setting/Setting.vue', 'el-icon-myuser', '2021-01-26 17:22:38', '2021-01-26 17:22:41',
        7, null, 0),
       (6, '发布文章', '/articles', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:30:48',
        '2021-01-26 14:30:51', 1, 2, 0),
       (7, '修改文章', '/articles/*', '/article/Article.vue', 'el-icon-myfabiaowenzhang', '2021-01-26 14:31:32',
        '2021-01-26 14:31:34', 2, 2, 1),
       (8, '文章列表', '/article-list', '/article/ArticleList.vue', 'el-icon-mywenzhangliebiao', '2021-01-26 14:32:13',
        '2021-01-26 14:32:16', 3, 2, 0),
       (9, '分类管理', '/categories', '/category/Category.vue', 'el-icon-myfenlei', '2021-01-26 14:33:42',
        '2021-01-26 14:33:43', 4, 2, 0),
       (10, '标签管理', '/tags', '/tag/Tag.vue', 'el-icon-myicontag', '2021-01-26 14:34:33', '2021-01-26 14:34:36', 5, 2,
        0),
       (11, '评论管理', '/comments', '/comment/Comment.vue', 'el-icon-mypinglunzu', '2021-01-26 14:35:31',
        '2021-01-26 14:35:34', 1, 3, 0),
       (12, '留言管理', '/messages', '/message/Message.vue', 'el-icon-myliuyan', '2021-01-26 14:36:09',
        '2021-01-26 14:36:13', 2, 3, 0),
       (13, '用户列表', '/users', '/user/User.vue', 'el-icon-myyonghuliebiao', '2021-01-26 14:38:09', '2021-01-26 14:38:12',
        1, 202, 0),
       (14, '角色管理', '/roles', '/role/Role.vue', 'el-icon-myjiaoseliebiao', '2021-01-26 14:39:01', '2021-01-26 14:39:03',
        2, 213, 0),
       (15, '接口管理', '/resources', '/resource/Resource.vue', 'el-icon-myjiekouguanli', '2021-01-26 14:40:14',
        '2021-08-07 20:00:28', 2, 213, 0),
       (16, '菜单管理', '/menus', '/menu/Menu.vue', 'el-icon-mycaidan', '2021-01-26 14:40:54', '2021-08-07 10:18:49', 2,
        213, 0),
       (17, '友链管理', '/links', '/friendLink/FriendLink.vue', 'el-icon-mydashujukeshihuaico-', '2021-01-26 14:41:35',
        '2021-01-26 14:41:37', 3, 4, 0),
       (18, '关于我', '/about', '/about/About.vue', 'el-icon-myguanyuwo', '2021-01-26 14:42:05', '2021-01-26 14:42:10', 4,
        4, 0),
       (19, '日志管理', '/log-submenu', 'Layout', 'el-icon-myguanyuwo', '2021-01-31 21:33:56', '2021-01-31 21:33:59', 6,
        null, 0),
       (20, '操作日志', '/operation/log', '/log/Operation.vue', 'el-icon-myguanyuwo', '2021-01-31 15:53:21',
        '2021-01-31 15:53:25', 1, 19, 0),
       (201, '在线用户', '/online/users', '/user/Online.vue', 'el-icon-myyonghuliebiao', '2021-02-05 14:59:51',
        '2021-02-05 14:59:53', 7, 202, 0),
       (202, '用户管理', '/users-submenu', 'Layout', 'el-icon-myyonghuliebiao', '2021-02-06 23:44:59',
        '2021-02-06 23:45:03', 4, null, 0),
       (205, '相册管理', '/album-submenu', 'Layout', 'el-icon-myimage-fill', '2021-08-03 15:10:54', '2021-08-07 20:02:06',
        5, null, 0),
       (206, '相册列表', '/albums', '/album/Album.vue', 'el-icon-myzhaopian', '2021-08-03 20:29:19', '2021-08-04 11:45:47',
        1, 205, 0),
       (208, '照片管理', '/albums/:albumId', '/album/Photo.vue', 'el-icon-myzhaopian', '2021-08-03 21:37:47',
        '2021-08-05 10:24:08', 1, 205, 1),
       (209, '页面管理', '/pages', '/page/Page.vue', 'el-icon-myyemianpeizhi', '2021-08-04 11:36:27', '2021-08-07 20:01:26',
        2, 4, 0),
       (210, '照片回收站', '/photos/delete', '/album/Delete.vue', 'el-icon-myhuishouzhan', '2021-08-05 13:55:19', null, 3,
        205, 1),
       (213, '权限管理', '/permission-submenu', 'Layout', 'el-icon-mydaohanglantubiao_quanxianguanli',
        '2021-08-07 19:56:55', '2021-08-07 19:59:40', 4, null, 0),
       (214, '网站管理', '/website', '/website/Website.vue', 'el-icon-myxitong', '2021-08-07 20:06:41', null, 1, 4, 0),
       (215, '说说管理', '/talk-submenu', 'Layout', 'el-icon-mypinglun', '2022-01-23 20:17:59', '2022-01-23 20:38:06', 5,
        null, 0),
       (216, '发布说说', '/talks', '/talk/Talk.vue', 'el-icon-myfabusekuai', '2022-01-23 20:18:43', '2022-01-23 20:38:19',
        1, 215, 0),
       (217, '说说列表', '/talk-list', '/talk/TalkList.vue', 'el-icon-myiconfontdongtaidianji', '2022-01-23 23:28:24',
        '2022-01-24 00:02:48', 2, 215, 0),
       (218, '修改说说', '/talks/:talkId', '/talk/Talk.vue', 'el-icon-myshouye', '2022-01-24 00:10:44', null, 3, 215, 1);

insert into tb_page (id, page_name, page_label, page_cover, create_time, update_time)
values (1, '首页', 'home', 'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-12-27 12:19:01'),
       (2, '归档', 'archive',
        'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-10-04 15:43:14'),
       (3, '分类', 'category',
        'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-10-04 15:43:31'),
       (4, '标签', 'tag', 'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-10-04 15:43:38'),
       (5, '相册', 'album', 'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-12-27 12:23:12'),
       (6, '友链', 'link', 'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-10-04 15:44:02'),
       (7, '关于', 'about', 'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-10-04 15:44:08'),
       (8, '留言', 'message',
        'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-10-04 16:11:45'),
       (9, '个人中心', 'user',
        'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-07 10:32:36', '2021-10-04 15:45:17'),
       (10, '文章列表', 'articleList',
        'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2021-08-10 15:36:19', '2021-10-04 15:45:45'),
       (11, '说说', 'talk', 'https://yinghuomars-blog.static.yinghuomars.xyz/config/a81071657bd9fdbef9728506da08a1b9.png',
        '2022-01-23 00:51:24', '2022-01-23 03:01:21');

insert into tb_resource (id, resource_name, url, request_method, parent_id, is_anonymous, create_time,
                         update_time)
values (165, '分类模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (166, '博客信息模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (167, '友链模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (168, '文章模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (169, '日志模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (170, '标签模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (171, '照片模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (172, '用户信息模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (173, '用户账号模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (174, '留言模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (175, '相册模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (176, '菜单模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (177, '角色模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (178, '评论模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (179, '资源模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (180, '页面模块', null, null, null, 0, '2021-08-11 21:04:21', null),
       (181, '查看博客信息', '/', 'GET', 166, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:29'),
       (182, '查看关于我信息', '/about', 'GET', 166, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:29'),
       (183, '查看后台信息', '/admin', 'GET', 166, 0, '2021-08-11 21:04:22', null),
       (184, '修改关于我信息', '/admin/about', 'PUT', 166, 0, '2021-08-11 21:04:22', null),
       (185, '查看后台文章', '/admin/articles', 'GET', 168, 0, '2021-08-11 21:04:22', null),
       (186, '添加或修改文章', '/admin/articles', 'POST', 168, 0, '2021-08-11 21:04:22', null),
       (187, '恢复或删除文章', '/admin/articles', 'PUT', 168, 0, '2021-08-11 21:04:22', null),
       (188, '物理删除文章', '/admin/articles', 'DELETE', 168, 0, '2021-08-11 21:04:22', null),
       (189, '上传文章图片', '/admin/articles/images', 'POST', 168, 0, '2021-08-11 21:04:22', null),
       (190, '修改文章置顶', '/admin/articles/top', 'PUT', 168, 0, '2021-08-11 21:04:22', null),
       (191, '根据id查看后台文章', '/admin/articles/*', 'GET', 168, 0, '2021-08-11 21:04:22', null),
       (192, '查看后台分类列表', '/admin/categories', 'GET', 165, 0, '2021-08-11 21:04:22', null),
       (193, '添加或修改分类', '/admin/categories', 'POST', 165, 0, '2021-08-11 21:04:22', null),
       (194, '删除分类', '/admin/categories', 'DELETE', 165, 0, '2021-08-11 21:04:22', null),
       (195, '搜索文章分类', '/admin/categories/search', 'GET', 165, 0, '2021-08-11 21:04:22', null),
       (196, '查询后台评论', '/admin/comments', 'GET', 178, 0, '2021-08-11 21:04:22', null),
       (197, '删除评论', '/admin/comments', 'DELETE', 178, 0, '2021-08-11 21:04:22', null),
       (198, '审核评论', '/admin/comments/review', 'PUT', 178, 0, '2021-08-11 21:04:22', null),
       (199, '查看后台友链列表', '/admin/links', 'GET', 167, 0, '2021-08-11 21:04:22', null),
       (200, '保存或修改友链', '/admin/links', 'POST', 167, 0, '2021-08-11 21:04:22', null),
       (201, '删除友链', '/admin/links', 'DELETE', 167, 0, '2021-08-11 21:04:22', null),
       (202, '查看菜单列表', '/admin/menus', 'GET', 176, 0, '2021-08-11 21:04:22', null),
       (203, '新增或修改菜单', '/admin/menus', 'POST', 176, 0, '2021-08-11 21:04:22', null),
       (204, '删除菜单', '/admin/menus/*', 'DELETE', 176, 0, '2021-08-11 21:04:22', null),
       (205, '查看后台留言列表', '/admin/messages', 'GET', 174, 0, '2021-08-11 21:04:22', null),
       (206, '删除留言', '/admin/messages', 'DELETE', 174, 0, '2021-08-11 21:04:22', null),
       (207, '审核留言', '/admin/messages/review', 'PUT', 174, 0, '2021-08-11 21:04:22', null),
       (208, '查看操作日志', '/admin/operation/logs', 'GET', 169, 0, '2021-08-11 21:04:22', null),
       (209, '删除操作日志', '/admin/operation/logs', 'DELETE', 169, 0, '2021-08-11 21:04:22', null),
       (210, '获取页面列表', '/admin/pages', 'GET', 180, 0, '2021-08-11 21:04:22', null),
       (211, '保存或更新页面', '/admin/pages', 'POST', 180, 0, '2021-08-11 21:04:22', null),
       (212, '删除页面', '/admin/pages/*', 'DELETE', 180, 0, '2021-08-11 21:04:22', null),
       (213, '根据相册id获取照片列表', '/admin/photos', 'GET', 171, 0, '2021-08-11 21:04:22', null),
       (214, '保存照片', '/admin/photos', 'POST', 171, 0, '2021-08-11 21:04:22', null),
       (215, '更新照片信息', '/admin/photos', 'PUT', 171, 0, '2021-08-11 21:04:22', null),
       (216, '删除照片', '/admin/photos', 'DELETE', 171, 0, '2021-08-11 21:04:22', null),
       (217, '移动照片相册', '/admin/photos/album', 'PUT', 171, 0, '2021-08-11 21:04:22', null),
       (218, '查看后台相册列表', '/admin/photos/albums', 'GET', 175, 0, '2021-08-11 21:04:22', null),
       (219, '保存或更新相册', '/admin/photos/albums', 'POST', 175, 0, '2021-08-11 21:04:22', null),
       (220, '上传相册封面', '/admin/photos/albums/cover', 'POST', 175, 0, '2021-08-11 21:04:22', null),
       (221, '获取后台相册列表信息', '/admin/photos/albums/info', 'GET', 175, 0, '2021-08-11 21:04:22', null),
       (222, '根据id删除相册', '/admin/photos/albums/*', 'DELETE', 175, 0, '2021-08-11 21:04:22', null),
       (223, '根据id获取后台相册信息', '/admin/photos/albums/*/info', 'GET', 175, 0, '2021-08-11 21:04:22', null),
       (224, '更新照片删除状态', '/admin/photos/delete', 'PUT', 171, 0, '2021-08-11 21:04:22', null),
       (225, '查看资源列表', '/admin/resources', 'GET', 179, 0, '2021-08-11 21:04:22', null),
       (226, '新增或修改资源', '/admin/resources', 'POST', 179, 0, '2021-08-11 21:04:22', null),
       (227, '导入swagger接口', '/admin/resources/import/swagger', 'GET', 179, 0, '2021-08-11 21:04:22', null),
       (228, '删除资源', '/admin/resources/*', 'DELETE', 179, 0, '2021-08-11 21:04:22', null),
       (229, '保存或更新角色', '/admin/role', 'POST', 177, 0, '2021-08-11 21:04:22', null),
       (230, '查看角色菜单选项', '/admin/role/menus', 'GET', 176, 0, '2021-08-11 21:04:22', null),
       (231, '查看角色资源选项', '/admin/role/resources', 'GET', 179, 0, '2021-08-11 21:04:22', null),
       (232, '查询角色列表', '/admin/roles', 'GET', 177, 0, '2021-08-11 21:04:22', null),
       (233, '删除角色', '/admin/roles', 'DELETE', 177, 0, '2021-08-11 21:04:22', null),
       (234, '查询后台标签列表', '/admin/tags', 'GET', 170, 0, '2021-08-11 21:04:22', null),
       (235, '添加或修改标签', '/admin/tags', 'POST', 170, 0, '2021-08-11 21:04:22', null),
       (236, '删除标签', '/admin/tags', 'DELETE', 170, 0, '2021-08-11 21:04:22', null),
       (237, '搜索文章标签', '/admin/tags/search', 'GET', 170, 0, '2021-08-11 21:04:22', null),
       (238, '查看当前用户菜单', '/admin/user/menus', 'GET', 176, 0, '2021-08-11 21:04:22', null),
       (239, '查询后台用户列表', '/admin/users', 'GET', 173, 0, '2021-08-11 21:04:22', null),
       (240, '修改用户禁用状态', '/admin/users/disable', 'PUT', 172, 0, '2021-08-11 21:04:22', null),
       (241, '查看在线用户', '/admin/users/online', 'GET', 172, 0, '2021-08-11 21:04:22', null),
       (242, '修改管理员密码', '/admin/users/password', 'PUT', 173, 0, '2021-08-11 21:04:22', null),
       (243, '查询用户角色选项', '/admin/users/role', 'GET', 177, 0, '2021-08-11 21:04:22', null),
       (244, '修改用户角色', '/admin/users/role', 'PUT', 172, 0, '2021-08-11 21:04:22', null),
       (245, '下线用户', '/admin/users/*/online', 'DELETE', 172, 0, '2021-08-11 21:04:22', null),
       (246, '获取网站配置', '/admin/website/config', 'GET', 166, 0, '2021-08-11 21:04:22', null),
       (247, '更新网站配置', '/admin/website/config', 'PUT', 166, 0, '2021-08-11 21:04:22', null),
       (248, '根据相册id查看照片列表', '/albums/*/photos', 'GET', 171, 1, '2021-08-11 21:04:22', '2021-08-11 21:06:35'),
       (249, '查看首页文章', '/articles', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:45'),
       (250, '查看文章归档', '/articles/archives', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:47'),
       (251, '根据条件查询文章', '/articles/condition', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:47'),
       (252, '搜索文章', '/articles/search', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:48'),
       (253, '根据id查看文章', '/articles/*', 'GET', 168, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:49'),
       (254, '点赞文章', '/articles/*/like', 'POST', 168, 0, '2021-08-11 21:04:22', null),
       (255, '查看分类列表', '/categories', 'GET', 165, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:26'),
       (256, '查询评论', '/comments', 'GET', 178, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:33'),
       (257, '添加评论', '/comments', 'POST', 178, 0, '2021-08-11 21:04:22', '2021-08-11 21:10:05'),
       (258, '评论点赞', '/comments/*/like', 'POST', 178, 0, '2021-08-11 21:04:22', null),
       (259, '查询评论下的回复', '/comments/*/replies', 'GET', 178, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:30'),
       (260, '查看友链列表', '/links', 'GET', 167, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:41'),
       (261, '查看留言列表', '/messages', 'GET', 174, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:14'),
       (262, '添加留言', '/messages', 'POST', 174, 0, '2021-08-11 21:04:22', '2022-12-08 00:55:44'),
       (263, '获取相册列表', '/photos/albums', 'GET', 175, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:20'),
       (264, '用户注册', '/register', 'POST', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:01'),
       (265, '查询标签列表', '/tags', 'GET', 170, 1, '2021-08-11 21:04:22', '2021-08-11 21:06:30'),
       (267, '更新用户头像', '/users/avatar', 'POST', 172, 0, '2021-08-11 21:04:22', null),
       (268, '发送邮箱验证码', '/users/code', 'GET', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:02'),
       (269, '绑定用户邮箱', '/users/email', 'POST', 172, 0, '2021-08-11 21:04:22', null),
       (270, '更新用户信息', '/users/info', 'PUT', 172, 0, '2021-08-11 21:04:22', null),
       (271, 'qq登录', '/users/oauth/qq', 'POST', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:06'),
       (272, '微博登录', '/users/oauth/weibo', 'POST', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:06'),
       (273, '修改密码', '/users/password', 'PUT', 173, 1, '2021-08-11 21:04:22', '2021-08-11 21:07:09'),
       (274, '上传语音', '/voice', 'POST', 166, 1, '2021-08-11 21:04:22', '2021-08-11 21:05:33'),
       (275, '上传访客信息', '/report', 'POST', 166, 1, '2021-08-24 00:32:05', '2021-08-24 00:32:07'),
       (276, '获取用户区域分布', '/admin/users/area', 'GET', 173, 0, '2021-08-24 00:32:35', '2021-09-24 16:25:34'),
       (278, '说说模块', null, null, null, 0, '2022-01-24 01:29:13', null),
       (279, '查看首页说说', '/home/talks', 'GET', 278, 1, '2022-01-24 01:29:29', '2022-01-24 01:31:56'),
       (280, '查看说说列表', '/talks', 'GET', 278, 1, '2022-01-24 01:29:52', '2022-01-24 01:31:56'),
       (281, '根据id查看说说', '/talks/*', 'GET', 278, 1, '2022-01-24 01:30:10', '2022-01-24 01:31:57'),
       (282, '点赞说说', '/talks/*/like', 'POST', 278, 0, '2022-01-24 01:30:30', null),
       (283, '上传说说图片', '/admin/talks/images', 'POST', 278, 0, '2022-01-24 01:30:46', null),
       (284, '保存或修改说说', '/admin/talks', 'POST', 278, 0, '2022-01-24 01:31:04', null),
       (285, '删除说说', '/admin/talks', 'DELETE', 278, 0, '2022-01-24 01:31:22', null),
       (286, '查看后台说说', '/admin/talks', 'GET', 278, 0, '2022-01-24 01:31:38', null),
       (287, '根据id查看后台说说', '/admin/talks/*', 'GET', 278, 0, '2022-01-24 01:31:53', '2022-01-24 01:33:14');

insert into tb_role (id, role_name, role_label, is_disable, create_time, update_time)
values (1, '管理员', 'admin', 0, '2021-03-22 14:10:21', '2022-01-24 01:32:26'),
       (2, '用户', 'user', 0, '2021-03-22 14:25:25', '2022-01-24 01:32:21');

insert into tb_role_menu (id, role_id, menu_id)
values (2461, 1, 1),
       (2462, 1, 2),
       (2463, 1, 6),
       (2464, 1, 7),
       (2465, 1, 8),
       (2466, 1, 9),
       (2467, 1, 10),
       (2468, 1, 3),
       (2469, 1, 11),
       (2470, 1, 12),
       (2471, 1, 202),
       (2472, 1, 13),
       (2473, 1, 201),
       (2474, 1, 213),
       (2475, 1, 14),
       (2476, 1, 15),
       (2477, 1, 16),
       (2478, 1, 4),
       (2479, 1, 214),
       (2480, 1, 209),
       (2481, 1, 17),
       (2482, 1, 18),
       (2483, 1, 205),
       (2484, 1, 206),
       (2485, 1, 208),
       (2486, 1, 210),
       (2487, 1, 215),
       (2488, 1, 216),
       (2489, 1, 217),
       (2490, 1, 218),
       (2491, 1, 19),
       (2492, 1, 20),
       (2493, 1, 5);

insert into tb_role_resource (id, role_id, resource_id)
values (4751, 2, 254),
       (4752, 2, 267),
       (4753, 2, 269),
       (4754, 2, 270),
       (4755, 2, 257),
       (4756, 2, 258),
       (4757, 2, 282),
       (4758, 1, 165),
       (4759, 1, 192),
       (4760, 1, 193),
       (4761, 1, 194),
       (4762, 1, 195),
       (4763, 1, 166),
       (4764, 1, 183),
       (4765, 1, 184),
       (4766, 1, 246),
       (4767, 1, 247),
       (4768, 1, 167),
       (4769, 1, 199),
       (4770, 1, 200),
       (4771, 1, 201),
       (4772, 1, 168),
       (4773, 1, 185),
       (4774, 1, 186),
       (4775, 1, 187),
       (4776, 1, 188),
       (4777, 1, 189),
       (4778, 1, 190),
       (4779, 1, 191),
       (4780, 1, 254),
       (4781, 1, 169),
       (4782, 1, 208),
       (4783, 1, 209),
       (4784, 1, 170),
       (4785, 1, 234),
       (4786, 1, 235),
       (4787, 1, 236),
       (4788, 1, 237),
       (4789, 1, 171),
       (4790, 1, 213),
       (4791, 1, 214),
       (4792, 1, 215),
       (4793, 1, 216),
       (4794, 1, 217),
       (4795, 1, 224),
       (4796, 1, 172),
       (4797, 1, 240),
       (4798, 1, 241),
       (4799, 1, 244),
       (4800, 1, 245),
       (4801, 1, 267),
       (4802, 1, 269),
       (4803, 1, 270),
       (4804, 1, 173),
       (4805, 1, 239),
       (4806, 1, 242),
       (4807, 1, 276),
       (4808, 1, 174),
       (4809, 1, 205),
       (4810, 1, 206),
       (4811, 1, 207),
       (4812, 1, 175),
       (4813, 1, 218),
       (4814, 1, 219),
       (4815, 1, 220),
       (4816, 1, 221),
       (4817, 1, 222),
       (4818, 1, 223),
       (4819, 1, 176),
       (4820, 1, 202),
       (4821, 1, 203),
       (4822, 1, 204),
       (4823, 1, 230),
       (4824, 1, 238),
       (4825, 1, 177),
       (4826, 1, 229),
       (4827, 1, 232),
       (4828, 1, 233),
       (4829, 1, 243),
       (4830, 1, 178),
       (4831, 1, 196),
       (4832, 1, 197),
       (4833, 1, 198),
       (4834, 1, 257),
       (4835, 1, 258),
       (4836, 1, 179),
       (4837, 1, 225),
       (4838, 1, 226),
       (4839, 1, 227),
       (4840, 1, 228),
       (4841, 1, 231),
       (4842, 1, 180),
       (4843, 1, 210),
       (4844, 1, 211),
       (4845, 1, 212),
       (4846, 1, 278),
       (4847, 1, 282),
       (4848, 1, 283),
       (4849, 1, 284),
       (4850, 1, 285),
       (4851, 1, 286),
       (4852, 1, 287);

insert into tb_user_auth (id, user_info_id, username, password, login_type, ip_address, ip_source,
                          create_time, update_time, last_login_time)
values (1, 1, 'admin@qq.com', '$2a$10$1zov7.1BqbdJrLr4gZ9qrOvl3zRHOxLs8JXHZq60MGZxEptSwO2KC', 1, '127.0.0.1',
        '本地局域网', '2021-08-12 15:43:18', '2022-12-10 22:25:22', '2022-12-10 22:25:22');

insert into tb_user_info (id, email, nickname, avatar, intro, web_site, is_disable, create_time,
                          update_time)
values (1, 'admin@qq.com', '管理员',
        'https://yinghuomars-blog.static.yinghuomars.xyz/avatar/0058e1ad9a55c67bcd9aafc9c674c18a.jpg', '无~',
        'https://www.yinghuomars.xyz', 0, '2021-08-12 15:43:17', '2022-12-10 09:01:00');

insert into tb_user_role (id, user_id, role_id)
values (1, 1, 1);

insert into tb_website_config (id, config, create_time, update_time)
values (1,
        '{"alipayQRCode":"","gitee":"","github":"","isChatRoom":1,"isCommentReview":0,"isEmailNotice":1,"isMessageReview":0,"isMusicPlayer":1,"isReward":0,"qq":"","socialLoginList":["qq","weibo"],"socialUrlList":[],"touristAvatar":"https://yinghuomars-blog.static.yinghuomars.xyz/config/f094a5f78b835c4d09de3d0af2a960b3.jpg","userAvatar":"https://yinghuomars-blog.static.yinghuomars.xyz/config/87d6468ac5d0a6230298fe84ff54dad1.jpg","websiteAuthor":"荧惑Mars","websiteAvatar":"https://yinghuomars-blog.static.yinghuomars.xyz/config/ab9477101f2e4179c6a540a29d2a1738.jpg","websiteCreateTime":"2022-12-01","websiteIntro":"雪霁银装素，桔高映琼枝。","websiteName":"荧惑Mars","websiteNotice":"这里是荧惑Mars的个人网站","websiteRecordNo":"湘ICP备2021016617号","websocketUrl":"wss://www.yinghuomars.xyz/wss/websocket","weiXinQRCode":""}',
        '2021-08-09 19:37:30', '2022-12-11 00:41:50');

commit;