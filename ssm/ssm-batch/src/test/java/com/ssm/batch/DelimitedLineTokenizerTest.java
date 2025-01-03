package com.ssm.batch;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;

/**
 * 测试：拆分器
 * <p>
 *     SpringBatch可以提供完整的容错支持，
 *     如果在数据本身有什么错误，不会导致程序产生异常。
 * @author caimeng
 * @date 2025/1/3 16:11
 */
@Slf4j
@ContextConfiguration(classes = StartSpringBatchApplication.class)
@ExtendWith(SpringExtension.class)
public class DelimitedLineTokenizerTest {
    @Autowired
    private DelimitedLineTokenizer delimitedLineTokenizer;

    @Test
    public void splitTest() {
        String lineData = "9197276813101,李兴华,9820.22,2025-10-11 11:21:21,洛阳支行";
        // 数据拆分
        FieldSet fieldSet = delimitedLineTokenizer.tokenize(lineData);
        String format = MessageFormat.format(
                "【交易信息】账户ID: {0}, 姓名: {1}, 金额: {2}, 交易时间: {3}, 交易位置: {4}",
                fieldSet.readLong(0),
                fieldSet.readString(1),
                fieldSet.readDouble(2),
                // 返回类型是Date，实际是Date的toString方法。所以pattern不生效
                fieldSet.readDate(3, "yyyy-MM-dd HH:mm:ss"),
                fieldSet.readString(4));
        // 【交易信息】账户ID: 9,197,276,813,101, 姓名: 李兴华, 金额: 9,820.22, 交易时间: 2025/10/11 上午12:00, 交易位置: 洛阳支行
        System.out.println(format);
    }

    /**
     * 测试：数据容错
     * <p>
     *     如果发现某些数据上出现了一些小小的偏差，SpringBatch内部会自动的对其进行完整的纠正。
     *     从整体的设计来讲，可以减少数据转换逻辑上的处理。
     */
    @Test
    public void errorDataTest() {
        String lineData = "9197276813101xxx,李兴华,9820.22a,20252-10-11 11:21:21,洛阳支行";
        // 数据拆分
        FieldSet fieldSet = delimitedLineTokenizer.tokenize(lineData);
        String format = MessageFormat.format(
                "【交易信息】账户ID: {0}, 姓名: {1}, 金额: {2}, 交易时间: {3}, 交易位置: {4}",
                fieldSet.readLong(0),
                fieldSet.readString(1),
                fieldSet.readDouble(2),
                fieldSet.readDate(3),
                fieldSet.readString(4));
        /*
         * 【交易信息】账户ID: 9,197,276,813,101, 姓名: 李兴华, 金额: 9,820.22, 交易时间: 20252/10/11 上午12:00, 交易位置: 洛阳支行
         * 修正的部分:
         *      9197276813101xxx    => 9,197,276,813,101
         *      9820.22a            => 9,820.22
         * 时间没有修复
         * 修正不是万能的，如果将第一项（9197276813101xxx）改成"xxx"，则不会识别（指报错）
         */
        System.out.println(format);
    }
}
