--
-- Created by IntelliJ IDEA.
-- User: bebop
-- Date: 2018/9/1
-- Time: 下午4:20
-- To change this template use File | Settings | File Templates.
--

local key = KEYS[1]
local value = 1
local limit = tonumber(ARGV[1])
local expire = ARGV[2]

if redis.call("SET", key, value, "NX", "EX", expire) then
    return true
else
    if redis.call("INCR", key) <= limit then
        return true
    end
    if redis.call("TTL", key) == -1 then
        redis.call("EXPIRE", key, expire)
    end
end
return false