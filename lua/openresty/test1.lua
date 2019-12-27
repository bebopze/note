-- 获取get请求参数
local arg = ngx.req.get_uri_args()
for k, v in pairs(arg) do
    ngx.say("[GET ] key:", k, " v:", v)
    ngx.say("<br>")
end

-- 获取post请求时 请求参数
ngx.req.read_body() -- 解析 body 参数之前一定要先读取 body
local arg = ngx.req.get_post_args()
for k, v in pairs(arg) do
    ngx.say("[POST] key:", k, " v:", v)
    ngx.say("<br>")
end