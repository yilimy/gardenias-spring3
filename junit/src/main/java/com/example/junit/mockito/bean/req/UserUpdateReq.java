package com.example.junit.mockito.bean.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caimeng
 * @date 2024/6/28 17:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateReq {
    private Long id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 电话
     */
    private String phone;
}
