# 基础镜像
FROM openjdk:11.0.16-jre

ADD blog-springboot.jar /home/blog-springboot.jar

# 开放端口
EXPOSE 8080

# 启动容器
ENTRYPOINT ["java","-jar","/home/blog-springboot.jar"]