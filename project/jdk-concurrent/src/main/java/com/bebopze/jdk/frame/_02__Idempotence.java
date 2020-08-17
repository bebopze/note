package com.bebopze.jdk.frame;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.net.HostAndPort;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * - 2、幂等框架
 * -
 * -        // 代码目录结构
 * -        com.xzg.cd.idempotence
 * -            - Idempotence
 * -            - IdempotenceIdGenerator（幂等号生成类）
 * -            - IdempotenceStorage（接口：用来读写幂等号）
 * -            - RedisClusterIdempotenceStorage（IdempotenceStorage的实现类）
 *
 * @author bebopze
 * @date 2020/8/17
 */
class Idempotence {

    private IdempotenceStorage storage;

    public Idempotence(IdempotenceStorage storage) {
        this.storage = storage;
    }

    public boolean saveIfAbsent(String idempotenceId) {
        return storage.saveIfAbsent(idempotenceId);
    }

    public void delete(String idempotenceId) {
        storage.delete(idempotenceId);
    }
}

class IdempotenceIdGenerator {
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}

interface IdempotenceStorage {
    boolean saveIfAbsent(String idempotenceId);

    void delete(String idempotenceId);
}

//class RedisClusterIdempotenceStorage implements IdempotenceStorage {
//
//    private JedisCluster jedisCluster;
//
//    /**
//     * Constructor
//     *
//     * @param redisClusterAddress the format is 128.91.12.1:3455;128.91.12.2:3452;289.13.2.12:8978
//     * @param config              should not be null
//     */
//    public RedisClusterIdempotenceStorage(String redisClusterAddress, GenericObjectPoolConfig config) {
//        Set<HostAndPort> redisNodes = parseHostAndPorts(redisClusterAddress);
//        this.jedisCluster = new JedisCluster(redisNodes, config);
//    }
//
//    public RedisClusterIdempotenceStorage(JedisCluster jedisCluster) {
//        this.jedisCluster = jedisCluster;
//    }
//
//    /**
//     * Save {@idempotenceId} into storage if it does not exist.
//     *
//     * @param idempotenceId the idempotence ID
//     * @return true if the {@idempotenceId} is saved, otherwise return false
//     */
//    @Override
//    public boolean saveIfAbsent(String idempotenceId) {
//        Long success = jedisCluster.setnx(idempotenceId, "1");
//        return success == 1;
//    }
//
//    @Override
//    public void delete(String idempotenceId) {
//        jedisCluster.del(idempotenceId);
//    }
//
//    @VisibleForTesting
//    protected Set<HostAndPort> parseHostAndPorts(String redisClusterAddress) {
//        String[] addressArray = redisClusterAddress.split(";");
//        Set<HostAndPort> redisNodes = new HashSet<>();
//        for (String address : addressArray) {
//            String[] hostAndPort = address.split(":");
//            redisNodes.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
//        }
//        return redisNodes;
//    }
//}