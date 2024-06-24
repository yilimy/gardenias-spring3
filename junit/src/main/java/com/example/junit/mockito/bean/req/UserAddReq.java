package com.example.junit.mockito.bean.req;

import lombok.Data;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/21 16:45
 */
@Data
public class UserAddReq {
    private String username;
    private String phone;
    private List<String> features;
}
