package com.bebopze.jdk.patterndesign;

import static com.bebopze.jdk.patterndesign.State.*;

/**
 * 16. 状态模式         --->   状态机 的一种
 *
 * @author bebopze
 * @date 2020/8/12
 */
public class _16_State {


    // 核心：   状态机


    // 状态模式：
    //      状态机 的一种  实现方式


    //  状态机 的实现：
    //      1、分支逻辑法
    //      2、查表法
    //      3、状态模式


    // 状态机 的3个组成部分：
    //      状态（State）、事件（Event）、动作（Action）


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        // 1、分支逻辑法
        test_1();


        // 2、查表法
        test_2();


        // 3、状态模式
        test_3();


        test_4();
    }


    private static void test_1() {

        State1.MarioStateMachine mario = new State1.MarioStateMachine();

        mario.obtainMushRoom();

        int score = mario.getScore();
        State state = mario.getCurrentState();

        System.out.println("mario score: " + score + "; state: " + state);
    }

    private static void test_2() {

        State2.MarioStateMachine mario = new State2.MarioStateMachine();

        mario.obtainMushRoom();

        int score = mario.getScore();
        State state = mario.getCurrentState();

        System.out.println("mario score: " + score + "; state: " + state);
    }

    private static void test_3() {

//        State3.MarioStateMachine mario = new State3.MarioStateMachine();
//
//        mario.obtainMushRoom();
//
//        int score = mario.getScore();
//        State state = mario.getCurrentState();
//
//        System.out.println("mario score: " + score + "; state: " + state);
    }


    private static void test_4() {

        State4.MarioStateMachine mario = new State4.MarioStateMachine();

        mario.obtainMushRoom();

        int score = mario.getScore();
        State state = mario.getCurrentState();

        System.out.println("mario score: " + score + "; state: " + state);
    }
}


// ----------------------------------- 实现 ----------------------------------------


// ----------------------------------- 1、分支逻辑法 ----------------------------------------


class State1 {

    /**
     * 马里奥-状态机
     */
    public static class MarioStateMachine {
        private int score;
        private State currentState;

        public MarioStateMachine() {
            this.score = 0;
            this.currentState = SMALL;
        }

        /**
         * 获得🍄 -> SUPER 马里奥
         */
        public void obtainMushRoom() {
            if (currentState.equals(SMALL)) {
                this.currentState = SUPER;
                this.score += 100;
            }
        }

        /**
         * 获得披风 -> CAPE 马里奥
         */
        public void obtainCape() {
            if (currentState.equals(SMALL) || currentState.equals(SUPER)) {
                this.currentState = CAPE;
                this.score += 200;
            }
        }

        /**
         * 获得🔥 -> FIRE 马里奥
         */
        public void obtainFireFlower() {
            if (currentState.equals(SMALL) || currentState.equals(SUPER)) {
                this.currentState = FIRE;
                this.score += 300;
            }
        }

        /**
         * 撞到怪物👹 -> SMALL 马里奥
         */
        public void meetMonster() {
            if (currentState.equals(SUPER)) {
                this.currentState = SMALL;
                this.score -= 100;
                return;
            }

            if (currentState.equals(CAPE)) {
                this.currentState = SMALL;
                this.score -= 200;
                return;
            }

            if (currentState.equals(FIRE)) {
                this.currentState = SMALL;
                this.score -= 300;
                return;
            }
        }

        public int getScore() {
            return this.score;
        }

        public State getCurrentState() {
            return this.currentState;
        }
    }
}


enum State {
    SMALL(0), SUPER(1), FIRE(2), CAPE(3);
    private int value;

    private State(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}


// ----------------------------------- 2、查表法 --------------------------------------------


class State2 {


    public enum Event {
        GOT_MUSHROOM(0),
        GOT_CAPE(1),
        GOT_FIRE(2),
        MET_MONSTER(3);

        private int value;

        private Event(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class MarioStateMachine {
        private int score;
        private State currentState;

        private static final State[][] transitionTable = {
                {SUPER, CAPE, FIRE, SMALL},
                {SUPER, CAPE, FIRE, SMALL},
                {CAPE, CAPE, CAPE, SMALL},
                {FIRE, FIRE, FIRE, SMALL}
        };

        private static final int[][] actionTable = {
                {+100, +200, +300, +0},
                {+0, +200, +300, -100},
                {+0, +0, +0, -200},
                {+0, +0, +0, -300}
        };

        public MarioStateMachine() {
            this.score = 0;
            this.currentState = SMALL;
        }

        public void obtainMushRoom() {
            executeEvent(Event.GOT_MUSHROOM);
        }

        public void obtainCape() {
            executeEvent(Event.GOT_CAPE);
        }

        public void obtainFireFlower() {
            executeEvent(Event.GOT_FIRE);
        }

        public void meetMonster() {
            executeEvent(Event.MET_MONSTER);
        }

        private void executeEvent(Event event) {
            int stateValue = currentState.getValue();
            int eventValue = event.getValue();
            this.currentState = transitionTable[stateValue][eventValue];
            this.score = actionTable[stateValue][eventValue];
        }

        public int getScore() {
            return this.score;
        }

        public State getCurrentState() {
            return this.currentState;
        }
    }
}


// ----------------------------------- 3、状态模式 --------------------------------------------


class State3 {

