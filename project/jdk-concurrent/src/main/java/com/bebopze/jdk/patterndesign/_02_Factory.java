package com.bebopze.jdk.patterndesign;

import java.util.HashMap;
import java.util.Map;

/**
 * 2. 工厂模式                  -->   简单工厂、工厂方法、抽象工厂
 *
 * @author bebopze
 * @date 2020/8/4
 */
public class _02_Factory {


    //      相关类型 对象


    // 核心：       解耦  ->  对象的 创建 和 使用


    // 作用：        -->   这也是判断要不要使用工厂模式最本质的参考标准
    //
    //      1、封装变化：   创建逻辑有可能变化，封装成工厂类之后，创建逻辑的变更对调用者透明。
    //      2、代码复用：   创建代码抽离到独立的工厂类之后可以复用。
    //      3、隔离复杂性： 封装复杂的创建逻辑，调用者无需了解如何创建对象。
    //      4、控制复杂度： 将创建代码抽离出来，让原本的函数或类职责更单一，代码更简洁。


    // 复杂度无法被消除，只能被转移：
    //
    //      - 不用工厂模式，if-else 逻辑、创建逻辑、业务代码 耦合在一起
    //
    //      - 简单工厂 是将 不同创建逻辑 放到 一个工厂 类中，if-else 逻辑在这个 工厂类 中
    //
    //      - 工厂方法 是将 不同创建逻辑 放到 不同工厂 类中，
    //                先用 工厂类的工厂 来得到 某个工厂，再用这个工厂来创建
    //                if-else 逻辑在 工厂类的工厂 中


    // -----------------------------------------------------------------------------------------------------------------


    public static void main(String[] args) {

        // 1、简单工厂
        test__SimpleFactory();

        // 2、工厂方法
        test__FactoryMethod();

        // 3、抽象工厂
        test__AbstractFactory();
    }


    private static void test__SimpleFactory() {

        // 1、对象工厂  -->  对象
        IRuleConfigParser parser = SimpleFactory.RuleConfigParserFactory.createParser("json");

        // 2、parse
        parser.parse("simple_factory.json");
    }

    private static void test__FactoryMethod() {

        // 1、工厂的工厂  -->  对象工厂
        FactoryMethod.IRuleConfigParserFactory parserFactory = FactoryMethod.RuleConfigParserFactoryMap.getParserFactory("yml");

        // 2、对象
        IRuleConfigParser parser = parserFactory.createParser();

        // 3、parse
        parser.parse("factory_method.yml");
    }

    private static void test__AbstractFactory() {


        // 1、工厂的工厂  -->  对象工厂
        AbstractFactory.IConfigParserFactory parserFactory = AbstractFactory.RuleConfigParserFactoryMap.getParserFactory("xml");

        // 2、对象
        IRuleConfigParser ruleParser = parserFactory.createRuleParser();
        ISystemConfigParser systemParser = parserFactory.createSystemParser();

        // 3、parse
        ruleParser.parse("abstract_factory.xml");
        systemParser.parse("abstract_factory.xml");
    }

}


// ------------------------------------------------- 实现 --------------------------------------------------------------


/**
 * 1、简单工厂           ====>    预创建 -> 缓存 -> 查表
 */
class SimpleFactory {


    static class RuleConfigParserFactory {

        private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();

        static {
            cachedParsers.put("json", new IRuleConfigParser.JsonRuleConfigParser());
            cachedParsers.put("xml", new IRuleConfigParser.XmlRuleConfigParser());
            cachedParsers.put("yml", new IRuleConfigParser.YmlRuleConfigParser());
            cachedParsers.put("properties", new IRuleConfigParser.PropertiesRuleConfigParser());
        }


        public static IRuleConfigParser createParser(String configFormat) {
            if (configFormat == null || configFormat.isEmpty()) {
                throw new IllegalArgumentException(configFormat + "未定义解析器");
            }
            IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
            return parser;
        }
    }
}


/**
 * 2、工厂方法
 * -
 * -
 * - 非得要将 if 分支逻辑去掉     -->     比较经典处理方法就是利用多态
 * -
 */
class FactoryMethod {


    // 因为工厂类只包含方法，不包含成员变量，完全可以复用，
    // 不需要每次都创建新的工厂类对象，所以，简单工厂模式的第二种实现思路更加合适。

    /**
     * 工厂的工厂
     */
    static class RuleConfigParserFactoryMap {
        private static final Map<String, IRuleConfigParserFactory> cachedFactories = new HashMap<>();

        static {
            cachedFactories.put("json", new JsonRuleConfigParserFactory());
            cachedFactories.put("xml", new XmlRuleConfigParserFactory());
            cachedFactories.put("yml", new YmlRuleConfigParserFactory());
            cachedFactories.put("properties", new PropertiesRuleConfigParserFactory());
        }

        public static IRuleConfigParserFactory getParserFactory(String type) {
            if (type == null || type.isEmpty()) {
                return null;
            }
            IRuleConfigParserFactory parserFactory = cachedFactories.get(type.toLowerCase());
            return parserFactory;
        }
    }


