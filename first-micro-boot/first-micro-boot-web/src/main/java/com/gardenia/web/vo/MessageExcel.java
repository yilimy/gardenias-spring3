package com.gardenia.web.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/1/22 12:00
 */
@Data
public class MessageExcel {
    @Excel(name = "信息的标题", width = 30)      // 生成Excel表格列的配置
    private String title;
    @Excel(name = "信息的发布日期", orderNum = "1", width = 50)
    private Date pubDate;
    @Excel(name = "信息内容", orderNum = "2", width = 50)
    private String content;
}
