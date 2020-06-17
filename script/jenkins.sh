#!/usr/bin/env bash

rsync -avz project/cloud/sca/service/user-service/target/user-service.jar /usr/local/cloud/sca/
rsync -avz --delete /usr/local/cloud/sca/ host1:/usr/local/cloud/sca/
ssh -f host1 /usr/local/cloud/sca/stop.sh
sleep 5
ssh -f host1 /usr/local/cloud/sca/start.sh &


scp -o StrictHostKeyChecking=no user-api/target/user-api.jar root@127.0.0.1:/root/docker/user-api.jar
time=$(date +%s)
ssh root@127.0.0.1 > /dev/null 2>&1 << EOF
cd /root/docker
docker build -t 127.0.0.1:5000/user-api:$time .
docker push 127.0.0.1:5000/user-api:$time
kubectl patch pod test -p '{"spec":{"containers":[{"name":"test","image":"127.0.0.1:5000/user-api:$time"}]}}'
exit
EOF