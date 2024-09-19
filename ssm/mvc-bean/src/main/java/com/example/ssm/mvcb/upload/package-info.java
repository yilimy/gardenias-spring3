/**
 * SpringMVC对文件上传的支持
 * <a href="https://www.bilibili.com/video/BV1i3H5ejEXu/" />
 * <p>
 *     1. 用户请求： 普通参数 + 上传文件
 *     2. Action控制器
 *     3. MultipartFile
 *     4. 配置：
 *          - 存储路径
 *          - 文件大小限制
 *          - 请求限制
 *          - 存储阈值 (达到多少值之后，向磁盘写入)
 * <p>
 *     在配置类中覆写上传文件的方法
 *     {@link com.example.ssm.mvcb.web.config.StartWebAnnotationApplication#customizeRegistration(ServletRegistration.Dynamic)}
 * @author caimeng
 * @date 2024/9/18 10:58
 */
package com.example.ssm.mvcb.upload;

import jakarta.servlet.ServletRegistration;