package com.gardenia.web.vo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author caimeng
 * @date 2025/1/22 14:39
 */
@Data
/*
 * 根据必需参数，创建一个名为"getInstance"静态方法
 * public static Emp getInstance(@NonNull Long empNo) {
 *      return new Emp(empNo);
 *  }
 */
@RequiredArgsConstructor(staticName = "getInstance")
public class Emp {
    /**
     * 与NoArgsConstructor注解冲突: 有非空属性，就不应该有无参构造。
     * 虽然IDEA会飘红，但只是不能生成带校验的有参构造，setter方法中的非空校验依旧有效。
     */
    @NonNull
    private Long empNo;
    private String empName;
    private Double salary;
}
