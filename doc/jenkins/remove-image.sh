#!/bin/bash

echo "检查 $1 镜像是否存在"
imageid=`docker images | grep $1 | awk '{print $3}'`
if [ "$imageid" != "" ];then
    echo "删除 $1 镜像"
    docker rmi -f $imageid
fi

