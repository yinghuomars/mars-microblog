## docker安装

### 下载

```shell
curl -fsSL https://get.docker.com | bash -s docker
```

### 启动

```shell
systemctl start docker
```

### 开机自启

```shell
systemctl enable docker
```

## 项目组件镜像下载

### 必下载项

#### jdk（后端项目运行环境）

```shell
docker pull openjdk:11.0.16-jre
```

#### 网络代理（转发请求）

```shell
docker pull nginx:1.22.1
```

#### 消息队列（邮件、同步es）

```shell
docker pull rabbitmq:3.11.4-management
```

#### 缓存（网站访问量、验证码等缓存）

```shell
docker pull redis:6.2.7
```

#### 数据库（数据持久化）

```shell
docker pull mysql:8.0.31
```

### 可选下载项

#### elasticsearch（搜索）

```shell
docker pull elasticsearch:7.9.3
```

#### maxwell（同步数据库数据到消息队列）

```shell
docker pull zendesk/maxwell:v1.39.4
```

### 查看镜像

```shell
docker images
```

| REPOSITORY      | TAG               | IMAGE ID     | SIZE  |
|-----------------|-------------------|--------------|-------|
| zendesk/maxwell | v1.39.4           | 8e71c37842b8 | 589MB |
| mysql           | 8.0.31            | a3a2968869cf | 538MB |
| rabbitmq        | 3.11.4-management | 789501296640 | 263MB |
| redis           | 6.2.7             | 48da0c367062 | 113MB |
| nginx           | 1.22.1            | 404359394820 | 142MB |
| openjdk         | 11.0.16-jre       | 362cda5d270e | 302MB |
| elasticsearch   | 7.9.3             | 1ab13f928dc8 | 742MB |

## 启动mysql

```shell
docker run -itd --name blog-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=Blog123@mysql mysql:8.0.31
```

> `root` 为数据库账号
>
> `Blog123@mysql` 为数据库密码

## 启动rabbitmq

> 需要先在宿主机创建存储目录，然后启动临时容器，将需要的数据从临时容器中复制到宿主机的存储目录，然后删除临时容器，最后启动新的容器并挂载宿主机的存储目录

### 创建存储目录

```shell
mkdir /opt/blog/rabbitmq/{config,data,log}
```

> `config` 存储配置
>
> `data` 存储数据
>
> `log` 存储日志

### 启动临时容器

```shell
docker run -itd --name blog-rabbitmq rabbitmq:3.11.4-management
```

### 复制临时容器中的相关文件到宿主机

```shell
docker cp blog-rabbitmq:/etc/rabbitmq /opt/blog/rabbitmq/config
docker cp blog-rabbitmq:/var/lib/rabbitmqq /opt/blog/rabbitmq/data
docker cp blog-rabbitmq:/var/log/rabbitmq /opt/blog/rabbitmq/log

mv /opt/blog/rabbitmq/config/rabbitmq /opt/blog/rabbitmq/config
rm -rf /opt/blog/rabbitmq/config/rabbitmq

mv /opt/blog/rabbitmq/data/rabbitmq /opt/blog/rabbitmq/data
rm -rf /opt/blog/rabbitmq/data/rabbitmq

mv /opt/blog/rabbitmq/log/rabbitmq /opt/blog/rabbitmq/log
rm -rf /opt/blog/rabbitmq/log/rabbitmq
```

### 删除临时容器

```shell
docker stop blog-rabbitmq
docker rm blog-rabbitmq
```

### 新建容器并挂载宿主机存储目录（二选一）

#### 开放必须端口-推荐

```shell
docker run -itd --name blog-rabbitmq -p 5672:5672 -p 15672:15672 -v /opt/blog/rabbitmq/config:/etc/rabbitmq -v /opt/blog/rabbitmq/data:/var/lib/rabbitmq -v /opt/blog/rabbitmq/log:/var/log/rabbitmq rabbitmq:3.11.4-management
```

#### 开放所有相关端口

```shell
docker run -itd --name blog-rabbitmq -p 4369:4369 -p 5671:5671 -p 5672:5672 -p 15671:15671 -p 15672:15672 -p 25672:25672 -v /opt/blog/rabbitmq/config:/etc/rabbitmq -v /opt/blog/rabbitmq/data:/var/lib/rabbitmq -v /opt/blog/rabbitmq/log:/var/log/rabbitmq rabbitmq:3.11.4-management
```

### 启动后通过访问宿主机的`15672`端口进入web管理页面进行账号修改

```text
默认账号：guest
默认密码：guest
```

