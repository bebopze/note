package com.bebopze.jdk.patterndesign;

import org.springframework.util.StringUtils;

/**
 * 3. Builder模式
 *
 * @author bebopze
 * @date 2020/8/5
 */
public class _03_Builder {


    public static void main(String[] args) {

        test_builder();
    }


    private static void test_builder() {

        // 这段代码会抛出IllegalArgumentException，因为 minIdle > maxIdle

        ResourcePoolConfig config = ResourcePoolConfig.builder()
                .setName("db-connection-pool")
                .setMaxTotal(16)
                .setMaxIdle(10)
                .setMinIdle(12)
                .build();
    }
}


class ResourcePoolConfig {

    // 字段
    private String name;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;


    private ResourcePoolConfig(Builder builder) {
        this.name = builder.name;
        this.maxTotal = builder.maxTotal;
        this.maxIdle = builder.maxIdle;
        this.minIdle = builder.minIdle;
    }

    // ...省略getter方法...


    /**
     * Builder 对象
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }


    // ---------------------------------- Builder类


    /**
     * Builder类设计成了 ResourcePoolConfig的 内部类
     * -
     * 也可以将Builder类设计成 独立的非内部类 ResourcePoolConfigBuilder
     */
    static class Builder {

        private static final int DEFAULT_MAX_TOTAL = 8;
        private static final int DEFAULT_MAX_IDLE = 8;
        private static final int DEFAULT_MIN_IDLE = 0;

        // 字段copy
        private String name;
        private int maxTotal = DEFAULT_MAX_TOTAL;
        private int maxIdle = DEFAULT_MAX_IDLE;
        private int minIdle = DEFAULT_MIN_IDLE;


        /**
         * xxBuilder -> xx
         *
         * @return
         */
        public ResourcePoolConfig build() {
            // 可以把校验逻辑放到这里来做，包括必填项校验、依赖关系校验、约束条件校验等
            if (StringUtils.isEmpty(name)) {
                throw new IllegalArgumentException("...");
            }
            if (maxIdle > maxTotal) {
                throw new IllegalArgumentException("...");
            }
            if (minIdle > maxTotal || minIdle > maxIdle) {
                throw new IllegalArgumentException("...");
            }

            return new ResourcePoolConfig(this);
        }


        // ------------------------------------- set xx

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }
    }


//    public static class Builder {
//
//        // 字段copy
//        private String name;
//        private int maxTotal;
//        private int maxIdle;
//        private int minIdle;
//
//
//        /**
//         * xxBuilder -> xx
//         *
//         * @return
//         */
//        public ResourcePoolConfig build() {
//            return new ResourcePoolConfig(this);
//        }
//
//
//        // ------------------------------------- set xx
//
//        public Builder setName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Builder setMaxTotal(int maxTotal) {
//            this.maxTotal = maxTotal;
//            return this;
//        }
//
//        public Builder setMaxIdle(int maxIdle) {
//            this.maxIdle = maxIdle;
//            return this;
//        }
//
//        public Builder setMinIdle(int minIdle) {
//            this.minIdle = minIdle;
//            return this;
//        }
//    }
}
