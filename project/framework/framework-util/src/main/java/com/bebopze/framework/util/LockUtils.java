package com.bebopze.framework.util;

import com.google.common.collect.Lists;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author bebopze
 * @date 2018/8/3
 */
@Component
public class LockUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 获取🔐
     *
     * @param key
     * @param randomVal
     * @param timeout   单位：秒
     */
    public boolean lock(String key, String randomVal, Long timeout) {

        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/distributedLock.lua")));
        redisScript.setResultType(Boolean.class);

        Boolean result = stringRedisTemplate.execute(redisScript, Lists.newArrayList(key), randomVal, String.valueOf(timeout));
        return result;
    }

    /**
     * 释放🔐
     *
     * @param key
     * @param val
     * @return
     */
    public boolean releaseLock(String key, String val) {

        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/releaseLock.lua")));
        redisScript.setResultType(Boolean.class);

        Boolean result = stringRedisTemplate.execute(redisScript, Lists.newArrayList(key), val);
        return result;
    }


    /**
     * 锁val是否还在（业务逻辑还没执行完）
     *
     * @param key
     * @param val
     * @return
     */
    public boolean isLocked(String key, String val) {
        String lockVal = stringRedisTemplate.opsForValue().get(key);
        return val.equals(lockVal);
    }


    /**
     * 锁还在(业务逻辑还没执行完)，重置🔐过期时间（锁续期）
     *
     * @param key
     * @param timeout
     */
    public void resetLockExpire(String key, long timeout) {
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
}
