# 项目部署（docker）

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

```shell
docker pull openjdk:11.0.16-jre
docker pull nginx:1.22.1
docker pull rabbitmq:3.11.4-management
docker pull redis:6.2.7
docker pull mysql:8.0.31
```

### 可选下载项

```shell
docker pull elasticsearch:7.9.3
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
docker run -itd --name blog-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=blog#123 mysql:8.0.31
```

## 启动rabbitmq

### 创建数据存储目录

```shell
mkdir /opt/blog/rabbitmq/{config,data,log}
```

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

### 挂载宿主机目录并新建容器

```shell
docker run -itd --name blog-rabbitmq -p 4369:4369 -p 5671:5671 -p 5672:5672 -p 15671:15671 -p 15672:15672 -p 25672:25672 -v /opt/blog/rabbitmq/config:/etc/rabbitmq -v /opt/blog/rabbitmq/data:/var/lib/rabbitmq -v /opt/blog/rabbitmq/log:/var/log/rabbitmq rabbitmq:3.11.4-management
```

### 启动后通过访问宿主机的`15672`端口进入web管理页面进行账号修改

```text
默认账号：guest
默认密码：guest
```

## 启动redis

### 创建数据存储目录

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

### 挂载宿主机目录并新建容器

```shell
docker run -itd --name blog-redis -p 6379:6379 -p 16379:16379 -v /opt/blog/redis/redis.conf:/etc/redis/redis.conf -v /opt/blog/redis/data:/data:rw --privileged=true redis:6.2.7 redis-server /etc/redis/redis.conf --appendonly yes
```

## 启动nginx

### 创建数据存储目录

```shell
mkdir /opt/blog/nginx/webapps/root
```

### 添加nginx配置文件

```text
/opt/blog/nginx/nginx.conf
```

### 挂载宿主机目录并新建容器

```shell
docker run -itd --name blog-nginx -p 80:80 -p 443:443 -p 8080:8080 -p 18080:18080 -p 10080:10080 -v /opt/blog/nginx/nginx.conf:/etc/nginx/nginx.conf -v /opt/blog/nginx/webapps:/home/webapps nginx:1.22.1
```

## 启动elasticsearch

### 启动容器

```shell
docker run -itd --name blog-elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m" elasticsearch:7.9.3
```

### 进入容器，下载ik分词器，修改账户密码

```shell
docker exec -it blog-elasticsearch /bin/bash
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.9.3/elasticsearch-analysis-ik-7.9.3.zip
elasticsearch-setup-passwords interactive
```

### 重启容器

```shell
docker restart blog-elasticsearch
```

### 创建索引

`PUT:http://宿主机ip:9200/article`

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

## 启动maxwell

```shell
docker run -itd --name blog-maxwell --restart=always zendesk/maxwell:v1.39.4 bin/maxwell  --user='数据库用户名' --password='数据库密码'  --host='数据库地址'  --producer=rabbitmq --rabbitmq_user='MQ用户名' --rabbitmq_pass='MQ密码' --rabbitmq_host='MQ地址' --rabbitmq_port='MQ端口（5672）' --rabbitmq_exchange='maxwell_exchange'  --rabbitmq_exchange_type='fanout' --rabbitmq_exchange_durable='true' --filter='exclude: *.*, include: microblog.tb_article.article_title = *, include: microblog.tb_article.article_content = *, include: microblog.tb_article.is_delete = *, include: microblog.tb_article.status = *' 
```

## 启动项目

```shell
docker run -itd --name yinghuomars-microblog -p 8081:8080 yinghuomars.com/yinghuomars-microblog:v1.0.0
```