package com.example.agent.source.transformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * 自定义 ClassFileTransformer 类
 * <p>
 *     读取加载的类，然后通过字节码工具进行解析和修改，对目标类进行增强。
 * @author caimeng
 * @date 2024/11/19 9:27
 */
public class AOPTransformer implements ClassFileTransformer {
    private final String className;

    public AOPTransformer(String className) {
        this.className = className;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        if (className == null) {
            // 返回null表示不修改类字节码，和返回 classfileBuffer 是一样的效果
            return null;
        }
        // 注意：该方法传入的 className 是 a/b/c 的格式，而不是 a.b.c
        if (className.equals(this.className.replace(".", "/"))) {
            System.out.println("Find " + this.className + " !");
            // 使用 javassist 技术，进行字节码插桩
            ClassPool classPool = ClassPool.getDefault();
            classPool.appendClassPath(new LoaderClassPath(loader));
            classPool.appendSystemPath();
            try {
                // 也可以通过 CtClass ctClass =  classPool.get(className) 方式获取
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
                for (CtMethod declaredMethod : declaredMethods) {
                    // 目标方法前后插桩
                    declaredMethod.insertBefore("System.out.println(\"before invoke "
                            + declaredMethod.getName() + " \");");
                    declaredMethod.insertAfter("System.out.println(\"after invoke "
                            + declaredMethod.getName() + " \");");
                }
                return ctClass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}
