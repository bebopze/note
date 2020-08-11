package com.bebopze.jdk.patterndesign;

import java.util.HashMap;
import java.util.Map;

/**
 * 2. 工厂模式
 *
 * @author bebopze
 * @date 2020/8/4
 */
public class _02_Factory {


    // ---------------------------------------------------------------


    public static void main(String[] args) {


    }


    // ----------------------------------实现-----------------------------

    /**
     * 1、简单工厂
     */
//    static class RuleConfigParserFactory {
//        private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();
//
//        static {
//            cachedParsers.put("json", new JsonRuleConfigParser());
//            cachedParsers.put("xml", new XmlRuleConfigParser());
//            cachedParsers.put("yaml", new YamlRuleConfigParser());
//            cachedParsers.put("properties", new PropertiesRuleConfigParser());
//        }
//
//        public static IRuleConfigParser createParser(String configFormat) {
//            if (configFormat == null || configFormat.isEmpty()) {
//                return null;//返回null还是IllegalArgumentException全凭你自己说了算
//            }
//            IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
//            return parser;
//        }
//    }
//
//    interface IRuleConfigParser {
//
//    }


    /**
     * 2、工厂方法
     */
//    public class RuleConfigSource {
//
//        public RuleConfig load(String ruleConfigFilePath) {
//
//            String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
//
//            IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParserFactory(ruleConfigFileExtension);
//            if (parserFactory == null) {
//                throw new IllegalArgumentException("Rule config file format is not supported: " + ruleConfigFilePath);
//            }
//            IRuleConfigParser parser = parserFactory.createParser();
//
//            String configText = "";
//
//            // 从ruleConfigFilePath文件中读取配置文本到configText中
//            RuleConfig ruleConfig = parser.parse(configText);
//
//            return ruleConfig;
//        }
//
//        private String getFileExtension(String filePath) {
//            //...解析文件名获取扩展名，比如rule.json，返回json
//            return "json";
//        }
//    }
//
//    // 因为工厂类只包含方法，不包含成员变量，完全可以复用，
//    // 不需要每次都创建新的工厂类对象，所以，简单工厂模式的第二种实现思路更加合适。
//
//    static class RuleConfigParserFactoryMap { //工厂的工厂
//        private static final Map<String, IRuleConfigParserFactory> cachedFactories = new HashMap<>();
//
//        static {
//            cachedFactories.put("json", new JsonRuleConfigParserFactory());
//            cachedFactories.put("xml", new XmlRuleConfigParserFactory());
//            cachedFactories.put("yaml", new YamlRuleConfigParserFactory());
//            cachedFactories.put("properties", new PropertiesRuleConfigParserFactory());
//        }
//
//        static IRuleConfigParserFactory getParserFactory(String type) {
//            if (type == null || type.isEmpty()) {
//                return null;
//            }
//            IRuleConfigParserFactory parserFactory = cachedFactories.get(type.toLowerCase());
//            return parserFactory;
//        }
//    }
//
//    interface IRuleConfigParserFactory {
//
//    }


    /**
     * 3、抽象工厂
     */

    //    针对规则配置的解析器：基于接口IRuleConfigParser
    //    JsonRuleConfigParser
    //    XmlRuleConfigParser
    //    YamlRuleConfigParser
    //    PropertiesRuleConfigParser

    //    针对系统配置的解析器：基于接口ISystemConfigParser
    //    JsonSystemConfigParser
    //    XmlSystemConfigParser
    //    YamlSystemConfigParser
    //    PropertiesSystemConfigParser

//    public interface IConfigParserFactory {
//
//        IRuleConfigParser createRuleParser();
//
//        ISystemConfigParser createSystemParser();
//
//        // 此处可以扩展新的parser类型，比如IBizConfigParser
//    }
//
//    public class JsonConfigParserFactory implements IConfigParserFactory {
//        @Override
//        public IRuleConfigParser createRuleParser() {
//            return new JsonRuleConfigParser();
//        }
//
//        @Override
//        public ISystemConfigParser createSystemParser() {
//            return new JsonSystemConfigParser();
//        }
//    }
//
//    public class XmlConfigParserFactory implements IConfigParserFactory {
//        @Override
//        public IRuleConfigParser createRuleParser() {
//            return new XmlRuleConfigParser();
//        }
//
//        @Override
//        public ISystemConfigParser createSystemParser() {
//            return new XmlSystemConfigParser();
//        }
//    }
//
//    // 省略YamlConfigParserFactory和PropertiesConfigParserFactory代码

}
