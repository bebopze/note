--
-- redis lua script debug:

-- https://blog.huangz.me/2017/redis-lua-debuger-introduction.html
-- https://redis.io/topics/ldb
-- http://www.bilibili.com/video/av9437433/             Redis 作者 antirez 演示如何使用 Lua 调试器（Redis 3.2 版本新功能）
-- https://www.youtube.com/watch?v=IMvRfStaoyM          Redis 作者 antirez 演示如何使用 Redis 3.2 的新功能 —— Lua 调试器


-- ./redis-cli --ldb --eval releaseLock.lua k , v

--      ./redis-cli  :  redis client
--      ldb          :  dubug
--      eval         :  执行lua script

--      debug 模式下常用指令：
--            h(elp)           : help
--            s(tep)           : 执行下一步
--            n(ext)           : 执行下一步  s(tep)命令的别名
--            restart          : 重头开始debug脚本                  ==> 脚本编辑后，直接重头开始debug调试。  不用重新命令进入debug模式，及给定参数。
--            p(rint)          : 打印 所有 变量的value              ==> 控制台打印所有变量值
--            p(rint) <var>    : 打印 指定 变量的value              ==> 控制台打印指定变量值
--            l(ist)           : 打印当前debug断点行及其上下5行，并标识当前debug断点行
--            w(hole)          : 打印全部脚本内容，并标识当前debug断点行
--            c(ontinue)       : 直接跳转到下一个breakpoint处
--
--


local key = KEYS[1]
local value = ARGV[1]


local set_repay = redis.call("SET", key, value);    -- {["ok"]="OK"}    ==> table对象 对应Java Map: {"ok":"OK"}
local repay = redis.call("GET", key);               -- value / false    ==> 存在：value / 不存在：false
local del_repay = redis.call("DEL", key);           -- 1                ==> DEL成功的数量

redis.breakpoint();     -- 打断点

if repay == false then
    redis.breakpoint();
    return true
elseif repay == value then
    redis.breakpoint();
    if redis.call("DEL", key) <= 1 then       -- 0: 刚刚好自动过期   /   1: DEL成功
        return true
    end
end
return false
