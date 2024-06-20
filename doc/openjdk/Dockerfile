FROM openjdk:17-alpine3.14

# 作者信息
MAINTAINER jie.luo Docker openjdk "luoj@ajzxjk.com"

# 修改源
RUN echo "https://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && \
    echo "https://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories

# 安装需要的软件，解决时区问题
RUN apk --update add curl bash tzdata --allow-untrusted && \
    rm -rf /var/cache/apk/*

#修改镜像为东八区时间
ENV TZ Asia/Shanghai


# build
# docker build --rm -t summary/openjdk:17-alpine3.14 .

# docker run -itd --name summary-openjdk summary/openjdk:17-alpine3.14 .








