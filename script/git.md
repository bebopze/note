
### 1.删除远程分支(已push)的commit

```

   方式一：IDEA操作
    
        a、选中要回退到的commit，右键点击 Reset Current Branch to Here
        
            - 此时你会发现，选中之前的远程commit已全部置白
        
        b、push时，选中Force Push
        
            - 此时你会发现，远程commit已经彻底消失
        
        
    
   方式二：命令行操作
        
        git reset --hard HEAD~n    // 参数n：倒退(删除)n个commit
        
        git push --force           // 将本次变更强行推送至服务器  远程commit会彻底消失
        
        
   ⚠️⚠️⚠️警告：这类操作比较危险
        
        例如：在你的 commit 3 之后，别人又提交了新的 commit 4，
            
            那在你强制推送之后，那位仁兄的 commit 4 也跟着一起消失了。
                
                
   参考链接：https://segmentfault.com/q/1010000002898735
   
```
[参考链接](!https://segmentfault.com/q/1010000002898735)