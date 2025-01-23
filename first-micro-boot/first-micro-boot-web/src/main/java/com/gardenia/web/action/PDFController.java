package com.gardenia.web.action;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caimeng
 * @date 2025/1/23 16:05
 */
@Controller     // 使用的是普通控制器的注解
@RequestMapping("/data/*")
public class PDFController {

    /**
     * 返回一个pdf文件流 | 生成pdf
     * @param response 响应体
     */
    @SneakyThrows
    @GetMapping("pdf")
    public void createPDFData(HttpServletResponse response) {
        // 设置响应类型
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        // 强制性开启下载，并设置下载的文件名称
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename("gardenia_" + System.currentTimeMillis() + ".pdf").build().toString());
        Rectangle rectangle = PageSize.A4;
        // 使用itext在内存中形成pdf文件
        Document document = new Document(rectangle, 10, 10, 50, 20);     // 设置页面大小和边距
        // 获取PDF的输出流
        PdfWriter.getInstance(document, response.getOutputStream());
        // 开始构建pdf文件内容
        document.open();
        // 当前程序中已经提供了资源文件，利用资源匹配符进行资源路径的定义
        ClassPathResource imageResource = new ClassPathResource("/images/IMG_0669.png");
        // 根据指定的路径加载图片
        Image img = Image.getInstance(imageResource.getFile().getAbsolutePath());
        // 方法会自动计算合适的缩放比例，确保图像在缩放后不会失真
        img.scaleToFit(rectangle.getWidth() / 3, rectangle.getHeight());
        // PDF 文件在生成的时候，是基于坐标的方式实现绘制的
        float pointX = (rectangle.getWidth() - img.getScaledWidth()) / 2;
        float pointY = rectangle.getHeight() - img.getScaledHeight() - 200;
        // 绘制图片坐标定义
        img.setAbsolutePosition(pointX, pointY);
        // 将图片追加到文档中
        document.add(img);
        // 追加文字段落前，换三行
        document.add(new Paragraph("\n".repeat(3)));
        // 如果想要输出文字，并且输出中文，必需设置字体
        ClassPathResource fontResource = new ClassPathResource("/fonts/AlibabaPuHuiTi-3-45-Light.ttf");
        BaseFont baseFont = BaseFont.createFont(fontResource.getFile().getAbsolutePath(),
                // 设置为横向编排文字
                BaseFont.IDENTITY_H,
                // 不使用内嵌的字体，使用外嵌字体
                BaseFont.NOT_EMBEDDED);
        // 定义为普通文字大小
        Font font = new Font(baseFont, 20, Font.NORMAL);
        // 在pdf文档上绘制文本信息
        String[] titles = {"沐言科技", "李兴华编程训练营", "人人分享公益直播"};
        String[] contents = {"www.yootk.com", "edu.yootk.com", "share.yootk.com"};
        for (int i = 0; i < titles.length; i++) {
            // 定义表格行
            PdfPTable table = new PdfPTable(2);
            // 定义表格列, title
            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new Paragraph(titles[i], font));
            table.addCell(cell);
            // 定义表格列, content
            cell = new PdfPCell();
            cell.setPhrase(new Paragraph(contents[i]));
            table.addCell(cell);
            /*
             * 表格加入到文档之中
             * title1, content1
             * title2, content2
             * title3, content3
             */
            document.add(table);
        }
        document.close();
    }
}
