#!/usr/bin/env bash

# 删除原有service-user-center镜像
docker rmi service-user-center:0.0.1-SNAPSHOT
# 将model、utils、common基础工程打包，上传到maven本地仓库
mvn ‐f ../framework/framework-model/pom.xml install
mvn ‐f ../framework/framework-common/pom.xml install
mvn ‐f ../framework/framework-util/pom.xml install
# 使用maven插件构建docker镜像
mvn ‐f pom_docker.xml clean package ‐DskipTests docker:build
# 修改镜像的组织名，以便推送到个人组织内，注意这里bebopze-sc‐cloud‐01是新添加的组织名
# 执行docker push 将本地服务器上的镜像推送到云平台镜像仓库
