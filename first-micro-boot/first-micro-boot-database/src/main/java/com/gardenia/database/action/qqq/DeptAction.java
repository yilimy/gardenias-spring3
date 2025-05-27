package com.gardenia.database.action.qqq;

import com.gardenia.database.service.CompanyService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * @author caimeng
 * @date 2025/5/27 15:01
 */
@Slf4j
@RestController
@RequestMapping("/qqq/dept/*")
public class DeptAction {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;  // 会根据当前的程序包，自动进行切换
    @Autowired
    private CompanyService companyService;

    @SneakyThrows
    @RequestMapping("datasource")
    public Object echo() {
        log.info("【qqq】数据源：{}", dataSource);
        // test_qqq
        return dataSource.getConnection().getCatalog();
    }

    /**
     * 测试多数据源获取数据
     * @return 查询结果
     */
    @RequestMapping("company/list")
    public Object list() {
        return companyService.list();
    }
}
