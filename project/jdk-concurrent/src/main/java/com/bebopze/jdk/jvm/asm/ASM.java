package com.bebopze.jdk.jvm.asm;

/**
 * @author bebopze
 * @date 2020/7/23
 */
public class ASM {

    /**
     * native method        // javac -h . com/bebopze/jdk/jvm/asm/ASM.java
     *
     * @return
     */
    public native int hashcode();


    //    @HotSpotIntrinsicCandidate
    public static void main(String[] args) {

        test_ASMifier();
    }

    private static void test_ASMifier() {


        // javac ASM.java       --->   ASM.class

        // javac -h . com/bebopze/jdk/jvm/asm/ASM.java


        System.out.println("ASMifier-------------");


//        $ java -cp /PATH/TO/asm-all-6.0_BETA.jar org.objectweb.asm.util.ASMifier Foo.class | tee FooDump.java

//          java -cp  .../rt.jar  xx.xx.ASMifier  Demo.class | tee DemoDump.java

//          java -cp /Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home/jre/lib/rt.jar jdk.internal.org.objectweb.asm.util.ASMifier ASM.class | tee ASMDump.java


    }
}
