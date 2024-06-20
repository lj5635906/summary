#!/bin/bash


echo "检查 $1 容器是否存在"
containerid=`docker ps -a | grep -w $1 | awk '{print $1}'`
if [ "$containerid" != "" ];then
        echo "$1 容器存在,停止容器"
        docker stop $containerid
        echo "删除 $1 容器"
        docker rm $containerid
fi



echo "检查 $1 镜像是否存在"
imageid=`docker images | grep $1 | awk '{print $3}'`
if [ "$imageid" != "" ];then
    echo "删除 $1 镜像"
    docker rmi -f $imageid
fi



