###          https://steps.8bq.ovh/api?account=123456@qq.com&password=zepp_app&steps=361
###   https://steps.api.030101.xyz/api?account=123456@qq.com&password=zepp_app&steps=361





##### $steps = Get-Random -Minimum 10000 -Maximum 20000  # 生成1万到2万随机步数


# 获取当前小时数
$currentHour = (Get-Date).Hour

# 计算随机数范围 [currentHour*100, (currentHour+1)*100)
$minValue = $currentHour * 100
$maxValue = ($currentHour + 1) * 100
$randomValue = Get-Random -Minimum $minValue -Maximum $maxValue

$randomStep = $minValue + $randomValue
echo $randomStep 







# 定义接口参数
$account = "123456@qq.com"
$password = "zepp_app"
$steps = $randomStep





# 日志文件路径（自动按日期分割）
# $logPath = "C:\Logs\StepSync_$(Get-Date -Format 'yyyyMMdd').log"
$logPath = "C:\tools\shell_script\log\logfile.txt"  # 修改为您希望保存日志的文件路径

# 定义日志记录函数（支持时间戳与结构化信息）
function Write-Log {
    param(
        [string]$Message,
        [string]$Status = "INFO"
    )
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $logEntry = "[$timestamp][$Status] $Message"
    Add-Content -Path $logPath -Value $logEntry
}







# 创建 Web 会话对象（兼容低版本 PowerShell）
$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession

# 设置用户代理（模拟 Chrome 浏览器行为）[1,3](@ref)
$session.UserAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36"

# 定义请求头（精简非必要字段，避免冗余）[1,5](@ref)
$headers = @{
    "accept"          = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
    "accept-language" = "zh-CN,zh;q=0.9"
    "priority"        = "u=0, i"
}




# 记录开始时间和请求的URL
$startTime = Get-Date
$logMessage = "[$startTime] Sending request to https://steps.8bq.ovh/api with steps=$steps"
Add-Content -Path $logPath -Value $logMessage



try {
	
    # 发送 GET 请求（增加超时设置与证书忽略）[3,5](@ref)
    $response = Invoke-WebRequest `
        -UseBasicParsing `
        -Uri "https://steps.8bq.ovh/api?account=$account&password=$password&steps=$steps" `
        -WebSession $session `
        -Headers $headers `
        -TimeoutSec 30 `
        -ErrorAction Stop





	$logMessage = "[$endTime] Response received: $($response.StatusCode) $($response.StatusDescription)"
    Add-Content -Path $logPath -Value $logMessage

    # 记录接口返回内容
    $responseContent = $response.Content
    Add-Content -Path $logPath -Value $responseContent
	
	
	
} catch {
	
	
	
    # 错误处理（细化异常分类）[1](@ref)
    $errorMsg = switch ($_.Exception.Response.StatusCode) {
        401 { "认证失败：账号或密码错误" }
        429 { "请求频繁：请等待 1 小时后再试" }
        500 { "服务器内部错误" }
        default { "未知错误：$($_.Exception.Message)" }
    }
    Write-Host "[失败] $errorMsg" -ForegroundColor Red
	
	
	
	$endTime = Get-Date
    $errorMessage = "[$endTime] Error occurred: $_"
    Add-Content -Path $logPath -Value $errorMessage
	
	
	
    exit 1
}