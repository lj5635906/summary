FROM openjdk:17-alpine3.14

# 作者信息
MAINTAINER jie.luo Docker summary-gateway "luoj@ajzxjk.com"

ARG JAR_FILE

COPY ${JAR_FILE} /summary/app.jar

# 暴露端口号
EXPOSE 8080

CMD ["--server.port=8400"]

# 执行命令
ENTRYPOINT ["nohup","java","-jar","/summary/app.jar","&"]

# build
# docker build --rm -t summary/summary-goods:v1 --build-arg JAR_FILE=summary-goods-1.0-SNAPSHOT.jar .

# docker run -itd --name summary-goods -p 10300:10300 summary/summary-goods:v1








