package com.example.boot3.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caimeng
 * @date 2024/4/11 11:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageChannel {
    private String host;
    private String token;
}
