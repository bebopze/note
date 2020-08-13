package com.bebopze.jdk.patterndesign;

import java.util.Scanner;
import java.util.Stack;

/**
 * 19. 备忘录模式              --->   也叫  快照（Snapshot）模式
 *
 * @author bebopze
 * @date 2020/8/13
 */
public class _19_Memento {


    // 核心：   快照、  副本 -> 恢复


    //


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        test_1();
    }


    private static void test_1() {

        InputText inputText = new InputText();
        SnapshotHolder snapshotsHolder = new SnapshotHolder();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {

            String input = scanner.next();

            switch (input) {

                case ":list":
                    System.out.println(inputText.getText());
                    break;

                case ":undo":
                    Snapshot snapshot = snapshotsHolder.popSnapshot();
                    inputText.restoreSnapshot(snapshot);
                    break;

                case ":exit":
                    System.exit(0);

                default:
                    snapshotsHolder.pushSnapshot(inputText.createSnapshot());
                    inputText.append(input);
                    break;
            }
        }
    }

}


// ------------------------------- 1、经典实现 ------------------------------------

/**
 * 源文件
 */
class InputText {
    private StringBuilder text = new StringBuilder();

    public String getText() {
        return text.toString();
    }

    public void append(String input) {
        text.append(input);
    }

    public Snapshot createSnapshot() {
        return new Snapshot(text.toString());
    }

    public void restoreSnapshot(Snapshot snapshot) {
        this.text.replace(0, this.text.length(), snapshot.getText());
    }
}

/**
 * 快照      -->  只有get、 无set  ===>  不变
 */
class Snapshot {
    private String text;

    public Snapshot(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}

/**
 * 快照存储容器
 */
class SnapshotHolder {
    private Stack<Snapshot> snapshots = new Stack<>();

    public Snapshot popSnapshot() {
        return snapshots.pop();
    }

    public void pushSnapshot(Snapshot snapshot) {
        snapshots.push(snapshot);
    }
}



