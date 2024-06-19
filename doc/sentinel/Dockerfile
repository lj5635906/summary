FROM openjdk:17-alpine3.14

# 作者信息
MAINTAINER jie.luo Docker sentinel "luoj@ajzxjk.com"

# 修改源
RUN echo "https://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && \
    echo "https://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories

# 安装需要的软件，解决时区问题
RUN apk --update add curl bash tzdata --allow-untrusted && \
    rm -rf /var/cache/apk/*

#修改镜像为东八区时间
ENV TZ Asia/Shanghai
ENV JAVA_OPTS="-server -Xms256m -Xmx512m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m"

WORKDIR /usr/local/sentinel

ADD sentinel-dashboard-1.8.8.jar  /usr/local/sentinel/app.jar

# 暴露端口号
EXPOSE 8080
# 执行命令
ENTRYPOINT ["java","-jar","/usr/local/sentinel/app.jar"]

# build
# docker build --rm -t summary/sentinel-dashboard:1.8.8 .

# docker run -itd --restart=always --name sentinel-dashboard -p 8858:8080 summary/sentinel-dashboard:1.8.8








