-- 数组

arr = { "aaa", "bbb", "ccc" }

--[[
-- 数组for
for index=1,#arr do
	print(arr[index])
end
---]]
-- 泛型for
for i, v in ipairs(arr) do
    print(i, v)
end

print(arr[0])
print(arr[1])
print(arr[4])
