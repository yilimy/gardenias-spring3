package com.example.ssm.mvcb.service.impl;

import com.example.ssm.mvcb.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 子类不需要有任何Security的注解了，因为父接口上已经有了
 * @author caimeng
 * @date 2024/10/14 18:49
 */
@Slf4j
@Service
public class IAdminServiceImpl implements IAdminService {
    @Override   
    public boolean add() {
        return false;
    }

    @Override
    public boolean edite() {
        return false;
    }

    @Override
    public Object delete(List<String> ids) {
        log.info("执行了业务删除, ids={}", ids);
        return null;
    }

    @Override
    public String get(String username) {
        log.info("get username={}", username);
        return username;
    }

    @Override
    public Object list() {
        return List.of("沐言", "小李老师");
    }
}
