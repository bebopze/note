#!/bin/bash
cd /usr/local/alipay-life/service
>nohup.out
nohup java -Xms10m -Xmx500m -jar alipay-life-service.jar --spring.profiles.active=pub &
proc_name="alipay-life-service.jar"
name_suffix="\>"
proc_id=`ps -ef|grep -i ${proc_name}${name_suffix}|grep -v "grep"|awk '{print $2}'`
echo ${proc_name}" pid:"echo ${proc_id[@]}