//package org.example.trisceli;
//
//import com.sun.tools.javac.api.JavacTrees;
//import com.sun.tools.javac.processing.JavacProcessingEnvironment;
//import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.tree.TreeMaker;
//import com.sun.tools.javac.util.Context;
//
//import javax.annotation.processing.AbstractProcessor;
//import javax.annotation.processing.ProcessingEnvironment;
//import javax.annotation.processing.RoundEnvironment;
//import javax.lang.model.SourceVersion;
//import javax.lang.model.element.Element;
//import javax.lang.model.element.TypeElement;
//import javax.tools.Diagnostic;
//import java.lang.annotation.RetentionPolicy;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * 自定义插入式注解处理器
// * {@link javax.annotation.processing.AbstractProcessor} 就属于 Pluggable Annotation Processing API
// * 继承 Processor 后，需要依赖SPI发现机制，定义META.services
// * @author caimeng
// * @date 2024/7/26 10:38
// */
//public class TrisceliVersionProcessor extends AbstractProcessor {
//    private JavacTrees javacTrees;
//    private TreeMaker treeMaker;
//    private ProcessingEnvironment processingEnvironment;
//
//    /**
//     * 初始化处理器
//     * @param processingEnv environment to access facilities the tool framework
//     *                      提供一系列的实用工具
//     * provides to the processor
//     */
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        this.processingEnvironment = processingEnv;
//        this.javacTrees = JavacTrees.instance(processingEnv);
//        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
//        this.treeMaker = TreeMaker.instance(context);
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        /*
//         * 通过覆写 getSupportedSourceVersion 方法来指定支持的 Java 源代码版本
//         * 这里返回的是 RELEASE_17, 但是配置的编译版本是 8
//         */
//        return SourceVersion.latest();
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> set = new HashSet<>();
//        set.add(RetentionPolicy.class.getName());
//        return set;
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        for (TypeElement typeElement : annotations) {
//            // 获取到给定注解的 element (element可以是一个类、方法、包等)
//            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
//                // JCVariableDecl 为字段|变量定义语法树节点
//                JCTree.JCVariableDecl jcv = (JCTree.JCVariableDecl ) javacTrees.getTree(element);
//                String varType = jcv.vartype.type.toString();
//                // 限定变量类型为String，否则抛异常
//                if (!"java.lang.String".equals(varType)) {
//                    printErrorMessage(element, "Type '" + varType + "' is not support .");
//                }
//                jcv.init = treeMaker.Literal(getVersion());
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 利用 processingEnvironment 内的 Message 对象输出日志
//     * @param element element
//     * @param msg msg
//     */
//    private void printErrorMessage(Element element, String msg) {
//        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, element);
//    }
//
//    /**
//     * 获取 version
//     * @return 版本号
//     */
//    private String getVersion() {
//        // 这里省去获取逻辑，直接返回固定值
//        return "v1.0.1";
//    }
//}