    // ------------------------------ 1、工厂 -------------------------------


    // ------------------------------ 2、工厂的工厂 -------------------------------

    public interface IRuleConfigParserFactory {
        IRuleConfigParser createParser();
    }

    static class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new IRuleConfigParser.JsonRuleConfigParser();
        }
    }

    static class XmlRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new IRuleConfigParser.XmlRuleConfigParser();
        }
    }

    static class YmlRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new IRuleConfigParser.YmlRuleConfigParser();
        }
    }

    static class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory {
        @Override
        public IRuleConfigParser createParser() {
            return new IRuleConfigParser.PropertiesRuleConfigParser();
        }
    }
}


/**
 * 3、抽象工厂
 * -
 * - 场景：
 * -        让一个工厂负责创建 多个不同类型 的对象（IRuleConfigParser、ISystemConfigParser 等），
 * -        而 不是只创建 一种 parser 对象。
 * -
 * -        这样就可以有效地 减少 工厂类的 个数。
 * -
 */
class AbstractFactory {


//    针对规则配置的解析器： 基于接口 IRuleConfigParser
//          JsonRuleConfigParser
//          XmlRuleConfigParser
//          YmlRuleConfigParser
//          PropertiesRuleConfigParser

//    针对系统配置的解析器： 基于接口 ISystemConfigParser
//          JsonSystemConfigParser
//          XmlSystemConfigParser
//          YmlSystemConfigParser
//          PropertiesSystemConfigParser


    /**
     * 工厂的工厂
     */
    static class RuleConfigParserFactoryMap {
        private static final Map<String, IConfigParserFactory> cachedFactories = new HashMap<>();

        static {
            cachedFactories.put("json", new JsonConfigParserFactory());
            cachedFactories.put("xml", new XmlConfigParserFactory());
            cachedFactories.put("yml", new YmlConfigParserFactory());
            cachedFactories.put("properties", new PropertiesConfigParserFactory());
        }

        public static IConfigParserFactory getParserFactory(String type) {
            if (type == null || type.isEmpty()) {
                return null;
            }
            IConfigParserFactory parserFactory = cachedFactories.get(type.toLowerCase());
            return parserFactory;
        }
    }


    interface IConfigParserFactory {

        IRuleConfigParser createRuleParser();

        ISystemConfigParser createSystemParser();

        // 此处可以扩展新的parser类型，比如 IBizConfigParser
    }

    static class JsonConfigParserFactory implements IConfigParserFactory {
        @Override
        public IRuleConfigParser createRuleParser() {
            return new IRuleConfigParser.JsonRuleConfigParser();
        }

        @Override
        public ISystemConfigParser createSystemParser() {
            return new ISystemConfigParser.JsonSystemConfigParser();
        }
    }

    static class XmlConfigParserFactory implements IConfigParserFactory {
        @Override
        public IRuleConfigParser createRuleParser() {
            return new IRuleConfigParser.XmlRuleConfigParser();
        }

        @Override
        public ISystemConfigParser createSystemParser() {
            return new ISystemConfigParser.XmlSystemConfigParser();
        }
    }

    static class YmlConfigParserFactory implements IConfigParserFactory {
        @Override
        public IRuleConfigParser createRuleParser() {
            return new IRuleConfigParser.YmlRuleConfigParser();
        }

        @Override
        public ISystemConfigParser createSystemParser() {
            return new ISystemConfigParser.YmlSystemConfigParser();
        }
    }


    static class PropertiesConfigParserFactory implements IConfigParserFactory {
        @Override
        public IRuleConfigParser createRuleParser() {
            return new IRuleConfigParser.PropertiesRuleConfigParser();
        }

        @Override
        public ISystemConfigParser createSystemParser() {
            return new ISystemConfigParser.PropertiesSystemConfigParser();
        }
    }


}


// ------------------------------- Rule Parser 对象 -------------------------------


/**
 * Rule Parser
 */
interface IRuleConfigParser {

    void parse(String content);


    class JsonRuleConfigParser implements IRuleConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Json Rule Parser ...... parse " + content);
        }
    }

    class XmlRuleConfigParser implements IRuleConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Xml Rule Parser ...... parse " + content);
        }
    }

    class YmlRuleConfigParser implements IRuleConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Yml Rule Parser ...... parse " + content);
        }
    }

    class PropertiesRuleConfigParser implements IRuleConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Properties Rule Parser ...... parse " + content);
        }
    }

}


// ------------------------------- System Parser 对象 --------------------------------


/**
 * System Parser
 */
interface ISystemConfigParser {

    void parse(String content);


    class JsonSystemConfigParser implements ISystemConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Json System Parser ...... parse " + content);
        }
    }

    class XmlSystemConfigParser implements ISystemConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Xml System Parser ...... parse " + content);
        }
    }

    class YmlSystemConfigParser implements ISystemConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Yml System Parser ...... parse " + content);
        }
    }

    class PropertiesSystemConfigParser implements ISystemConfigParser {

        @Override
        public void parse(String content) {
            System.out.println("Properties System Parser ...... parse " + content);
        }
    }

}
