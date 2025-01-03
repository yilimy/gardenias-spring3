package com.ssm.batch.mapper;

import com.ssm.batch.vo.Bill;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNull;

/**
 * @author caimeng
 * @date 2025/1/3 17:21
 */
public class BillMapper implements FieldSetMapper<Bill> {

    @SuppressWarnings("NullableProblems")
    @Override
    public Bill mapFieldSet(@NonNull FieldSet fieldSet) {
        // 需要将传入的FieldSet接口对象实例，通过手工的方式转为Bill对象
        // 如果代码功底强，可以使用一个自定义的反射处理类进行对象实例化
        Bill bill = new Bill();
        bill.setId(fieldSet.readLong(0));
        bill.setName(fieldSet.readString(1));
        bill.setAmount(fieldSet.readDouble(2));
        bill.setTransaction(fieldSet.readDate(3, "yyyy-MM-dd HH:mm:ss"));
        bill.setLocation(fieldSet.readString(4));
        // 由于FieldSet本身有良好的容错处理，此时可以准确的获取所需要的数据对象。
        return bill;
    }
}
