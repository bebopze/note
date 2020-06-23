--
-- Created by IntelliJ IDEA.
-- User: bebopze
-- Date: 2018/9/27
-- Time: 下午4:59
-- To change this template use File | Settings | File Templates.
--

local key = KEYS[1]
local startTime = ARGV[1]
local expire = ARGV[2]

local set_repay = redis.call("SET", key, "v");              -- {["ok"]="OK"}
local del_repay_1 = redis.call("DEL", key);                 -- 被删除 key 的数量
local expire_repay = redis.call("EXPIRE", key, expire);     -- 1:成功 / 0:失败(key不存在、或低版本Redis不支持EXPIRE命令时)
local del_repay_2 = redis.call("DEL", key);                 -- 被删除 key 的数量

redis.breakpoint();

local repay = redis.call("SET", key, startTime, "NX", "EX", expire);

if repay.ok == "OK" then
    return true
else
    redis.call("EXPIRE", key, expire)
    return true
end
return false