#!/usr/bin/env bash

rsync -avz project/cloud/spring-cloud-alibaba/service/service-user-center/target/service-user-center.jar /usr/local/cloud/spring-cloud-alibaba/
rsync -avz --delete /usr/local/cloud/spring-cloud-alibaba/ host1:/usr/local/cloud/spring-cloud-alibaba/
ssh -f host1 /usr/local/cloud/spring-cloud-alibaba/stop.sh
sleep 5
ssh -f host1 /usr/local/cloud/spring-cloud-alibaba/start.sh &