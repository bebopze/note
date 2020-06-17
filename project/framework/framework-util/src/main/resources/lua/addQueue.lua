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

if redis.call("SET", key, startTime, "NX", "EX", expire) then
    return true
elseif redis.call("EXPIRE", key, expire) then
    return true
end
return false