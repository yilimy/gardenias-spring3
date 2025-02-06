package com.gardenia.web.vo;

import lombok.Data;
import lombok.NonNull;

/**
 * @author caimeng
 * @date 2025/1/22 14:27
 */
@Data   // 本身不会生成构造器，使用默认的无参构造器
public class DeptWithNonAttribute {
    /**
     * 该属性不允许为空
     * <p>
     *     自动生成了有参构造器，同时限制有参构造器的非空。
     *     没有生成默认的无参构造器
     *     public Dept(@NonNull Long deptNo, @NonNull String dName) {
     *         if (deptNo == null) {
     *             throw new NullPointerException("deptNo is marked non-null but is null");
     *         } else if (dName == null) {
     *             throw new NullPointerException("dName is marked non-null but is null");
     *         } else {
     *             this.deptNo = deptNo;
     *             this.dName = dName;
     *         }
     *     }
     * <p>
     *     setter方法的非空
     *     public void setDeptNo(@NonNull Long deptNo) {
     *         if (deptNo == null) {
     *             throw new NullPointerException("deptNo is marked non-null but is null");
     *         } else {
     *             this.deptNo = deptNo;
     *         }
     *     }
     */
    @NonNull
    private Long deptNo;
    @NonNull    // 该属性不允许为空
    private String dName;
    private Long loc;   // 该属性没有非空限制
}
