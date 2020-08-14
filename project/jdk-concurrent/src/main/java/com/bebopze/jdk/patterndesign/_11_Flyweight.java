package com.bebopze.jdk.patterndesign;

import java.util.HashMap;
import java.util.Map;

/**
 * 11. 享元模式
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class _11_Flyweight {


    // 核心：          不变


    // 意图：
    //      复用对象，节省内存。


    // 实现：
    //      通过工厂模式，在工厂类中，通过一个 Map 或者 List 来缓存 已经创建好的享元对象，以达到复用的目的。


    // ---------------------------------------------------------------


    public static void main(String[] args) {

        test__Chess();


        test__Integer();

        test__String();
    }


    private static void test__Chess() {

        System.out.println("----------------------Chess ...");

        // 开棋局
        ChessBoard chessBoard = new ChessBoard();


        // 黑方 - 出車
        chessBoard.move(1, 1, 1);

        // 红方 - 跳马
        chessBoard.move(18, 2, 3);


        // ...
    }


    private static void test__Integer() {

        System.out.println("----------------------Integer 缓存");


        Integer i1 = 56;
        Integer i2 = 56;
        Integer i3 = 129;
        Integer i4 = 129;

        System.out.println(i1 == i2);
        System.out.println(i3 == i4);
    }

    private static void test__String() {


        System.out.println("----------------------String 字符串常量池");

        String s1 = "小争哥";
        String s2 = "小争哥";
        String s3 = new String("小争哥");

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
    }

}


// ------------------------------------------------------ 实现 ----------------------------------------------------------


/**
 * 享元类
 */
class ChessPieceUnit {

    private int id;
    private String text;
    private Color color;


    public ChessPieceUnit(int id, String text, Color color) {
        this.id = id;
        this.text = text;
        this.color = color;
    }

    enum Color {
        RED, BLACK
    }

    // ...省略其他属性和getter方法...
}


/**
 * - 实现：
 * -    通过工厂模式，在工厂类中，通过一个 Map 或者 List 来缓存 已经创建好的享元对象，以达到复用的目的。
 */
class ChessPieceUnitFactory {

    /**
     * 缓存 创建好的享元对象 -> 16个象棋棋子 x 2
     */
    private static final Map<Integer, ChessPieceUnit> pieces = new HashMap<>();

    static {
        // 車-黑
        pieces.put(1, new ChessPieceUnit(1, "車", ChessPieceUnit.Color.BLACK));
        pieces.put(2, new ChessPieceUnit(2, "馬", ChessPieceUnit.Color.BLACK));


        //...省略摆放其他棋子的代码...


        // 車-红
        pieces.put(17, new ChessPieceUnit(17, "車", ChessPieceUnit.Color.RED));
        pieces.put(18, new ChessPieceUnit(18, "馬", ChessPieceUnit.Color.RED));
    }

    public static ChessPieceUnit getChessPiece(int chessPieceId) {
        return pieces.get(chessPieceId);
    }
}


/**
 * 棋子
 */
class ChessPiece {

    /**
     * 棋子  --> 固定16个，可设计为 享元模式 -->  不可变对象
     */
    private ChessPieceUnit chessPieceUnit;

    /**
     * 棋子-位置
     */
    private int positionX;
    private int positionY;


    public ChessPiece(ChessPieceUnit unit, int positionX, int positionY) {
        this.chessPieceUnit = unit;
        this.positionX = positionX;
        this.positionY = positionY;
    }


    ChessPiece setPositionXY(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        return this;
    }
}


/**
 * 棋局
 */
class ChessBoard {

    /**
     * 棋子ID - 棋子 享元对象
     */
    private Map<Integer, ChessPiece> chessPieces = new HashMap<>();

    public ChessBoard() {
        init();
    }

    private void init() {
        chessPieces.put(1, new ChessPiece(ChessPieceUnitFactory.getChessPiece(1), 0, 0));
        chessPieces.put(2, new ChessPiece(ChessPieceUnitFactory.getChessPiece(2), 1, 0));


        //...省略摆放其他棋子的代码...

        chessPieces.put(17, new ChessPiece(ChessPieceUnitFactory.getChessPiece(1), 0, 0));
        chessPieces.put(18, new ChessPiece(ChessPieceUnitFactory.getChessPiece(2), 1, 0));
    }

    public void move(int chessPieceId, int toPositionX, int toPositionY) {
        chessPieces.put(chessPieceId, chessPieces.get(chessPieceId).setPositionXY(toPositionX, toPositionY));
    }
}