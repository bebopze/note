package com.bebopze.jdk.annotation;

import java.io.*;
import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import javax.tools.Diagnostic.Kind;


/**
 * custom annotation Processor             - 自定义 注解处理器         ==>  关键字  -->  .java 源代码   ->  非 字节码
 * -
 * -
 * - 3、生成 新的源代码
 *
 * @author bebopze
 * @date 2020/11/25
 */
@SupportedAnnotationTypes("com.bebopze.jdk.annotation.Adapt")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AdaptProcessor extends AbstractProcessor {





    // 注解处理器 主要有三个用途：
    //
    //    1、定义编译规则，并检查被编译的源文件      // @Override
    //
    //    2、修改 已有源代码                      // Lombok  ->  修改代码 ，添加Getter/Setter...            涉及了 javac的 内部API，因此并不推荐
    //
    //    3、生成 新的源代码                      // 较为常见，是 OpenJDK 工具jcstress，以及 JMH 生成测试代码的方式


    // -----------------------------------------------------------


    /**
     * - 3、生成 新的源代码
     * -
     * -
     * - 生成源代码的方式实际上非常容易理解：
     * -
     * -    我们可以通过 Filer.createSourceFile 方法获得一个 类似于文件的概念
     * -
     * -    并通过 PrintWriter 将具体的内容一一写入即可
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            if (!"com.bebopze.jdk.annotation.Adapt".equals(annotation.getQualifiedName().toString())) {
                continue;
            }

            ExecutableElement targetAsKey = getExecutable(annotation, "value");

            for (ExecutableElement annotatedMethod : ElementFilter.methodsIn(roundEnv.getElementsAnnotatedWith(annotation))) {
                if (!annotatedMethod.getModifiers().contains(Modifier.PUBLIC)) {
                    processingEnv.getMessager().printMessage(Kind.ERROR, "@Adapt on non-public method");
                    continue;
                }
                if (!annotatedMethod.getModifiers().contains(Modifier.STATIC)) {
                    // TODO support non-static methods
                    continue;
                }

                TypeElement targetInterface = getAnnotationValueAsTypeElement(annotatedMethod, annotation, targetAsKey);
                if (targetInterface.getKind() != ElementKind.INTERFACE) {
                    processingEnv.getMessager().printMessage(Kind.ERROR, "@Adapt with non-interface input");
                    continue;
                }

                TypeElement enclosingType = getTopLevelEnclosingType(annotatedMethod);
                createAdapter(enclosingType, annotatedMethod, targetInterface);
            }
        }
        return true;
    }


    /**
     * - 3、生成 新的源代码
     * -
     * -
     * - 生成源代码的方式实际上非常容易理解：
     * -
     * -    我们可以通过 Filer.createSourceFile 方法获得一个 类似于文件的概念
     * -
     * -    并通过 PrintWriter 将具体的内容一一写入即可
     *
     * @param enclosingClass
     * @param annotatedMethod
     * @param targetInterface
     */
    private void createAdapter(TypeElement enclosingClass, ExecutableElement annotatedMethod,
                               TypeElement targetInterface) {

        PackageElement packageElement = (PackageElement) enclosingClass.getEnclosingElement();
        String packageName = packageElement.getQualifiedName().toString();
        String className = enclosingClass.getSimpleName().toString();
        String methodName = annotatedMethod.getSimpleName().toString();
        String adapterName = className + "_" + methodName + "Adapter";

        ExecutableElement overriddenMethod = getFirstNonDefaultExecutable(targetInterface);

        try {
            Filer filer = processingEnv.getFiler();
            JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + adapterName, new Element[0]);

            try (PrintWriter out = new PrintWriter(sourceFile.openWriter())) {
                out.println("package " + packageName + ";");
                out.println("import " + targetInterface.getQualifiedName() + ";");
                out.println();
                out.println("public class " + adapterName + " implements " + targetInterface.getSimpleName() + " {");
                out.println("  @Override");
                out.println("  public " + overriddenMethod.getReturnType() + " " + overriddenMethod.getSimpleName()
                        + formatParameter(overriddenMethod, true) + " {");
                out.println("    return " + className + "." + methodName + formatParameter(overriddenMethod, false) + ";");
                out.println("  }");
                out.println("}");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ExecutableElement getExecutable(TypeElement annotation, String methodName) {

        for (ExecutableElement method : ElementFilter.methodsIn(annotation.getEnclosedElements())) {
            if (methodName.equals(method.getSimpleName().toString())) {
                return method;
            }
        }
        processingEnv.getMessager().printMessage(Kind.ERROR, "Incompatible @Adapt.");
        return null;
    }

    private ExecutableElement getFirstNonDefaultExecutable(TypeElement annotation) {
        for (ExecutableElement method : ElementFilter.methodsIn(annotation.getEnclosedElements())) {
            if (!method.isDefault()) {
                return method;
            }
        }
        processingEnv.getMessager().printMessage(Kind.ERROR,
                "Target interface should declare at least one non-default method.");
        return null;
    }

    private TypeElement getAnnotationValueAsTypeElement(ExecutableElement annotatedMethod, TypeElement annotation,
                                                        ExecutableElement annotationFunction) {
        TypeMirror annotationType = annotation.asType();

        for (AnnotationMirror annotationMirror : annotatedMethod.getAnnotationMirrors()) {
            if (processingEnv.getTypeUtils().isSameType(annotationMirror.getAnnotationType(), annotationType)) {
                AnnotationValue value = annotationMirror.getElementValues().get(annotationFunction);
                if (value == null) {
                    processingEnv.getMessager().printMessage(Kind.ERROR, "Unknown @Adapt target");
                    continue;
                }
                TypeMirror targetInterfaceTypeMirror = (TypeMirror) value.getValue();
                return (TypeElement) processingEnv.getTypeUtils().asElement(targetInterfaceTypeMirror);
            }
        }
        processingEnv.getMessager().printMessage(Kind.ERROR, "@Adapt should contain target()");
        return null;
    }

    private TypeElement getTopLevelEnclosingType(ExecutableElement annotatedMethod) {

        TypeElement enclosingType = null;
        Element enclosing = annotatedMethod.getEnclosingElement();

        while (enclosing != null) {
            if (enclosing.getKind() == ElementKind.CLASS) {
                enclosingType = (TypeElement) enclosing;
            } else if (enclosing.getKind() == ElementKind.PACKAGE) {
                break;
            }
            enclosing = enclosing.getEnclosingElement();
        }
        return enclosingType;
    }

    private String formatParameter(ExecutableElement method, boolean includeType) {

        StringBuilder builder = new StringBuilder();
        builder.append('(');
        String separator = "";

        for (VariableElement parameter : method.getParameters()) {
            builder.append(separator);
            if (includeType) {
                builder.append(parameter.asType());
                builder.append(' ');
            }
            builder.append(parameter.getSimpleName());
            separator = ", ";
        }
        builder.append(')');
        return builder.toString();
    }
}