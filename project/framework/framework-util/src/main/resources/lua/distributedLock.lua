--
-- Created by IntelliJ IDEA.
-- User: liuzhe
-- Date: 2018/8/3
-- Time: 18:00
-- To change this template use File | Settings | File Templates.
-- rateLimit

local key = KEYS[1]
local value = ARGV[1]
local expire = ARGV[2]

if redis.call("SET", key, value, "NX", "EX", expire) then
    return true
elseif redis.call("TTL", key) == -1 then
    redis.call("EXPIRE", key, expire)
end
return false


