local key = KEYS[1]
local value = ARGV[1]
local expire = ARGV[2]

-- table
local ok_table = {} -- 声明一个table
ok_table.ok = "OK"  -- 赋值kv

local ook_table = {["ok"] = "OK"}


local setNxEx_reply = redis.call("SET", key, value, "NX", "EX", expire);
redis.debug("setNxEx_reply : ", setNxEx_reply); -- {["ok"]="OK"}


local tmp1 = setNxEx_reply == ok_table;         -- false
local tmp2 = setNxEx_reply.ok == ok_table.ok;   -- true
local tmp3 = setNxEx_reply.ok == ook_table.ok;  -- true


if setNxEx_reply.ok == ok_table.ok then
    --if setNxEx_reply.ok == "OK" then
    redis.breakpoint()
    return true
elseif redis.call("TTL", key) == -1 then
    redis.call("EXPIRE", key, expire)
end
return false