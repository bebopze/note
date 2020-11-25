package com.bebopze.jdk.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Set;


/**
 * custom annotation Processor                  - 自定义 注解处理器
 * -
 * -
 * - 1、定义编译规则，并检查被编译的源文件
 *
 * @author bebopze
 * @date 2020/11/25
 */
@SupportedAnnotationTypes("com.bebopze.jdk.annotation.CheckGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CheckGetterProcessor extends AbstractProcessor {


    // 注解处理器 主要有三个用途：
    //
    //    1、定义编译规则，并检查被编译的源文件      // @Override
    //
    //    2、修改 已有源代码                      // Lombok  ->  修改代码 ，添加Getter/Setter...            涉及了 javac的 内部API，因此并不推荐
    //
    //    3、生成 新的源代码                      // 较为常见，是 OpenJDK 工具jcstress，以及 JMH 生成测试代码的方式


    // -----------------------------------------------------------


    /**
     * 1、定义编译规则，并检查被编译的源文件
     *
     * @param annotations 该注解处理器所能处理的注解类型
     * @param roundEnv    囊括当前轮生成的抽象语法树的RoundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // TODO: annotated ElementKind.FIELD

        for (TypeElement annotatedClass : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(CheckGetter.class))) {
            for (VariableElement field : ElementFilter.fieldsIn(annotatedClass.getEnclosedElements())) {
                if (!containsGetter(annotatedClass, field.getSimpleName().toString())) {

                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("getter not found for '%s.%s'.", annotatedClass.getSimpleName(), field.getSimpleName())
                    );
                }
            }
        }
        return true;
    }


    private static boolean containsGetter(TypeElement typeElement, String name) {
        String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        for (ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
            if (!executableElement.getModifiers().contains(Modifier.STATIC)
                    && executableElement.getSimpleName().toString().equals(getter)
                    && executableElement.getParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