#### 建议新增一个账号，然后删除guest账号

```text
新账号：admin
新密码：Blog123@rabbitmq
```

## 启动redis

> 需要先在宿主机创建存储目录，然后启动临时容器，将需要的数据从临时容器中复制到宿主机的存储目录，然后删除临时容器，最后启动新的容器并挂载宿主机的存储目录

### 创建存储目录

```shell
mkdir /opt/blog/redis/data
```

### 启动临时容器

```shell
docker run -itd --name blog-redis --privileged=true redis:6.2.7 redis-server /etc/redis/redis.conf --appendonly yes
```

### 复制临时容器中的相关文件到宿主机

```shell
docker cp blog-redis:/data /opt/blog/redis/
docker cp blog-redis:/etc/redis/redis.conf /opt/blog/redis/
```

### 删除临时容器

```shell
docker stop blog-redis
docker rm blog-redis
```

### 修改redis配置（/opt/blog/redis/redis.conf）

#### 可以删除所有的配置，然后将下面的粘贴后报错

```
protected-mode no
daemonize no
databases 4
dir  ./
appendonly yes
logfile "access.log"
requirepass Blog123@redis
```

> `requirepass` 的值 `Blog123@redis` 是连接密码（默认不需要密码）
>
> `databases` 的值 `4` 是数据库数量（默认是16个，不需要那么多）

### 新建容器并挂载宿主机存储目录

```shell
docker run -itd --name blog-redis -p 6379:6379 -p 16379:16379 -v /opt/blog/redis/redis.conf:/etc/redis/redis.conf -v /opt/blog/redis/data:/data:rw --privileged=true redis:6.2.7 redis-server /etc/redis/redis.conf --appendonly yes
```

## 启动nginx

> 需要先在宿主机创建存储目录，然后启动临时容器，将需要的数据从临时容器中复制到宿主机的存储目录，然后删除临时容器，最后启动新的容器并挂载宿主机的存储目录

### 创建存储目录

```shell
mkdir /opt/blog/nginx/webapps/{blog,admin,static}
```

> vue打包前端会有两个模块,一个是 `blog` ,一个是 `admin` ,打包后分别是两个 `dist` 文件夹

> 将 `blog` 模块的 `dist` 文件夹下的内容放到 `/opt/blog/nginx/webapps/blog/`
>
> 将 `admin` 模块的 `dist` 文件夹下的内容放到 `/opt/blog/nginx/webapps/admin/`
>
> `static` 文件夹用来存放后续产生的文件

### 添加nginx配置文件

```text
vi /opt/blog/nginx/nginx.conf
```

#### nginx的配置参考：

```nginx configuration
worker_processes  1;
events {
    worker_connections    256;
}
http {
    include                     mime.types;
    default_type                application/octet-stream;
    sendfile                    on;
    keepalive_timeout           60;
    gzip                        on;
    client_max_body_size        5m;
#     前台 后端接口 websocket 静态文件
    server {
        listen                      80;
#         前台页面
        location / {
            root                    /home/webapps/blog;
            try_files               $uri                        $uri/                 @router;
            index                   index.html                  index.htm;
        }
        location @router {
            rewrite                 ^.*$ /index.html            last;
        }
#         后端接口
        location /api/ {
            proxy_pass              http://192.168.229.130:8080/;
        }
#         websocket
        location /wss/ {
            proxy_pass              http://192.168.229.130:8080/;
            proxy_http_version      1.1;
            proxy_set_header        Upgrade                     $http_upgrade;
            proxy_set_header        Connection                  "Upgrade";
            proxy_set_header        X-Real-IP                   $remote_addr;
        }
#         静态文件
        location /static/ {
            root                    /home/webapps/static;
        }
    }
#     后台
    server {
        listen                  443;
        location / {
            root                /home/webapps/admin;
            try_files           $uri                     $uri/                 @router;
            index               index.html               index.htm;
        }
        location @router {
            rewrite             ^.*$ /index.html         last;
        }
    }
}
```

### 挂载宿主机目录并新建容器

```shell
docker run -itd --name blog-nginx -p 80:80 -p 443:443 -v /opt/blog/nginx/nginx.conf:/etc/nginx/nginx.conf -v /opt/blog/nginx/webapps:/home/webapps nginx:1.22.1
```

## 启动elasticsearch

> 需要先在宿主机创建存储目录，然后启动临时容器，将需要的数据从临时容器中复制到宿主机的存储目录，然后删除临时容器，最后启动新的容器并挂载宿主机的存储目录

### 创建存储目录

```shell
mkdir /opt/blog/elasticsearch
```