    /**
     * 所有状态类的接口
     */
    public interface IMario {

        State getName();


        // ----------------- 以下是 定义的 事件 --------------------

        void obtainMushRoom();

        void obtainCape();

        void obtainFireFlower();

        void meetMonster();
    }

    class SmallMario implements IMario {
        private MarioStateMachine stateMachine;

        public SmallMario(MarioStateMachine stateMachine) {
            this.stateMachine = stateMachine;
        }

        @Override
        public State getName() {
            return State.SMALL;
        }

        @Override
        public void obtainMushRoom() {
            stateMachine.setCurrentState(new SuperMario(stateMachine));
            stateMachine.setScore(stateMachine.getScore() + 100);
        }

        @Override
        public void obtainCape() {
//            stateMachine.setCurrentState(new CapeMario(stateMachine));
//            stateMachine.setScore(stateMachine.getScore() + 200);
        }

        @Override
        public void obtainFireFlower() {
//            stateMachine.setCurrentState(new FireMario(stateMachine));
//            stateMachine.setScore(stateMachine.getScore() + 300);
        }

        @Override
        public void meetMonster() {
            // do nothing...
        }
    }

    public class SuperMario implements IMario {
        private MarioStateMachine stateMachine;

        public SuperMario(MarioStateMachine stateMachine) {
            this.stateMachine = stateMachine;
        }

        @Override
        public State getName() {
            return State.SUPER;
        }

        @Override
        public void obtainMushRoom() {
            // do nothing...
        }

        @Override
        public void obtainCape() {
//            stateMachine.setCurrentState(new CapeMario(stateMachine));
//            stateMachine.setScore(stateMachine.getScore() + 200);
        }

        @Override
        public void obtainFireFlower() {
//            stateMachine.setCurrentState(new FireMario(stateMachine));
//            stateMachine.setScore(stateMachine.getScore() + 300);
        }

        @Override
        public void meetMonster() {
            stateMachine.setCurrentState(new SmallMario(stateMachine));
            stateMachine.setScore(stateMachine.getScore() - 100);
        }
    }

    // 省略CapeMario、FireMario类...

    public class MarioStateMachine {
        private int score;
        private IMario currentState; // 不再使用枚举来表示状态

        public MarioStateMachine() {
            this.score = 0;
            this.currentState = new SmallMario(this);
        }

        public void obtainMushRoom() {
            this.currentState.obtainMushRoom();
        }

        public void obtainCape() {
            this.currentState.obtainCape();
        }

        public void obtainFireFlower() {
            this.currentState.obtainFireFlower();
        }

        public void meetMonster() {
            this.currentState.meetMonster();
        }

        public int getScore() {
            return this.score;
        }

        public State getCurrentState() {
            return this.currentState.getName();
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setCurrentState(IMario currentState) {
            this.currentState = currentState;
        }
    }

}


// ----------------------------------- 3_2、状态模式 --------------------------------------------


class State4 {

    interface IMario {

        State getName();


        // -------------------- 以下是 定义的 事件 ----------------------

        void obtainMushRoom(MarioStateMachine stateMachine);

        void obtainCape(MarioStateMachine stateMachine);

        void obtainFireFlower(MarioStateMachine stateMachine);

        void meetMonster(MarioStateMachine stateMachine);
    }


    static class SmallMario implements IMario {
        private static final SmallMario instance = new SmallMario();

        private SmallMario() {
        }

        public static SmallMario getInstance() {
            return instance;
        }

        @Override
        public State getName() {
            return State.SMALL;
        }

        @Override
        public void obtainMushRoom(MarioStateMachine stateMachine) {
//            stateMachine.setCurrentState(SuperMario.getInstance());
//            stateMachine.setScore(stateMachine.getScore() + 100);
        }

        @Override
        public void obtainCape(MarioStateMachine stateMachine) {
//            stateMachine.setCurrentState(CapeMario.getInstance());
//            stateMachine.setScore(stateMachine.getScore() + 200);
        }

        @Override
        public void obtainFireFlower(MarioStateMachine stateMachine) {
//            stateMachine.setCurrentState(FireMario.getInstance());
//            stateMachine.setScore(stateMachine.getScore() + 300);
        }

        @Override
        public void meetMonster(MarioStateMachine stateMachine) {
            // do nothing...
        }
    }


    // 省略SuperMario、CapeMario、FireMario类...


    /**
     * 马里奥 - 状态机
     */
    public static class MarioStateMachine {
        /**
         * 分数
         */
        private int score;
        /**
         * 当前状态：SmallMario/SuperMario/CapeMario/FireMario
         */
        private IMario currentState;

        public MarioStateMachine() {
            this.score = 0;
            this.currentState = SmallMario.getInstance();
        }

        public void obtainMushRoom() {
            this.currentState.obtainMushRoom(this);
        }

        public void obtainCape() {
            this.currentState.obtainCape(this);
        }

        public void obtainFireFlower() {
            this.currentState.obtainFireFlower(this);
        }

        public void meetMonster() {
            this.currentState.meetMonster(this);
        }

        public int getScore() {
            return this.score;
        }

        public State getCurrentState() {
            return this.currentState.getName();
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setCurrentState(IMario currentState) {
            this.currentState = currentState;
        }
    }

}