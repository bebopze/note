package com.bebopze.framework.common.aspect;

import com.bebopze.framework.common.annotation.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 分布式🔐
 *
 * @author bebopze
 * @date 2020/08/03
 */
@Slf4j
@Aspect
@Component
public class DistributedLockAspect_Redisson {

    /**
     * 🔐KEY前缀
     */
    private static final String LOCK_KEY_PREFIX = "lock:key:";


    @Autowired
    private RedissonClient redissonClient;


    @Around(value = "@annotation(com.bebopze.framework.common.annotation.DistributedLock)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Method method = ((MethodSignature) point.getSignature()).getMethod();

        DistributedLock annotation = method.getAnnotation(DistributedLock.class);

        // timeout
        long timeout = annotation.value();

        // key
        String key = generateKey(method);


        RLock lock = redissonClient.getLock(key);


        // lock
        lock.tryLock(timeout, TimeUnit.SECONDS);
        log.debug("获取锁...  KEY：{} , ", key);

        // 定时任务会定期检查去续期 renewExpirationAsync(threadId).

        try {

            // access the resource protected by this lock

            Object result = point.proceed();

            return result;


        } finally {

            // unlock
            lock.unlock();
            log.debug("释放锁...  KEY：{} , ", key);
        }
    }

    /**
     * generate key
     *
     * @param method
     * @return
     */
    private String generateKey(Method method) {

        // sessionId
        Serializable sessionId = null;//SessionUtils.getSessionId();

        // methodName
        String fullMethodName = getFullMethodName(method);

        // sessionId + fullMethodName
        String lockKey = sessionId + ":" + fullMethodName;


        // prefix + MD5(key)
        return LOCK_KEY_PREFIX + DigestUtils.md5DigestAsHex(lockKey.getBytes());
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

}
