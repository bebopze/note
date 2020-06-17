package com.junzijian.framework.common.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 统一日志处理
 *
 * @author bebopze
 * @date 2019/10/24
 */
@Slf4j
@Aspect
@Component
public class GlobalLogHandler {


    @Around(value = "execution(* com.junzijian..*..*.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        long startTime = System.currentTimeMillis();

        // request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 日志记录
        logRequest(request, pjp);

        // exec
        Object result = pjp.proceed();

        // 统计时间
        logTime(request, startTime);

        return result;
    }


    /**
     * log request
     *
     * @param request
     * @param pjp
     */
    private void logRequest(HttpServletRequest request, ProceedingJoinPoint pjp) {

        Object[] args = pjp.getArgs();

        List<Object> argList = Arrays.stream(args)
                .filter(arg -> !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(argList)) {
            log.info(Stream.of(request.getContextPath() + request.getServletPath(), getIpAddress(request)).collect(Collectors.joining(" ")));
        } else {
            log.info(Stream.of(request.getContextPath() + request.getServletPath(), getIpAddress(request), JSON.toJSONString(argList)).collect(Collectors.joining(" ")));
        }

    }

    /**
     * log time
     *
     * @param request
     * @param startTime
     */
    private void logTime(HttpServletRequest request, long startTime) {

        String path = request.getContextPath() + request.getServletPath();

        long totalTime = System.currentTimeMillis() - startTime;

        log.info("{} {}s", path, (double) totalTime / 1000);
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ipIsBlank(ip)) {
            ip = request.getHeader("Proxy-Client-IP");

            if (ipIsBlank(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");

                if (ipIsBlank(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");

                    if (ipIsBlank(ip)) {
                        ip = request.getHeader("HTTP_X_FORWARDED_FOR");

                        if (ipIsBlank(ip)) {
                            ip = request.getRemoteAddr();
                        }
                    }
                }
            }

        } else if (ip.length() > 15) {

            // 经过多层代理后会有多个代理，取第一个ip地址就可以了
            String[] ips = ip.split("\\,");

            for (int index = 0; index < ips.length; index++) {

                String strIp = ips[index];

                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        return ip;
    }

    private static boolean ipIsBlank(String ip) {

        return !StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip);
    }

}
