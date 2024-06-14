package org.example.mock.spring;

import lombok.SneakyThrows;
import org.example.mock.service.config.AppConfig;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 模拟spring容器
 * @author caimeng
 * @date 2024/6/12 18:54
 */
public class IApplicationContext {
    private final Class<?> configClass;
    private final ConcurrentMap<String, IBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private final List<IBeanPostProcessor> beanPostProcessorList = new ArrayList<>();
    private final boolean printAble;

    public IApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        // 本类的打印控制
        printAble = AppConfig.printEnable(this.getClass());
        // 扫描 --> BeanDefinition --> beanDefinitionMap
        doScan();
        // 实例化单例bean, BeanDefinition --> beanObject
        doCreateSingletonBeans();
    }

    /**
     * 扫描包下的类，生成bean定义
     */
    private void doScan() {
        // 判断注解是否存在
        if (configClass.isAnnotationPresent(IComponentScan.class)) {
            // 获取注解上的属性信息
            IComponentScan annotation = configClass.getAnnotation(IComponentScan.class);
            // 获取到扫描的包名：org.example.mock.service
            String[] scanPackage = annotation.value();
            for (String packageName : scanPackage) {
                doScanPackage(packageName);
            }
        }
    }

    /**
     * 扫描
     * 检查指定的类中是否有扫描需要的注解 {@link IComponentScan}
     */
    @SneakyThrows
    private void doScanPackage(String scanPackage) {
        /*
         *目标：从当前地址获取到编译后的#{scanPackage}包路径下的.class文件，需要先知道运行的根地址
         * 运行的根地址可以从classpath中获取到，这个部分信息被封装在ClassLoader中
         * 因此可以通过 ClassLoader 直接读取该相对路径下的资源
         */
        ClassLoader classLoader = getClass().getClassLoader();
        // 包名转换为目录格式： org.example.mock.service ==> org/example/mock/service
        String path = scanPackage.replace(".", "/");
        // 得到的是文件夹
        URL resource = classLoader.getResource(path);
        assert Objects.nonNull(resource) : "扫描的目标地址不存在";
        File file = new File(resource.getFile());
        print("scan: " + file);
        cycled:
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                break cycled;
            }
            String rootPath = rootPath();
