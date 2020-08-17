package com.bebopze.jdk.frame.darkrule;

/**
 * 编程实现的灰度规则
 *
 * @author bebopze
 * @date 2020/8/17
 */


// 灰度规则配置(dark-rule.yaml)，放到classpath路径下
// features:
//        - key: call_newapi_getUserById
//        enabled: true
//        rule: {893,342,1020-1120,%30}
//        - key: call_newapi_registerUser
//        enabled: true
//        rule: {1391198723, %10}
//        - key: newalgo_loan
//        enabled: true
//        rule: {0-100}

public class UserPromotionDarkRule implements IDarkFeature {

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public boolean dark(long darkTarget) {
        // 灰度规则自己想怎么写就怎么写
        return false;
    }

    @Override
    public boolean dark(String darkTarget) {
        // 灰度规则自己想怎么写就怎么写
        return false;
    }
}


/**
 * Test
 */
class Test {

    public static void main(String[] args) {

        // 默认加载classpath下dark-rule.yaml文件中的灰度规则
        DarkLaunch darkLaunch = new DarkLaunch();

        // 添加编程实现的灰度规则
        darkLaunch.addProgrammedDarkFeature("user_promotion", new UserPromotionDarkRule());
        IDarkFeature darkFeature = darkLaunch.getDarkFeature("user_promotion");

        System.out.println(darkFeature.enabled());
        System.out.println(darkFeature.dark(893));
    }
}