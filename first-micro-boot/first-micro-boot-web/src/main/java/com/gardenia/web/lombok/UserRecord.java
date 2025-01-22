package com.gardenia.web.lombok;

import java.util.Optional;

/**
 * record类型
 * <p>
 *     与 Lombok 的异同：
 *          Lombok 是通过插件和预编译来实现的，不是语言级别
 *          Record 是语言级别的 Lombok，可以使用 Record 代替 Lombok 简化样例代码的编写。
 * <p>
 *     Record 特性
 *     1. 带有全部参数的构造方法
 *     2. public 访问器：属性的访问是通过公共方法
 *     3. 在编译 Record 过程中，会生成 toString(), hashCode(), equals()等方法
 *     4. 没有遵循 Bean 的命名规则，无 setter | getter 方法
 *     5. 类以及所有的属性都是final修饰的，Record不能被继承，Record为隐式的final类。除此以外，与普通类相同。
 *     6. final属性，只读，不能修改
 *     7. 不能声明实例属性，能声明static静态成员
 * <a href="https://blog.csdn.net/weixin_53676834/article/details/135777438" />
 * @since JDK 14
 * @author caimeng
 * @date 2025/1/21 17:53
 */
public record UserRecord(String name, Integer age, String email) {
    /*
     * 1. 小括号中的就是他的构造方法；
     * 2. 使用 record 关键字表示他是一个 record 类型；
     * 3. 不需要做其他操作，record 类型就创建好了；
     * 4. 包含两个属性: name 和 age。
     */

    /**
     * 紧凑型构造器 （可选方法）
     * <p>
     *     a. 注意：实际调用的是 record 类的全参构造方法（默认构造器为全参）
     *     类型于:
     *          <code>
     *              public record UserRecord(String name, Integer age, String email) {
     *                  public UserRecord(String name, Integer age, String email){
     *                      // 执行 "紧凑型构造器" 中的方法
     *                  }
     *              }
     *          </code>
     *     b. 部分参数的构造器 {@link UserRecord#UserRecord(String, Integer)}
     * <p>
     *     没有无参构造器，
     *     无参的 record 也没有意义。
     * @param name 姓名
     * @param age 年龄
     * @param email 邮箱
     */
    public UserRecord {
        // 可以写一些业务逻辑
        System.out.println("调用了 UserRecord 的全参构造器");
    }

    /**
     * 自定义构造器
     * <p>
     *     默认为全参构造器
     */
    public UserRecord(String name, Integer age) {
        // 会通过全参构造器调用到 “紧凑型构造器”
        this(name, age, null);
    }

    /**
     * 实例方法
     * @return 连接属性
     */
    public String contact() {
        return String.format("姓名: %s, 年龄:%d", name, age);
    }

    public static String emailUpperCase(String email) {
        // 不能调用属性中的 email 值
        return Optional.ofNullable(email).orElse("no email").toUpperCase();
    }
}
