-- 获取body信息
ngx.req.read_body()
local data = ngx.req.get_body_data()
ngx.say(data)
