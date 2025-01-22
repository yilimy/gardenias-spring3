/**
 * 如果不想用 lombok 插件，可以使用 record 类
 * {@link com.gardenia.web.lombok.UserRecord}
 * <p>
 *     在gradle中导入lombok
 *     1. 编译时生效
 *          compileOnly(libraries.'lombok')
 *     2. 注解生效
 *          annotationProcessor(libraries.'lombok')
 *     3. IDEA 安装lombok插件
 *     4. 重启 IDEA
 *     5. 注解配置生效 settings -> Compiler -> Annotation Processors --> 勾选Default的"Enable annotation processing"
 * <p>
 *     在maven中不需要显式的指定 annotationProcessor 的原因：
 *     1. 当在 dependencies 中添加 Lombok 依赖时，Maven 会自动将 Lombok 的注解处理器包含在编译类路径中。如果 Lombok 的版本较新（例如 1.18.16 之后），它会自动注册为注解处理器，而无需显式配置。
 *     2. 如果使用的是 IntelliJ IDEA，IDE 会自动检测项目中的注解处理器依赖，并将其加入注解处理器路径。这意味着即使没有在 pom.xml 中显式配置 &lt;annotationProcessorPaths&gt;，IDE 也会自动加载 Lombok 的注解处理器。
 *     3. Lombok 的注解处理器实现了 javax.annotation.processing.Processor 接口，并通过 META-INF/services 文件自动注册。因此，Maven 在编译时会自动发现并使用 Lombok 的注解处理器，而无需额外配置。
 *     4. 当项目中同时使用多个注解处理器（如 Lombok 和 MapStruct）时，可能会出现加载顺序问题。显式配置 &lt;annotationProcessorPaths&gt; 可以明确指定注解处理器的加载顺序。
 * ``` xml
 *     <plugin>
 *         <groupId>org.apache.maven.plugins</groupId>
 *         <artifactId>maven-compiler-plugin</artifactId>
 *         <version>3.8.1</version>
 *         <configuration>
 *             <source>8</source>
 *             <target>8</target>
 *             <annotationProcessorPaths>
 *                 <path>
 *                     <groupId>org.projectlombok</groupId>
 *                     <artifactId>lombok</artifactId>
 *                     <version>1.18.12</version>
 *                 </path>
 *                 <path>
 *                     <groupId>org.mapstruct</groupId>
 *                     <artifactId>mapstruct-processor</artifactId>
 *                     <version>1.5.3.Final</version>
 *                 </path>
 *             </annotationProcessorPaths>
 *         </configuration>
 *     </plugin>
 * ```
 * <p>
 *     lombok访问器 accessor
 *     考虑到涉及模式的不同需要，访问器有三种模式: fluent | chain | prefix
 * <p>
 *     异常处理 {@link com.gardenia.web.lombok.MessageHandler#print(String)}
 * @author caimeng
 * @date 2025/1/21 17:52
 */
package com.gardenia.web.lombok;