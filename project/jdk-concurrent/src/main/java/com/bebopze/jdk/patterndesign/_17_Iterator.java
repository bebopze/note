package com.bebopze.jdk.patterndesign;

import java.util.NoSuchElementException;

/**
 * 17. 迭代器模式            -->     也叫  游标模式（Cursor）
 *
 * @author bebopze
 * @date 2020/8/12
 */
public class _17_Iterator {


    // 核心：  容器、迭代


    // 用来：
    //      遍历集合对象


    // 实现：
    //      1、容器       接口   -->  实现类
    //      2、容器迭代器  接口   -->  实现类


    // ---------------------------------------------------------------


    public static void main(String[] args) {


        test_1();
    }

    private static void test_1() {

        IList<String> names = new IArrayList<>();
        names.add("xzg");
        names.add("wang");
        names.add("zheng");

        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.currentItem());
            iterator.next();
        }
    }
}


// ----------------------------------- 实现 ----------------------------------------


// ---------------------- 1、容器 ----------------------

/**
 * 容器 - 接口
 *
 * @param <E>
 */
interface IList<E> {

    /**
     * 创建 容器迭代器
     *
     * @return
     */
    Iterator iterator();


    //...省略其他接口函数...

    void add(E e);

    int size();

    E get(int cursor);
}


/**
 * 容器 - 实现
 *
 * @param <E>
 */
class IArrayList<E> implements IList<E> {

    @Override
    public Iterator iterator() {
        return new ArrayIterator(this);
    }

    @Override
    public void add(E e) {
        System.out.println("ArrayList  add --------- " + e);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public E get(int cursor) {
        return null;
    }


    //...省略其他代码
}


// ---------------------- 2、容器迭代器 ----------------------

/**
 * 容器迭代器 - 接口
 *
 * @param <E>
 */
interface Iterator<E> {

    boolean hasNext();

    void next();

    E currentItem();
}

/**
 * 容器迭代器 - 实现
 */
class ArrayIterator<E> implements Iterator<E> {
    private int cursor;
    private IArrayList<E> arrayList;

    public ArrayIterator(IArrayList<E> arrayList) {
        this.cursor = 0;
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasNext() {
        return cursor != arrayList.size();
    }

    @Override
    public void next() {
        cursor++;
    }

    @Override
    public E currentItem() {
        if (cursor >= arrayList.size()) {
            throw new NoSuchElementException();
        }
        return arrayList.get(cursor);
    }
}



