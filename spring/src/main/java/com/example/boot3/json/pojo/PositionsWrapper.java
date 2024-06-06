package com.example.boot3.json.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author caimeng
 * @date 2024/6/6 11:14
 */
@Data
public class PositionsWrapper {
    private List<Positions> one;
    private List<Positions> two;
}
