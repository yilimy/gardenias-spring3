package com.example.boot3.spring.cache.po;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caimeng
 * @date 2024/7/18 17:34
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Emp2 implements Serializable {
    @Id
    private String eid;
    private String ename;
    private String job;
    private Double salary;
}
