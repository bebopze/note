package com.bebopze.jdk.patterndesign;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bebopze.jdk.patterndesign.OrderType.*;

/**
 * 14. 策略模式
 *
 * @author bebopze
 * @date 2020/8/11
 */
public class _14_Strategy {


    // 核心：  解耦、消除 if-else


    // 策略模式：
    //      定义一组算法类，将每个算法分别封装起来，让它们可以互相替换。
    //      策略模式可以使 算法的变化 独立于 使用它们的客户端（这里的 客户端代指 使用算法的代码）。


    // 作用：
    //      解耦 策略的定义、创建和使用，控制代码的复杂度
    //      避免冗长的 if-else 或 switch 分支判断
    //      提供框架的扩展点


    // 设计意图：
    //


    // 查表法：
    //      借助“查表法”，根据type查表 替代 根据type的 if-else 分支判断


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        test_1();


        test_sortFile();
    }


    private static void test_1() {

        Order order = new Order();
        order.setType(NORMAL);

        OrderService orderService = new OrderService();

        orderService.discount(order);
    }


    private static void test_sortFile() {

        Sorter sorter = new Sorter();

        sorter.sortFile("/note/text/设计模式.txt");
    }


}


// ----------------------------------- 1、经典实现 ---------------------------------------


/**
 * 1、策略的定义
 */
interface DiscountStrategy {
    double calDiscount(Order order);
}


// ------------------- 一组 实现这个接口的 策略类 -------------------------

/**
 * 策略1
 */
class NormalDiscountStrategy implements DiscountStrategy {

    @Override
    public double calDiscount(Order order) {
        System.out.println("策略1......");
        return 1;
    }
}

/**
 * 策略2
 */
class GrouponDiscountStrategy implements DiscountStrategy {

    @Override
    public double calDiscount(Order order) {
        System.out.println("策略2......");
        return 2;
    }
}

/**
 * 策略3
 */
class PromotionDiscountStrategy implements DiscountStrategy {

    @Override
    public double calDiscount(Order order) {
        System.out.println("策略3......");
        return 3;
    }
}


/**
 * 2、策略的创建
 */
class DiscountStrategyFactory {

    /**
     * 创建并缓存策略
     */
    private static final Map<OrderType, DiscountStrategy> strategies = new HashMap<>();

    static {
        strategies.put(NORMAL, new NormalDiscountStrategy());
        strategies.put(GROUPON, new GrouponDiscountStrategy());
        strategies.put(PROMOTION, new PromotionDiscountStrategy());
    }

    /**
     * - 查表法：
     * -    借助“查表法”，根据type查表 替代 根据type的 if-else 分支判断
     *
     * @param type
     * @return
     */
    public static DiscountStrategy getDiscountStrategy(OrderType type) {
        return strategies.get(type);
    }
}


/**
 * 3、策略的使用
 */
class OrderService {

    public double discount(Order order) {
        OrderType type = order.getType();
        DiscountStrategy discountStrategy = DiscountStrategyFactory.getDiscountStrategy(type);
        return discountStrategy.calDiscount(order);
    }
}


@Data
class Order {
    OrderType type;
}

enum OrderType {
    NORMAL, GROUPON, PROMOTION;
}


// ----------------------------------- 2、给大文件的内容排序 ---------------------------------------


class Sorter {

    private static final long GB = 1000 * 1000 * 1000;
    private static final List<AlgRange> algs = new ArrayList<>();

    static {
        //  6GB
        algs.add(new AlgRange(0, 6 * GB, SortAlgFactory.getSortAlg("QuickSort")));
        // 10GB
        algs.add(new AlgRange(6 * GB, 10 * GB, SortAlgFactory.getSortAlg("ExternalSort")));
        // 100GB
        algs.add(new AlgRange(10 * GB, 100 * GB, SortAlgFactory.getSortAlg("ConcurrentExternalSort")));
        // TB
        algs.add(new AlgRange(100 * GB, Long.MAX_VALUE, SortAlgFactory.getSortAlg("MapReduceSort")));
    }


    public void sortFile(String filePath) {
        // 省略校验逻辑
        File file = new File(filePath);
        long fileSize = file.length();
        ISortAlg sortAlg = null;
        for (AlgRange algRange : algs) {
            if (algRange.inRange(fileSize)) {
                sortAlg = algRange.getAlg();
                break;
            }
        }
        sortAlg.sort(filePath);
    }

    private static class AlgRange {
        private long start;
        private long end;
        private ISortAlg alg;

        public AlgRange(long start, long end, ISortAlg alg) {
            this.start = start;
            this.end = end;
            this.alg = alg;
        }

        public ISortAlg getAlg() {
            return alg;
        }

        public boolean inRange(long size) {
            return size >= start && size < end;
        }
    }
}


class SortAlgFactory {
    private static final Map<String, ISortAlg> algs = new HashMap<>();

    static {
        algs.put("QuickSort", new QuickSort());
        algs.put("ExternalSort", new ExternalSort());
        algs.put("ConcurrentExternalSort", new ConcurrentExternalSort());
        algs.put("MapReduceSort", new MapReduceSort());
    }

    public static ISortAlg getSortAlg(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        return algs.get(type);
    }
}


interface ISortAlg {
    void sort(String filePath);
}

class QuickSort implements ISortAlg {

    @Override
    public void sort(String filePath) {
        //...
    }
}

class ExternalSort implements ISortAlg {

    @Override
    public void sort(String filePath) {
        //...
    }
}

class ConcurrentExternalSort implements ISortAlg {

    @Override
    public void sort(String filePath) {
        //...
    }
}

class MapReduceSort implements ISortAlg {

    @Override
    public void sort(String filePath) {
        //...
    }
}
