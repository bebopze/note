package com.bebopze.jdk.frame.guava;

import com.google.common.collect.ForwardingCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Wrapper模式
 *
 * @author bebopze
 * @date 2020/8/17
 */
public class _02__Wrapper {


    public static void main(String[] args) {

        test__ForwardingCollection();
    }


    private static void test__ForwardingCollection() {

        List<String> list = new ArrayList<>();

        AddLoggingCollection addLoggingCollection = new AddLoggingCollection(list);
    }
}


/**
 * AddLoggingCollection 是基于代理模式实现的一个代理类，
 * 它在原始 Collection 类的基础之上，针对“add”相关的操作，添加了记录日志的功能。
 *
 * @param <E>
 */
class AddLoggingCollection<E> extends ForwardingCollection<E> {

    private Collection<E> originalCollection;

    public AddLoggingCollection(Collection<E> originalCollection) {
        this.originalCollection = originalCollection;
    }

    @Override
    protected Collection delegate() {
        return this.originalCollection;
    }

    @Override
    public boolean add(E element) {
        System.out.println("Add element: " + element);
        return this.delegate().add(element);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        System.out.println("Size of elements to add: " + collection.size());
        return this.delegate().addAll(collection);
    }
}