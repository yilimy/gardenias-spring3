package com.gardenia.autoconfig.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 通过一个特定的 ImportSelector 类，对需要注入的 Bean 进行集中管理
 * <p>
 *     ImportSelector的导入不能直接生效，需要结合 Import 去看
 *     {@link com.gardenia.autoconfig.config.YootkConfigurationWithImportSelector}
 * <p>
 *     在该类中可以做一些扫描操作，来进行 Bean 注入的批处理
 * @author caimeng
 * @date 2025/5/15 17:13
 */
public class DefaultImportSelector implements ImportSelector {
    @SuppressWarnings("NullableProblems")
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 指定要注入的 Bean 类全名
        return new String[]{"com.gardenia.autoconfig.vo.DeptWithSelector"};
    }
}
