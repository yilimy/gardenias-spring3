package com.example.boot3.json.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/6 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Positions implements Serializable {
    private String name;
    private List<Integer> pos;
}
