package com.bebopze.jdk.concurrent.concurrentmode;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * copy-on-write
 *
 * @author bebopze
 * @date 2019/6/15
 */
public class CopyOnWrite {


    // 路由信息
    public final class Router {

        private final String ip;
        private final Integer port;
        private final String iface;

        // 构造函数
        public Router(String ip, Integer port, String iface) {
            this.ip = ip;
            this.port = port;
            this.iface = iface;
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//
//            if (o == null || getClass() != o.getClass()) return false;
//
//            Router router = (Router) o;
//
//            return new org.apache.commons.lang.builder.EqualsBuilder()
//                    .append(ip, router.ip)
//                    .append(port, router.port)
//                    .append(iface, router.iface)
//                    .isEquals();
//        }

        //        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//
//            if (o == null || getClass() != o.getClass()) return false;
//
//            Router router = (Router) o;
//
//            return new org.apache.commons.lang3.builder.EqualsBuilder()
//                    .append(ip, router.ip)
//                    .append(port, router.port)
//                    .append(iface, router.iface)
//                    .isEquals();
//        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Router router = (Router) o;
//            return com.google.common.base.Objects.equal(ip, router.ip) &&
//                    com.google.common.base.Objects.equal(port, router.port) &&
//                    com.google.common.base.Objects.equal(iface, router.iface);
//        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Router router = (Router) o;
//            return Objects.equals(ip, router.ip) &&
//                    Objects.equals(port, router.port) &&
//                    Objects.equals(iface, router.iface);
//        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            Router router = (Router) o;
//
//            if (!ip.equals(router.ip)) return false;
//            if (!port.equals(router.port)) return false;
//            return iface.equals(router.iface);
//        }


        // 重写 equals 方法
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Router) {
                Router r = (Router) obj;
                return iface.equals(r.iface) &&
                        ip.equals(r.ip) &&
                        port.equals(r.port);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int result = ip.hashCode();
            result = 31 * result + port.hashCode();
            result = 31 * result + iface.hashCode();
            return result;
        }
    }

    // 路由表信息
    public class RouterTable {

        //Key: 接口名
        //Value: 路由集合
        ConcurrentHashMap<String, CopyOnWriteArraySet<Router>> rt = new ConcurrentHashMap<>();

        // 根据接口名获取路由表
        public Set<Router> get(String iface) {
            return rt.get(iface);
        }

        // 删除路由
        public void remove(Router router) {
            Set<Router> set = rt.get(router.iface);
            if (set != null) {
                set.remove(router);
            }
        }

        // 增加路由
        public void add(Router router) {
            Set<Router> set = rt.computeIfAbsent(router.iface, r -> new CopyOnWriteArraySet<>());
            set.add(router);
        }
    }

}

