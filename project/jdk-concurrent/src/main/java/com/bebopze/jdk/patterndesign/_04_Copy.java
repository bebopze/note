package com.bebopze.jdk.patterndesign;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 4. 原型模式      深拷贝 、 浅拷贝
 *
 * @author bebopze
 * @date 2020/8/5
 */
public class _04_Copy {


    // 深拷贝
    //      值 拷贝

    //  实现方式：
    //      1、递归
    //      2、序列化 + 反序列化


    // 浅拷贝
    //      地址值 拷贝

    // 实现：
    //      Java clone()


    // ---------------------------------------------------------------


    public static void main(String[] args) throws CloneNotSupportedException {

        Person child = Person.builder().name("ed").age(14).sex(2).build();
        Person person = Person.builder().name("bebop").age(18).sex(1).child(child).build();
        System.out.println(person);


        Person deepCopy_recursion = deepCopy_recursion(person);
        Person deepCopy_serialization = deepCopy_serialization(person);

        Person shallowCopy = shallowCopy(person);


        System.out.println(deepCopy_recursion);
        System.out.println(deepCopy_serialization);
        System.out.println(shallowCopy);


        boolean equals = person.child.equals(shallowCopy.child);


        System.out.println("--------------------------");


        person.setAge(30);
        person.child.setName("jet");
        person.child.setAge(32);
        System.out.println(person);


        System.out.println(deepCopy_recursion);
        System.out.println(deepCopy_serialization);
        System.out.println(shallowCopy);


        System.out.println("--------------------------");
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Person implements Cloneable {

        private String name;
        private Integer age;
        private int sex;
        private Person child;


        /**
         * 调用 clone()   -->   Person 必须实现 Cloneable
         *
         * @return
         * @throws CloneNotSupportedException
         */
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", child=" + child +
                    '}';
        }
    }


    // ----------------------------------实现-----------------------------


    // ---------------------------------- 深拷贝


    /**
     * 1、递归
     * <p>
     * 第一种方法：递归拷贝对象、对象的引用对象 以及 引用对象的引用对象……  直到要拷贝的对象 只包含 基本数据类型 数据，没有引用对象为止。
     *
     * @param person
     * @return
     * @throws CloneNotSupportedException
     */
    public static Person deepCopy_recursion(Person person) throws CloneNotSupportedException {

        // deep copy
        Person deepCopy_person = Person.builder().name(person.name).age(person.age).child(person.child).build();

        // 递归 引用对象
        if (null != person.child) {
            Person deepCopy_child = deepCopy_recursion(person.child);
            deepCopy_person.setChild(deepCopy_child);
        }

        return deepCopy_person;
    }


    /**
     * 2、序列化 + 反序列化
     * <p>
     * 第二种方法：先将对象序列化，然后再反序列化成新的对象。
     *
     * @param person
     * @return
     * @throws CloneNotSupportedException
     */
    public static Person deepCopy_serialization(Person person) throws CloneNotSupportedException {

        // 序列化
        String jsonStr = JSON.toJSONString(person);

        // 反序列化
        Person deepCopy_person = JSON.parseObject(jsonStr, Person.class);

        return deepCopy_person;
    }


    // ---------------------------------- 浅拷贝


    public static Person shallowCopy(Person person) throws CloneNotSupportedException {

        // clone() 语法
        Person clone_person = (Person) person.clone();
        return clone_person;
    }
}
