local template = require "resty.template"  -- 调用模板
local redis = require "resty.redis_iresty"
local red = redis:new()
local msg = red:get("num")
if ngx.null == msg then
    return ngx.say("获取库存失败");
end

local context = {
    title = "品优购",
    -- 从redis中获取库存信息，在页面中显示
    num = msg
}
local request_uri = ngx.var.request_uri

template.render(string.sub(request_uri, 2), context);