> `plugins` 存储插件

### 启动临时容器

```shell
docker run -itd --name blog-elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" elasticsearch:7.9.3
```

### 复制临时容器中的相关文件到宿主机

```shell
docker cp blog-elasticsearch:/usr/share/elasticsearch/config /opt/blog/elasticsearch/
docker cp blog-elasticsearch:/usr/share/elasticsearch/data /opt/blog/elasticsearch/
docker cp blog-elasticsearch:/usr/share/elasticsearch/logs /opt/blog/elasticsearch/
docker cp blog-elasticsearch:/usr/share/elasticsearch/plugins /opt/blog/elasticsearch/
```

### 删除临时容器

```shell
docker stop blog-elasticsearch
docker rm blog-elasticsearch
```

### 新建容器并挂载宿主机存储目录

```shell
docker run -d --name blog-elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx256m" -v /opt/blog/elasticsearch/config:/usr/share/elasticsearch/config -v /opt/blog/elasticsearch/logs:/usr/share/elasticsearch/logs -v /opt/blog/elasticsearch/data:/usr/share/elasticsearch/data -v /opt/blog/elasticsearch/plugins:/usr/share/elasticsearch/plugins elasticsearch:7.9.3
```

### 进入容器，下载ik分词器，修改账户密码

```shell
docker exec -it blog-elasticsearch /bin/bash
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.9.3/elasticsearch-analysis-ik-7.9.3.zip
elasticsearch-setup-passwords interactive
......
exit
```

> ik分词器版本必须要和es版本完全一致
>
> 此处将所有账号密码都设为 `Blog123@elastic`

### 重启容器

```shell
docker restart blog-elasticsearch
```

### 创建索引

`PUT` `http://192.168.229.130:9200/article`

> 使用postman发起请求时，授权使用 `basic auth` 将账号密码填进去
>
> 此处 `192.168.229.130` 为宿主机ip（可在宿主机执行 `ip addr` 或 `ifconfig` 获取）

#### 请求体如下

```json
{
  "mappings": {
    "properties": {
      "id": {
        "type": "integer"
      },
      "articleTitle": {
        "type": "text",
        "analyzer": "ik_max_word"
      },
      "articleContent": {
        "type": "text",
        "analyzer": "ik_max_word"
      },
      "isDelete": {
        "type": "integer"
      },
      "status": {
        "type": "integer"
      }
    }
  }
}
```

#### 响应体如下

```json
{
  "acknowledged": true,
  "shards_acknowledged": true,
  "index": "article"
}
```

## 启动maxwell

> maxwell会创建一个名为 `maxwell` 的新数据库用来保存同步信息，所以说maxwell所使用的数据库账户需要具有新建数据库的权限

### 启动maxwell（二选一）

#### 命令行配置启动

```shell
docker run --name maxwell --restart=always -itd zendesk/maxwell:v1.39.4 bin/maxwell  --user='root' --password='Blog123@mysql'  --host='192.168.229.130'  --producer=rabbitmq --rabbitmq_user='admin' --rabbitmq_pass='Blog123@rabbitmq' --rabbitmq_host='192.168.229.130' --rabbitmq_port='5672' --rabbitmq_exchange='maxwell_exchange'  --rabbitmq_exchange_type='fanout' --rabbitmq_exchange_durable='true' --filter='exclude: *.*, include: microblog.tb_article.article_title = *, include: microblog.tb_article.article_content = *, include: microblog.tb_article.is_delete = *, include: microblog.tb_article.status = *'
```

#### 挂载配置文件启动-推荐

> maxwell配置文件宿主机路径：`/opt/blog/maxwell/config/config.properties`

```properties
log_level=info
producer=rabbitmq
rabbitmq_host=192.168.229.130
rabbitmq_port=5672
rabbitmq_user=admin
rabbitmq_pass=Blog123@rabbitmq
rabbitmq_exchange=maxwell_exchange
rabbitmq_exchange_type=fanout
rabbitmq_exchange_durable=true
host=192.168.229.130
user=root
password=Blog123@mysql
filter=exclude: *.*,include: microblog.tb_article.article_title = *,include: microblog.tb_article.article_content = *,include: microblog.tb_article.is_delete = *,include: microblog.tb_article.status = *
client_id=maxwell
```

```shell
docker run -itd --name maxwell -v /opt/blog/maxwell/config/config.properties:/app/config.properties --restart=always zendesk/maxwell:v1.39.4 bin/maxwell
```

### 启动maxwell-bootstrap（全量导入数据）

