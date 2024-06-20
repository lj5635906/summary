FROM openjdk:17-alpine3.14

# 作者信息
MAINTAINER jie.luo Docker summary-gateway "luoj@ajzxjk.com"

ARG JAR_FILE

COPY ${JAR_FILE} /summary/app.jar

# 暴露端口号
EXPOSE 5000

# 执行命令
ENTRYPOINT ["nohup","java","-jar","/summary/app.jar","&"]