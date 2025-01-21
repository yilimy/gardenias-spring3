/**
 * 热更新 | 热部署
 * <p>
 *     1. 引入依赖包
 *          org.springframework.boot:spring-boot-devtools
 *     2. 配置
 *          > Eclipse, STS工具，只需要引入上面的依赖即可
 *          > IDEA,
 *          >   a.   File -> Settings -> Build -> Compiler -> Build project automatically (勾选)
 *              b.   "Ctrl + Shift + Alt + /"调出 Maintenance 对话框，选择第一项，注册 (Registry)
 *                   确认 "compiler.automake.allow.when.app.running"勾选
 *                   (在新版本中没有找到该选项)
 *                   在IDEA 2021.2 或更高版本，从"Advanced Settings"找
 *              c.   确保没有禁用devtools,比如 spring.devtools.restart.enabled=false
 *              d.   启动项配置中 "On ‘Update’ action"和"On frame deactivation"两项选择"Update classes and resources"
 *              e.   使用 debug 启动更佳
 *              f.   重启 IDEA
 * <p>
 *     在每次代码修改并保存后，都会自动重新启动SpringBoot容器，实际上这个时候的启动并不是整个容器的启动，而是内部容器的启动。
 *     所谓的热部署，本质上是将整个类加载器进行拆分，
 *          > 在没有引入"devtools"工具时，所有的系统类和应用程序类都使用一个类加载器，
 *          > 但是当引入"devtools"之后，系统类有系统类的加载器，而应用程序类有应用程序类的加载器。
 *     热部署实际上是应用程序类的重新加载，要比整个项目重启启动要快一些。
 * <p>
 *     一般在上线的时候，将"devtools"依赖删除
 * @author caimeng
 * @date 2025/1/21 10:39
 */
package com.gardenia.web.hotfix;