package com.example.boot3.scan.integration.log;

import com.example.boot3.scan.enums.ForwardEnum;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author caimeng
 * @date 2024/5/22 10:48
 */
@Slf4j
@NoArgsConstructor
public class BusinessLogPointCut extends AspectJExpressionPointcut {

    /**
     * 扫描包路径，生成切面表达式
     * 参考 {@link ClassPathScanningCandidateComponentProvider#scanCandidateComponents(String)}
     * @param basePackage 包路径
     * @param cfiEnum 待匹配的枚举值
     * @return 切面表达式
     * @param <T> 枚举需要实现的接口类型
     */
    @SneakyThrows
    public static <T extends CodeFunctionInterface> String collectExpressionByScan(String basePackage, T[] cfiEnum) {
        List<String> expressionList = new ArrayList<>();
        List<CodeFunctionInterface> targetEnums = Stream.of(ForwardEnum.values())
                .flatMap(fe -> Stream.of(cfiEnum)
                        .filter(f -> f.getCode().equals(fe.getCode())))
                .collect(Collectors.toList());
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + '/' + "**/*.class";
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        for (Resource resource : resources) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
            log.debug("annotationMetadata = {}", annotationMetadata);
            MergedAnnotation<Service> serviceMergedAnnotation = annotationMetadata.getAnnotations().get(Service.class);
            Object value = serviceMergedAnnotation.getValue("value").orElse(null);
            if (Objects.isNull(value)) {
                MergedAnnotation<Component> componentMergedAnnotation = annotationMetadata.getAnnotations().get(Component.class);
                value = serviceMergedAnnotation.getValue("value").orElse(null);
            }
            if (Objects.isNull(value)) {
                continue;
            }
            final String serviceName = (String) value;
            List<String> funList = targetEnums.stream().filter(e -> e.getService().equals(serviceName)).map(CodeFunctionInterface::getMethod).collect(Collectors.toList());
            String className = metadataReader.getClassMetadata().getClassName();
            funList.forEach(fun -> expressionList.add(String.format("execution(* %s.%s(..))", className, fun)));
        }
        if (expressionList.isEmpty()) {
            return "";
        }
        return String.join("||", expressionList);
    }

    protected static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage));
    }
}
