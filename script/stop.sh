#!/bin/bash
proc_name="alipay-service.jar"
name_suffixx="\>"
proc_id=`ps -ef|grep -i ${proc_name}${name_suffixx}|grep -v "grep"|awk '{print $2}'`
if [[ -z $proc_id ]];then
    echo "The task is not running ! "
else
     echo ${proc_name}" pid:"
     echo ${proc_id[@]}
     echo "------kill the task!------"
     for id in ${proc_id[*]}
     do
       echo ${id}
       thread=`ps -mp ${id}|wc -l`
       echo "threads number: "${thread}
       kill -9 ${id}

       if [ $? -eq 0 ];then

            echo "task is killed ..."
       else
            echo "kill task failed "
       fi
     done
fi