package com.gardenia.web.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author caimeng
 * @date 2025/1/22 16:46
 */
@Data
/*
 * 1. 含有受限的全量参数构造器，没有无参构造器
 * 2. 含有静态的建造器 CompanyBuilder, 构建方法builder() 创建 Company 对象
 * 3. 默认依旧会提供setter和getter方法，与其他框架兼容性比较好
 */
@Builder
public class Company {
    private String name;
    private String location;
}
