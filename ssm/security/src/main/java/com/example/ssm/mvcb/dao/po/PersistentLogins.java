package com.example.ssm.mvcb.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Token持久化对象
 * @author caimeng
 * @date 2024/10/18 11:12
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersistentLogins implements Serializable {
    private String username;
    private String series;
    private String token;
    private Date last_used;
}