//                print("rootPath = " + rootPath);
            for (File f : files) {
                String fullFileName = f.getAbsolutePath();
                // 判断是否是一个clas文件
                if (!fullFileName.endsWith(".class")) {
                    continue ;
                }
                // .class 长度 6
                String className = fullFileName.substring(rootPath.length(), fullFileName.length() - 6);
                className = className.replace("\\", ".");
//                    print("className = " + className);
                /*
                 * 此时获取到的只是文件，通过类加载器加载类
                 * 类加载器接收参数的格式：org.example.mock.service.beans.UserService
                 */
                Class<?> clazz = classLoader.loadClass(className);
                // 判断类头上是否存在bean注解(IComponent)
                if (clazz.isAnnotationPresent(IComponent.class)) {
                    // 检查是否实现了 IBeanPostProcessor
                    checkPostProcessor(clazz);
                    // 实例化并保存单例bean
                    String scopeValue = IScope.SINGLETON;
                    if (clazz.isAnnotationPresent(IScope.class)) {
                        scopeValue = clazz.getAnnotation(IScope.class).value();
                    }
                    // 定义一个Bean
                    IBeanDefinition beanDefinition = IBeanDefinition.builder()
                            .type(clazz)
                            .scope(scopeValue)
                            .build();
                    String beanName = genBeanName(clazz);
                    beanDefinitionMap.put(beanName, beanDefinition);
                }
            }
        }

    }

    /**
     * 判断是否为 {@link IBeanPostProcessor}
     * 如果是，则实例化并存放到 beanPostProcessorList 中
     * @param clazz 扫描到的类
     */
    @SneakyThrows
    private void checkPostProcessor(Class<?> clazz) {
        /*
         * 两个类判断父子关系，父 isAssignableFrom 子
         * isAssignableFrom 是否派生
         */
        if (IBeanPostProcessor.class.isAssignableFrom(clazz)) {
            IBeanPostProcessor instance = (IBeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
            beanPostProcessorList.add(instance);
        }
    }

    /**
     * 获取到根路径
     * D:\Gardenias\SpringBoot3Demo\spring-mock\target\classes\
     * @return 根路径
     */
    private String rootPath() {
        String root = Optional.of(getClass()).map(clazz -> clazz.getResource("")).map(URL::getFile).orElse("");
        File rootFile = new File(root);
        // D:\Gardenias\SpringBoot3Demo\spring-mock\target\classes\org\example\mock\spring
        String absolutePath = rootFile.getAbsolutePath();
        // org.example.mock.spring
        String packageName = getClass().getPackageName();
        int suffixLen = packageName.length();
        return absolutePath.substring(0, absolutePath.length() - suffixLen);
    }

    /**
     * 生成bean的名字
     * @param clazz 类
     * @return bean的名字
     */
    private String genBeanName(Class<?> clazz){
        return Optional.of(clazz)
                .map(c -> clazz.getAnnotation(IComponent.class))
                .map(IComponent::value)
                .filter(v -> v.length() > 0)
                // 获取类的简单名，并首字母小写（双大写开头除外）
                .orElse(Introspector.decapitalize(clazz.getSimpleName()));
    }

    /**
     * 根据扫描出的BeanDefinition对象，创建单例bean
     */
    private void doCreateSingletonBeans() {
        /*
         * 视频中是根据keySet去获取map(value)的方式，读取的数据，是否有线程安全方面的考量？
         */
        beanDefinitionMap.forEach((k, bd) -> {
            if (IScope.SINGLETON.equals(bd.getScope())
                    // 已经实例化的不再执行实例化
                    && Objects.isNull(singletonObjects.get(k))) {
                Object singletonBean = createBean(k, bd);
                singletonObjects.put(k, singletonBean);
            }
        });
    }

    /**
     * 根据bean定义创建bean
     * @param beanName bean的名字
     * @param beanDefinition bean的定义
     * @return bean对象
     */
    @SneakyThrows
    private Object createBean(String beanName, IBeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getType();
        Object instance = clazz.getConstructor().newInstance();
        // 属性注入
        injectAutoWired(clazz, instance);
        // 检查回调
        doBeanNameAware(instance, beanName);
        // 后置处理器的before调用
        instance = beforeBeanPostProcessor(instance, beanName);
        // 初始化
        doInit(instance);
        // BeanPostProcessor 初始化后，执行aop
        // 后置处理器的after调用
        instance = afterBeanPostProcessor(instance, beanName);
        print("bean : complete : " + beanName);
        print("-".repeat(50));
        return instance;
    }

    /**
     * 简单版的依赖注入
     * 给标记了 {@link IAutowired} 注解的属性进行赋值
     */
    @SneakyThrows
    private void injectAutoWired(Class<?> clazz, Object instance) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(IAutowired.class)) {
                field.setAccessible(true);
                /*
                 * 原则上，先 by Type，再 by Name
                 * 这里为了方便，直接 by Name
                 */
                field.set(instance, getBean(field.getName()));
            }
        }
    }

    /**
     * 检查并执行 {@link IBeanNameAware} 的回调
     * @param instance bean的实例
     * @param beanName bean的名字
     */
    private void doBeanNameAware(Object instance, String beanName) {
        if (instance instanceof IBeanNameAware obj) {
            obj.setBeanName(beanName);
        }
    }

    private Object beforeBeanPostProcessor(Object bean, String beanName) {
        for (IBeanPostProcessor iBeanPostProcessor : beanPostProcessorList) {
            bean = iBeanPostProcessor.postProcessBeforeInitialization(bean, beanName);
        }
        return bean;
    }

    /**
     * 初始化方法调用
     * @param instance bean的实例
     */
    private void doInit(Object instance) {
        if (instance instanceof IInitializingBean obj) {
            obj.afterPropertiesSet();
        }
    }

    private Object afterBeanPostProcessor(Object bean, String beanName) {
        for (IBeanPostProcessor iBeanPostProcessor : beanPostProcessorList) {
            bean = iBeanPostProcessor.postProcessAfterInitialization(bean, beanName);
        }
        return bean;
    }

    public Object getBean(String beanName) {
        IBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (Objects.isNull(beanDefinition)) {
            throw new RuntimeException("没有找到" + beanName + "对应的BeanDefinition");
        }
        if (IScope.SINGLETON.equals(beanDefinition.getScope())) {
            // 单例
            Object bean = singletonObjects.get(beanName);
            if (Objects.isNull(bean)) {
                // 单例池中没有找到，执行bean创建，并将bean放入单例池
                bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
            return bean;
        } else if (IScope.PROTOTYPE.equals(beanDefinition.getScope())) {
            // 原型，每一次获取时，创建一个对象
            return createBean(beanName, beanDefinition);
        } else {
            throw new IllegalArgumentException("不合适的作用域类型");
        }
    }

    private void print(String msg) {
        if (printAble) {
            System.out.println(msg);
        }
    }
}
