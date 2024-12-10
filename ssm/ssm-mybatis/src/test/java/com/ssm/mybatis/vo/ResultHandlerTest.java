package com.ssm.mybatis.vo;

import com.ssm.mybatis.util.MybatisSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultHandler;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caimeng
 * @date 2024/12/10 14:41
 */
@Slf4j
public class ResultHandlerTest {
    /**
     * 测试：使用 ResultHandler 来接收数据
     * <p>
     *     在进行一些查询操作的时候，使用Map集合进行最终结果的保存是一项非常常常见的功能，
     *     所以在MyBatis内部是考虑到这种转换的需要，才提供有 ResultHandler 接口支持。
     *     <a href="https://www.bilibili.com/video/BV1CM1qYwEx1/">
     */
    @Test
    public void resultTest() {
        Map<Long, String> bookMap = new HashMap<>();
        MybatisSessionFactory.getSqlSession().select(
                BookTest.NAME_SPACE_BOOK + ".findAll",
                // 使用 ResultHandler 来处理查询结果，而不是通过接收返回值的形式接受结果。
                (ResultHandler<Book>) resultContext -> {
                    Book book = resultContext.getResultObject();
                    // 相当于把结果为list的集合转换成Map集合
                    bookMap.put(book.getBid(), "【" + book.getAuthor() +"】" + book.getTitle());
                });
        bookMap.forEach((k, v) -> System.out.println(k + "=" + v));
    }
}
