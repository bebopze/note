package com.bebopze.jdk.patterndesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 21. 解释器模式
 *
 * @author bebopze
 * @date 2020/8/13
 */
public class _21_Interpreter {


    // 核心：      语言、语法、解释器


    // 解释器模式
    //      为某个 语言 定义它的 语法（或者叫文法）表示，并定义一个 解释器 用来处理这个语法。


    // 实现：
    //      比较灵活，没有固定的模板


    // 实现的核心思想：
    //          将语法解析的工作拆分到各个小类中，以此来避免大而全的解析类。
    //
    //  一般的做法是
    //      将语法规则拆分一些小的独立的单元，然后对每个单元进行解析，最终合并为对整个语法规则的解析。


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        test_1();
    }


    private static void test_1() {

        String ruleExpression = "api_error_per_minute > 100 || api_count_per_minute > 10000";
        AlertRuleInterpreter interpreter = new AlertRuleInterpreter(ruleExpression);


        Map<String, Long> apiStat = new HashMap<>();
        apiStat.put("api_error_per_minute", 103L);
        apiStat.put("api_count_per_minute", 987L);

        boolean bool = interpreter.interpret(apiStat);

        System.out.println(bool);
    }


}


// ------------------------------- 1、经典实现 --------------------------------


/**
 * 我们可以把 自定义的告警规则 ，看作一种特殊“语言”的语法规则。
 */
interface Expression {
    boolean interpret(Map<String, Long> stats);
}


// ------------------ 利用 解释器模式，我们把 解析表达式的逻辑拆 分到 各个小类中，避免大而复杂的大类的出现。----------------------

/**
 * >
 */
class GreaterExpression implements Expression {
    private String key;
    private long value;

    public GreaterExpression(String expression) {
        String[] elements = expression.trim().split("\\s+");
        if (elements.length != 3 || !elements[1].trim().equals(">")) {
            throw new RuntimeException("Expression is invalid: " + expression);
        }
        this.key = elements[0].trim();
        this.value = Long.parseLong(elements[2].trim());
    }

    public GreaterExpression(String key, long value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean interpret(Map<String, Long> stats) {
        if (!stats.containsKey(key)) {
            return false;
        }
        long statValue = stats.get(key);
        return statValue > value;
    }
}

/**
 * <
 */
class LessExpression implements Expression {
    private String key;
    private long value;

    public LessExpression(String expression) {
        System.out.println("<");
    }

    public LessExpression(String key, long value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean interpret(Map<String, Long> stats) {
        if (!stats.containsKey(key)) {
            return false;
        }
        long statValue = stats.get(key);
        return statValue > value;
    }
}

/**
 * ==
 */
class EqualExpression implements Expression {
    private String key;
    private long value;

    public EqualExpression(String expression) {
        System.out.println("==");
    }

    public EqualExpression(String key, long value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean interpret(Map<String, Long> stats) {
        if (!stats.containsKey(key)) {
            return false;
        }
        long statValue = stats.get(key);
        return statValue > value;
    }
}

/**
 * &&
 */
class AndExpression implements Expression {
    private List<Expression> expressions = new ArrayList<>();

    public AndExpression(String strAndExpression) {
        String[] strExpressions = strAndExpression.split("&&");
        for (String strExpr : strExpressions) {
            if (strExpr.contains(">")) {
                expressions.add(new GreaterExpression(strExpr));
            } else if (strExpr.contains("<")) {
                expressions.add(new LessExpression(strExpr));
            } else if (strExpr.contains("==")) {
                expressions.add(new EqualExpression(strExpr));
            } else {
                throw new RuntimeException("Expression is invalid: " + strAndExpression);
            }
        }
    }

    public AndExpression(List<Expression> expressions) {
        this.expressions.addAll(expressions);
    }

    @Override
    public boolean interpret(Map<String, Long> stats) {
        for (Expression expr : expressions) {
            if (!expr.interpret(stats)) {
                return false;
            }
        }
        return true;
    }

}

/**
 * ||
 */
class OrExpression implements Expression {
    private List<Expression> expressions = new ArrayList<>();

    public OrExpression(String strOrExpression) {
        String[] andExpressions = strOrExpression.split("\\|\\|");
        for (String andExpr : andExpressions) {
            expressions.add(new AndExpression(andExpr));
        }
    }

    public OrExpression(List<Expression> expressions) {
        this.expressions.addAll(expressions);
    }

    @Override
    public boolean interpret(Map<String, Long> stats) {
        for (Expression expr : expressions) {
            if (expr.interpret(stats)) {
                return true;
            }
        }
        return false;
    }
}


/**
 * 组合
 */
class AlertRuleInterpreter {
    private Expression expression;

    public AlertRuleInterpreter(String ruleExpression) {
        this.expression = new OrExpression(ruleExpression);
    }

    public boolean interpret(Map<String, Long> stats) {
        return expression.interpret(stats);
    }
}