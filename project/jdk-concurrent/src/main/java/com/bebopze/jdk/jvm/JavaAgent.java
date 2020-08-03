package com.bebopze.jdk.jvm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import static org.springframework.asm.Opcodes.ASM7;

/**
 * 字节码注入（bytecode instrumentation）
 *
 * @author bebopze
 * @date 2020/7/30
 */
public class JavaAgent {


    public static void premain(String args, Instrumentation instrumentation) {

        // 注册类加载事件的拦截器
//        instrumentation.addTransformer(new MyTransformer());

        instrumentation.addTransformer(new MyTransformer2());
    }


    /**
     * 自定义 类加载事件的拦截器
     * <p>
     * 基于 类加载事件 的 拦截功能，我们可以 实现 字节码注入（bytecode instrumentation），往 正在被加载的类中 插入 额外的字节码。
     */
    static class MyTransformer implements ClassFileTransformer {

        /**
         * @param loader
         * @param className
         * @param classBeingRedefined
         * @param protectionDomain
         * @param classfileBuffer
         * @return 代表更新过后的类的字节码
         * @throws IllegalClassFormatException
         */
        @Override
        public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

            System.out.printf("Loaded %s: 0x%X%X%X%X\n", className, classfileBuffer[0], classfileBuffer[1], classfileBuffer[2], classfileBuffer[3]);


            // transform方法 返回 null 或者 抛出异常，那么 Java 虚拟机将使用 原来的 byte 数组 完成类加载工作。
            return null;
        }
    }


    /**
     * 字节码工程框架 ASM -->  asm-tree （ 面向对象的方式注入字节码 ）
     */
    static class MyTransformer2 implements ClassFileTransformer, Opcodes {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

            ClassReader cr = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode(ASM7);
            cr.accept(classNode, ClassReader.SKIP_FRAMES);

            for (MethodNode methodNode : classNode.methods) {
                if ("main".equals(methodNode.name)) {
                    InsnList instrumentation = new InsnList();
                    instrumentation.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                    instrumentation.add(new LdcInsnNode("Hello, Instrumentation!"));
                    instrumentation.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
                    methodNode.instructions.insert(instrumentation);
                }
            }

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(cw);
            return cw.toByteArray();
        }
    }

}
