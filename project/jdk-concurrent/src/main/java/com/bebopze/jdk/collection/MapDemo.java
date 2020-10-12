package com.bebopze.jdk.collection;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bebopze
 * @date 2020/6/15
 */
public class MapDemo {

    public static void main(String[] args) {

        resize();

//        test0();

//        test1();

        test_HashMap();

//        test2_ConcurrentHashMap();
    }


    private static void resize() {

        // 默认 16  ->  12
        //
        // 16 x 0.75f = 12
        //
        // 超过12 就 resize
        //
        //
        // 自己强行设置 initialCapacity时，数一下自己到底有几个元素


        HashMap map = new HashMap(16);

        // 16 x 0.75f = 12

        for (int i = 1; i <= 16; i++) {
            map.put("k" + i, "v" + i);
        }

        System.out.println();

    }


    private static HashMap<Integer, String> map = new HashMap(2, 0.75f);

    /**
     * HashMap Infinite Loop
     */
    private static void test_HashMap() {

        map.put(5, "C");

        new Thread("Thread1") {

            @Override
            public void run() {
                map.put(7, "B");
                System.out.println(map);
            }
        }.start();

        new Thread("Thread2") {

            @Override
            public void run() {
                map.put(3, "A");
                System.out.println(map);
            }
        }.start();
    }


    /**
     * ConcurrentHashMap  -->  https://www.cnblogs.com/chengxiao/p/6842045.html
     */
    private static void test2_ConcurrentHashMap() {

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 1000; i++) {
            map.put("k" + i, "v" + i);
        }

        Object val = map.get("k1");

        System.out.println(map);
    }


    private static void test0() {

        int num1 = 1 << 30;
        int num2 = Integer.MAX_VALUE;
        System.out.println(num1);
        System.out.println(num2);


        /**
         * hashMap  -->   https://www.cnblogs.com/chengxiao/p/6059914.html   /   https://blog.csdn.net/woshimaxiao1/article/details/83661464
         *
         * JDK1.7 扩容 环形链表bug  -->  https://www.cnblogs.com/wen-he/p/11496050.html
         */
        HashMap map = new HashMap(16);

        for (int i = 1; i <= 160; i++) {
            map.put("k" + i, "v" + i);
        }


        map.remove("k1");


        System.out.println(map);
    }


    private static void test1() {

        HashMap<Person, String> map = new HashMap();
        Person p1 = new Person(1234, "乔峰");

        //put到hashmap中去
        map.put(p1, "天龙八部");


        //get取出，从逻辑上讲应该能输出“天龙八部”
        Person p2 = new Person(1234, "乔峰");   // null

        System.out.println("结果:" + map.get(p2));


        // 只重写了equals()   没有重写hashCode()  -->  导致hashcode值并不相同      默认调用 Object#hashcode  会根据 堆内存中的 地址值 -->  整数 返回
        int p1_hashcode = p1.hashCode();
        int p2_hashcode = p2.hashCode();

        System.out.println("p1_hashcode :" + p1_hashcode);
        System.out.println("p2_hashcode :" + p2_hashcode);
    }


    private static class Person {
        int idCard;
        String name;

        public Person(int idCard, String name) {
            this.idCard = idCard;
            this.name = name;
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) {
//                return true;
//            }
//            if (o == null || getClass() != o.getClass()) {
//                return false;
//            }
//
//            Person person = (Person) o;
//            // 两个对象是否等值，通过idCard来确定
//            return this.idCard == person.idCard;
//        }
//


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Person person = (Person) o;

            return idCard == person.idCard;
        }

//        @Override
//        public int hashCode() {
//            return idCard;
//        }
    }

}
