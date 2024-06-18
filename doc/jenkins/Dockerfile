FROM jenkins/jenkins:2.235-alpine

# 作者信息
MAINTAINER jie.luo Docker jenkins "luoj@ajzxjk.com"

USER root

#修改镜像为东八区时间
ENV TZ Asia/Shanghai
ENV JENKINS_UC https://updates.jenkins-zh.cn
ENV JENKINS_UC_DOWNLOAD https://mirrors.tuna.tsinghua.edu.cn/jenkins
ENV JENKINS_OPTS="-Dhudson.model.UpdateCenter.updateCenterUrl=https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/2.235/update-center.json"
ENV JENKINS_OPTS="-Djenkins.install.runSetupWizard=false"
COPY init.groovy /usr/share/jenkins/ref/init.groovy.d/init.groovy
COPY hudson.model.UpdateCenter.xml /usr/share/jenkins/ref/hudson.model.UpdateCenter.xml
COPY mirror-adapter.crt /usr/share/jenkins/ref/mirror-adapter.crt



# docker build --rm -t summary/jenkins:2.235-alpine .

