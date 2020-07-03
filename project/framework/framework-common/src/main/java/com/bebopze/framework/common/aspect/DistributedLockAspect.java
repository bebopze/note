package com.bebopze.framework.common.aspect;

import com.bebopze.framework.common.annotation.DistributedLock;
import com.bebopze.framework.util.LockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 分布式🔐
 *
 * @author bebopze
 * @date 2018/8/3
 */
@Slf4j
@Aspect
@Component
public class DistributedLockAspect {

    /**
     * 🔐KEY前缀
     */
    private static final String LOCK_KEY_PREFIX = "lock:key:";

    /**
     * 异步延时任务线程池
     */
    private static final ScheduledExecutorService resetLockExpireService = Executors.newScheduledThreadPool(1);


    @Autowired
    private LockUtils lockUtils;


    @Around(value = "@annotation(com.bebopze.framework.common.annotation.DistributedLock)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        DistributedLock annotation = method.getAnnotation(DistributedLock.class);

        // timeout
        long timeout = annotation.value();


        // key
        // sessionId
        Serializable sessionId = null;//SessionUtils.getSessionId();
        // methodName
        String fullMethodName = getFullMethodName(method);
        // sessionId + fullMethodName
        String lockKey = sessionId + ":" + fullMethodName;

        // prefix + MD5(key)
        String key = LOCK_KEY_PREFIX + DigestUtils.md5DigestAsHex(lockKey.getBytes());


        // randomVal     生成一个随机数：作为当前🔐的val
        long randomNum = ThreadLocalRandom.current().nextInt(1000000000);
        String val = System.currentTimeMillis() + String.valueOf(randomNum);


        try {

            // 获取锁
            boolean getLock = lockUtils.lock(key, val, timeout);
            log.debug(getLock ? "获取锁成功！KEY：" + key + " , VAL：" + val : "获取锁失败！KEY：" + key + " , VAL：" + val);

            // 获取到锁
            if (getLock) {

                // 开启一个异步定时任务  --> 每隔2/3过期时间，检测lock是否还在（业务逻辑还没执行完），locked则锁自动续期
                resetLockExpireTask(key, val, timeout);

                // 执行原方法
                Object result = point.proceed();

                return result;
            }

        } finally {

            // 释放锁
            boolean releaseLock = lockUtils.releaseLock(key, val);
            log.debug(releaseLock ? "释放锁成功！KEY：" + key + " , VAL：" + val : "释放锁失败！KEY：" + key + " , VAL：" + val);
        }

        return null;
    }

    /**
     * method的全方法名
     *
     * @param method
     * @return
     */
    private String getFullMethodName(Method method) {

        StringBuilder fullMethodName = new StringBuilder();

        String clazzName = method.getDeclaringClass().getName();
        String methodName = method.getName();

        fullMethodName.append(clazzName);
        fullMethodName.append(methodName);

        Class<?>[] parameterTypes = method.getParameterTypes();

        if (ArrayUtils.isNotEmpty(parameterTypes)) {

            for (Class<?> parameterType : parameterTypes) {

                fullMethodName.append(parameterType.getName());
            }
        }

        return fullMethodName.toString();
    }

    /**
     * 开启一个异步定时任务  --> 每隔2/3过期时间，检测lock是否还在（业务逻辑还没执行完），isLocked 则锁自动续期
     * -
     * - https://mp.weixin.qq.com/s/MLDeZ_GKlY289pZ_IPJvOA
     *
     * @param key
     * @param val
     * @param timeout x秒
     */
    private void resetLockExpireTask(String key, String val, long timeout) {

        resetLockExpireService.schedule(() -> {

            // 锁还在（业务逻辑还没执行完）
            if (lockUtils.isLocked(key, val)) {

                // 重置🔐过期时间（锁续期）
                lockUtils.resetLockExpire(key, timeout);
                log.debug("锁续期成功！key : {} , timeout : {}", val, timeout);

                // 递归
                resetLockExpireTask(key, val, timeout);
            }

        }, timeout * 1000 * 2 / 3, TimeUnit.MICROSECONDS);
    }

}
