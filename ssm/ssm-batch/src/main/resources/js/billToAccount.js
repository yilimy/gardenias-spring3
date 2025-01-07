var account = new com.ssm.batch.vo.Account(); // 对象实例化
account.id = item.getId();  // 属性赋值
account.amount = item.getAmount() * 1   // 数据乘1，会返回本身的数值，而非字符串
account;