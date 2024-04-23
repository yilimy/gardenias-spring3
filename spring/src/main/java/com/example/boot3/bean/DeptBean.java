package com.example.boot3.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 单例bean
 * @author caimeng
 * @date 2024/4/10 17:16
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeptBean {
    private String deptNo;
    private String deptName;
    private String loc;

    @Override
    public String toString() {
        return "[" + super.hashCode() + "] DeptBean 对象实例";
    }

}
