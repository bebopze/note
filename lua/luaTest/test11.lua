-- table
mytable = {} -- 声明一个table
mytable.first = "tom"
mytable.second = "james"

print(mytable[1])
print(mytable.first)
print(mytable["second"])



-- 引入模块    my-module.lua
require "my-module"

print(module.index)

print(module.sum(10, 20))