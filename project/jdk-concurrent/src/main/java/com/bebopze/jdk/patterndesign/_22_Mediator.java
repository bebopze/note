package com.bebopze.jdk.patterndesign;

/**
 * 22. 中介模式
 *
 * @author bebopze
 * @date 2020/8/13
 */
public class _22_Mediator {


    // 核心：      网状、中介、一对多


    // 定义：
    //      中介模式定义了一个单独的（中介）对象，来封装一组对象之间的交互。
    //      将这组对象之间的交互委派给与中介对象交互，来避免对象之间的直接交互。

    // 作用：
    //      多对多（网状关系）  --->   一对多（星状关系）

    //      中介模式的设计思想跟 中间层 很像，
    //      通过引入中介这个中间层，将一组对象之间的交互关系（或者依赖关系）从多对多（网状关系）转换为一对多（星状关系）。

    //      原来一个对象要跟 n 个对象交互，现在只需要跟 一个中介对象 交互，
    //      从而最小化对象之间的交互关系，降低了代码的复杂度，提高了代码的可读性和可维护性。


    // 生活场景：
    //      航空管制
    //      中介者 -->  “塔台”
    //
    //      “塔台” -> 中介，让每架飞机只跟塔台来通信，发送自己的位置给塔台，由塔台来负责每架飞机的航线调度。
    //      这样就大大简化了通信网络。


    // 平衡 复杂度：
    //      平衡对象之间交互的复杂度 和 中介类本身的复杂度


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        test_1();
    }

    private static void test_1() {

    }

}


// ------------------------------- 1、经典实现 --------------------------------


///**
// * 中介 -> 调度者
// */
//interface Mediator {
//    void handleEvent(Component component, String event);
//}
//
//public class LandingPageDialog implements Mediator {
//
//    private Button loginButton;
//    private Button regButton;
//    private Selection selection;
//    private Input usernameInput;
//    private Input passwordInput;
//    private Input repeatedPswdInput;
//    private Text hintText;
//
//
//    @Override
//    public void handleEvent(Component component, String event) {
//
//        if (component.equals(loginButton)) {
//
//            String username = usernameInput.text();
//            String password = passwordInput.text();
//
//            //校验数据...
//            //做业务处理...
//
//        } else if (component.equals(regButton)) {
//
//            //获取usernameInput、passwordInput、repeatedPswdInput数据...
//            //校验数据...
//            //做业务处理...
//
//        } else if (component.equals(selection)) {
//
//            String selectedItem = selection.select();
//
//            if (selectedItem.equals("login")) {
//
//                usernameInput.show();
//                passwordInput.show();
//                repeatedPswdInput.hide();
//                hintText.hide();
//                //...省略其他代码
//
//            } else if (selectedItem.equals("register")) {
//                //....
//            }
//
//        }
//    }
//}
//
//public class UIControl {
//
//    private static final String LOGIN_BTN_ID = "login_btn";
//    private static final String REG_BTN_ID = "reg_btn";
//    private static final String USERNAME_INPUT_ID = "username_input";
//    private static final String PASSWORD_INPUT_ID = "pswd_input";
//    private static final String REPEATED_PASSWORD_INPUT_ID = "repeated_pswd_input";
//    private static final String HINT_TEXT_ID = "hint_text";
//    private static final String SELECTION_ID = "selection";
//
//
//    public static void main(String[] args) {
//
//        Button loginButton = (Button) findViewById(LOGIN_BTN_ID);
//        Button regButton = (Button) findViewById(REG_BTN_ID);
//        Input usernameInput = (Input) findViewById(USERNAME_INPUT_ID);
//        Input passwordInput = (Input) findViewById(PASSWORD_INPUT_ID);
//        Input repeatedPswdInput = (Input) findViewById(REPEATED_PASSWORD_INPUT_ID);
//        Text hintText = (Text) findViewById(HINT_TEXT_ID);
//        Selection selection = (Selection) findViewById(SELECTION_ID);
//
//        Mediator dialog = new LandingPageDialog();
//        dialog.setLoginButton(loginButton);
//        dialog.setRegButton(regButton);
//        dialog.setUsernameInput(usernameInput);
//        dialog.setPasswordInput(passwordInput);
//        dialog.setRepeatedPswdInput(repeatedPswdInput);
//        dialog.setHintText(hintText);
//        dialog.setSelection(selection);
//
//
//        loginButton.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.handleEvent(loginButton, "click");
//            }
//        });
//
//        regButton.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.handleEvent(regButton, "click");
//            }
//        });
//
//        //....
//    }
//}