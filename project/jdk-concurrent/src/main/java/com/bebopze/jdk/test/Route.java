package com.bebopze.jdk.test;

/**
 * @author bebopze
 * @date 2020/12/21
 */
public class Route {


    public static void main(String[] args) {


        test_route();
    }


    private static void test_route() {

        long orderId = 10000032L;
        int base = 32;

        // ------- 路由的规则

        // 库   ->   orderId % 32
        int db_idx = 1 % base;

        // 表   ->   orderId / 32 % 32
        long db_table_idx = orderId / base & base;


        System.out.println("库   ->   orderId % 32       = " + db_idx);
        System.out.println("表   ->   orderId / 32 % 32  = " + db_table_idx);
    }
}
