/**
 * 自定义banner
 * <p>
 *     1. 通过在线banner生成器，生成banner文件
 *          <a href="https://www.bootschool.net/ascii" />
 *          <a href="https://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20" />
 *     2. 在配置文件中指定banner地址
 *          spring.banner.location=banner_gardenia_3d_ascii.txt
 *          如果不使用 spring.banner.location 进行指定，则需要将banner文件命名为 “banner.txt”并放到 resource 的根目录下
 * <p>
 *     基于bean的方式配置banner
 *     1. 实现Banner接口
 *          {@link org.springframework.boot.Banner}
 *          准备三个banner，每次随机选择一项
 *          {@link com.gardenia.web.banner.GardeniaBanner}
 *     2. 修改启动类
 * @author caimeng
 * @date 2025/1/20 14:55
 */
package com.gardenia.web.banner;