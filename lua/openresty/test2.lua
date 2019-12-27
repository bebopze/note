-- 获取header
local headers = ngx.req.get_headers()
for k, v in pairs(headers) do
    ngx.say("[header] name:", k, " v:", v)
    ngx.say("<br>")
end
