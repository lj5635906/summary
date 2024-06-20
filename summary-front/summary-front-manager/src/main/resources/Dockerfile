FROM openjdk:17-alpine3.14

# 作者信息
MAINTAINER jie.luo Docker summary-front-manager "luoj@ajzxjk.com"

ARG JAR_FILE

COPY ${JAR_FILE} /summary/app.jar

# 暴露端口号
EXPOSE 20100
# 执行命令
ENTRYPOINT ["java","-jar","/summary/app.jar",">","/dev/null","&"]