FROM openjdk:17-alpine3.14

# 作者信息
MAINTAINER jie.luo Docker summary-goods "luoj@ajzxjk.com"

# 修改源
RUN echo "https://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && \
    echo "https://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories

# 安装需要的软件，解决时区问题
RUN apk --update add curl bash tzdata --allow-untrusted && \
    rm -rf /var/cache/apk/*

#修改镜像为东八区时间
ENV TZ Asia/Shanghai

# jar 包
ARG JAR_FILE

COPY ${JAR_FILE} app.jar

# 暴露端口号
EXPOSE 10300
# 执行命令
ENTRYPOINT ["java","-jar","/app.jar"]








