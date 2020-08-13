package com.bebopze.jdk.patterndesign;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

import static com.bebopze.jdk.patterndesign.Event.*;

/**
 * 20. 命令模式
 *
 * @author bebopze
 * @date 2020/8/13
 */
public class _20_Command {


    // 核心：   函数 -> 对象


    // 借助命令模式，我们将函数封装成对象，这样就可以实现把函数像对象一样使用。


    // 场景：
    //      游戏开发（天天酷跑、QQ飞车）


    // Command 对象  --->  封装了客户端请求：event + data  ---> 内部实现了 execute()
    //
    // ===>  将函数封装成了对象：
    //
    // 将 event + data 通过构造 传递、封装到了 ----> Command内部   --->  最终传递、封装到了 执行函数 execute() 中
    //
    // ====》  request（event、data） -->  execute()  -->  Command 对象


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        test_GameApp();
    }


    private static void test_GameApp() {

        GameApp.mainLoop();
    }


}


// ------------------------------- 1、经典实现 --------------------------------


/**
 * Command 模式      ====>    将  函数 -> 对象
 */
interface Command {
    /**
     * 执行函数
     */
    void execute();
}


class GotDiamondCommand implements Command {

    /**
     * 数据
     */
    Object data;

    public GotDiamondCommand(Object data) {
        this.data = data;
    }

    @Override
    public void execute() {
        System.out.println("Got Diamond Command  .... " + data);
    }
}

class GotStartCommand implements Command {

    Object data;

    public GotStartCommand(Object data) {
        this.data = data;
    }

    @Override
    public void execute() {
        System.out.println("Got Start Command    .... " + data);
    }
}

class HitObstacleCommand implements Command {

    Object data;

    public HitObstacleCommand(Object data) {
        this.data = data;
    }

    @Override
    public void execute() {
        System.out.println("Hit Obstacle Command .... " + data);
    }
}

class ArchiveCommand implements Command {

    Object data;

    public ArchiveCommand(Object data) {
        this.data = data;
    }

    @Override
    public void execute() {
        System.out.println("Archive Command      .... " + data);
    }
}


// ------------------ 请求： 指令 + 数据 --------------------


// 客户端发送给服务器的请求，一般都包括两部分内容：指令和数据。
// 指令 我们也可以叫作 事件
// 数据 是 执行这个指令 所需的数据


/**
 * 请求： 指令（事件） +  数据
 */
@Data
@AllArgsConstructor
class Request {

    /**
     * 1、指令 -> 事件
     */
    Event event;

    /**
     * 2、数据
     */
    Object data;
}

enum Event {
    GOT_DIAMOND, GOT_STAR, HIT_OBSTACLE, ARCHIVE;
}


// --------------------------------------- 手游后台 ------------------------------------------------

// -------------- 天天酷跑、QQ飞车¬


class GameApp {

    static final int MAX_HANDLED_REQ_COUNT_PER_LOOP = 100;


    public static void mainLoop() {

        Queue<Command> queue = new LinkedList<>();
        List<Request> requests = new ArrayList<>();


        while (true) {

            // 1、手游后端服务器 轮询获取客户端发来的请求
            acceptRequest(requests);

            // 2、获取到请求之后，借助命令模式，把请求包含的数据和处理逻辑封装为命令对象，并存储在内存队列中。
            request_2_command(requests, queue);

            // 3、从队列中取出一定数量的命令来执行
            executeCommand(queue);


            // 4、执行完成之后，再重新开始新的一轮轮询
        }
    }


    /**
     * 1、手游后端服务器 轮询获取客户端发来的请求
     *
     * @param requests
     * @return
     */
    private static List<Request> acceptRequest(List<Request> requests) {

        // 从epoll或者select中获取数据，并封装成Request

        // 注意设置超时时间，如果很长时间没有接收到请求，就继续下面的逻辑处理。


        Request request_1 = new Request(GOT_DIAMOND, 1);
        Request request_2 = new Request(GOT_STAR, 2);
        Request request_3 = new Request(HIT_OBSTACLE, 3);
        Request request_4 = new Request(ARCHIVE, 4);

        requests.add(request_1);
        requests.add(request_2);
        requests.add(request_3);
        requests.add(request_4);

        return requests;
    }

    /**
     * 2、获取到请求之后，借助命令模式，把请求包含的数据和处理逻辑封装为命令对象，并存储在内存队列中。
     *
     * @param requests
     * @param queue
     */
    private static void request_2_command(List<Request> requests, Queue<Command> queue) {

        for (Request request : requests) {

            // ------------ 请求： 指令(事件)  +  数据 --------------------

            // 指令（事件）
            Event event = request.getEvent();
            // 数据
            Object data = request.getData();


            // Command 对象  --->  封装了客户端请求：event + data  ---> 内部实现了 execute()
            //
            // ===>  将函数封装成了对象：
            //
            // 将 event + data 通过构造 传递、封装到了 ----> Command内部   --->  最终传递、封装到了 执行函数 execute() 中
            //
            // ====》  request（event、data） -->  execute()  -->  Command 对象

            Command command = null;

            if (GOT_DIAMOND.equals(event)) {

                command = new GotDiamondCommand(data);

            } else if (GOT_STAR.equals(event)) {

                command = new GotStartCommand(data);

            } else if (HIT_OBSTACLE.equals(event)) {

                command = new HitObstacleCommand(data);

            } else if (ARCHIVE.equals(event)) {

                command = new ArchiveCommand(data);
            }

            // ...else if...

            queue.add(command);
        }
    }

    /**
     * 3、从队列中取出一定数量的命令来执行。  执行完成之后，再重新开始新的一轮轮询。
     *
     * @param queue
     */
    private static void executeCommand(Queue<Command> queue) {
        int handledCount = 0;
        while (handledCount < MAX_HANDLED_REQ_COUNT_PER_LOOP) {
            if (queue.isEmpty()) {
                break;
            }
            Command command = queue.poll();
            command.execute();
        }
    }
}