```shell
docker run -itd --rm --name maxwell-bootstrap zendesk/maxwell:v1.39.4 bin/maxwell-bootstrap --database 'microblog' --table 'tb_article' --host '192.168.229.130' --user 'root' --password 'Blog123@mysql' --client_id maxwell --log_level info
```

## 启动项目

> 启动参数如下

| 参数                       | 默认值                      | 描述                        | 是否必须                 |
|--------------------------|--------------------------|---------------------------|----------------------|
| MULT_MAX_SIZE            | 5MB                      | 最大文件大小                    | 是                    |
| MULT_MAX_REQ             | 20MB                     | 单次请求的最大大小                 | 是                    |
| DATABASE_HOST            | 127.0.0.1                | 数据库地址                     | 是                    |
| DATABASE_PORT            | 3306                     | 数据库端口                     | 是                    |
| DATABASE_DB              | microblog                | 数据库名                      | 是                    |
| DATABASE_USERNAME        | root                     | 数据库用户名                    | 是                    |
| DATABASE_PASSWORD        | Blog123@mysql            | 数据库密码                     | 是                    |
| ES_URI                   | http://127.0.0.1:9200    | es url                    | 是                    |
| ES_USERNAME              | elastic                  | es用户名                     | 是                    |
| ES_PASSWORD              | Blog123@elastic          | es密码                      | 是                    |
| ES_READ_TIMEOUT          | 10s                      | es查询超时时间                  | 是                    |
| ES_CONN_TIMEOUT          | 5s                       | es连接超时时间                  | 是                    |
| MAIL_TEST                | true                     | 邮箱测试链接                    | 是                    |
| MAIL_HOST                | smtp.qq.com              | 邮件服务主机地址                  | 是                    |
| MAIL_PORT                | 587                      | 邮件服务主机端口                  | 是                    |
| MAIL_NICKNAME            | 荧惑Mars                   | 发送邮件时昵称                   | 是                    |
| MAIL_USERNAME            | admin@qq.com             | 邮箱号                       | 是                    |
| MAIL_PASSWORD            | ''                       | 邮箱授权码                     | 是                    |
| RABBITMQ_HOST            | 127.0.0.1                | rabbitmq地址                | 是                    |
| RABBITMQ_PORT            | 5672                     | rabbitmq端口                | 是                    |
| RABBITMQ_USERNAME        | admin                    | rabbitmq用户名               | 是                    |
| RABBITMQ_PASSWORD        | Blog123@rabbitmq         | rabbitmq密码                | 是                    |
| REDIS_HOST               | 127.0.0.1                | redis地址                   | 是                    |
| REDIS_PORT               | 5672                     | redis端口                   | 是                    |
| REDIS_PWD                | admin                    | redis密码                   | 是                    |
| REDIS_DATABASE           | Blog123@redis            | redis数据库                  | 是                    |
| MANAGE_HEALTH_ES         | true                     | 检查es连接                    | 是                    |
| MANAGE_HEALTH_MAIL       | true                     | 检查mail连接                  | 是                    |
| WEBSITE_URL              | http://localhost         | 网站域名（用于邮件通知）              | 是                    |
| UPLOAD_MODE              | local                    | 上传策略（local或oss）           | 是                    |
| UPLOAD_LOCAL_BASEURL     | http://127.0.0.1/static/ | local-本地上传的访问路径           | UPLOAD_MODE为lcoal时必填 |
| UPLOAD_LOCAL_BASEPATH    | /home/static/            | local-本地上传的存储目录           | UPLOAD_MODE为lcoal时必填 |
| UPLOAD_OSS_URL           | ''                       | oss-访问路径                  | UPLOAD_MODE为oss时必填   |
| UPLOAD_OSS_ACCESS_ID     | ''                       | oss-访问密钥Id                | UPLOAD_MODE为oss时必填   |
| UPLOAD_OSS_ACCESS_SECRET | ''                       | oss-访问密钥Secret            | UPLOAD_MODE为oss时必填   |
| UPLOAD_OSS_ENDPOINT      | ''                       | oss-端点                    | UPLOAD_MODE为oss时必填   |
| UPLOAD_OSS_BUCKET        | ''                       | oss-bucket名称              | UPLOAD_MODE为oss时必填   |
| SEARCH_MODE              | mysql                    | 搜索模式（mysql或elasticsearch） | 是                    |
| PLATFORM_QQ_APPID        | ''                       | QQ appId                  | 是                    |

```shell
docker run -itd --name yinghuomars-microblog -p 8080:8080 yinghuomars.com/yinghuomars-microblog:v1.0.0
```