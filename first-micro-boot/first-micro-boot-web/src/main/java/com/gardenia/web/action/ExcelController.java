package com.gardenia.web.action;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import com.gardenia.web.vo.MessageExcel;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author caimeng
 * @date 2025/1/23 16:05
 */
@Controller     // 使用的是普通控制器的注解
@RequestMapping("/data/*")
public class ExcelController {

    /**
     * 返回一个Excel文件流 | 生成 excel
     * @param response 响应体
     */
    @SneakyThrows
    @GetMapping("excel")
    public void createExcelData(HttpServletResponse response) {
        /*
         * 设置响应类型
         * 到 tomcat 的 web.xml 文件中搜索 excel，我们选取xlsx标签的值
         */
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 强制性开启下载，并设置下载的文件名称
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename("gardenia_" + System.currentTimeMillis() + ".xls").build().toString());
        // 读取数据
        List<MessageExcel> messageExcelList = detectData();
        // 进行XLS文件内容配置
        ExportParams exportParams = new ExportParams(
                // 本标签中的内容标题
                "沐言科技消息管理",
                // 标签名
                "最新消息",
                // office 2007前 - HSSF
                // office 2007后 - XSSF
                ExcelType.XSSF);
        // 创建工作簿
        @Cleanup
        Workbook workbook = new XSSFWorkbook();
        /*
         * 导出服务
         * 与之相对的，还有导入服务: cn.afterturn.easypoi.excel.imports.ExcelImportService
         */
        new ExcelExportService().createSheet(
                // 工作簿
                workbook,
                // 参数
                exportParams,
                // 映射对象
                MessageExcel.class,
                // 数据集
                messageExcelList);
        // 数据写入返回流
        workbook.write(response.getOutputStream());
    }

    /**
     * 收集数据并返回对象集
     * @return excel映射对象集合
     */
    private static List<MessageExcel> detectData() {
        // 测试数据
        String[] titles = {"沐言科技", "李兴华编程训练营", "人人分享公益直播"};
        String[] contents = {"www.yootk.com", "edu.yootk.com", "share.yootk.com"};
        // 数据与对象的映射
        List<MessageExcel> messageExcelList = new ArrayList<>();
        MessageExcel me;
        for (int i = 0; i <titles.length; i++) {
            me = new MessageExcel();
            me.setTitle(titles[i]);
            me.setContent(contents[i]);
            me.setPubDate(new Date());
            messageExcelList.add(me);
        }
        return messageExcelList;
    }
}
