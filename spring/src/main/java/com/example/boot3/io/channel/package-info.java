/**
 * <a href="https://www.bilibili.com/video/BV1fY411J78M?p=14">Channels工具</a>
 * <p>
 *     java.io包是传统的IO编程工具类，同时在JDK1.4以前属于主力，
 *     但是在JDK1.7以后，java.io包开始被废弃，提出了NIO包。
 *     NIO包主打的是性能，但是替换的时候出现了一些问题，程序员习惯了InputStream和OutputStream，
 *     为此，引入了 {@link java.nio.channels.Channels}作为桥梁。
 * <p>
 *     在调用 {@link java.nio.channels.Channels#newChannel(OutputStream)} 的时候，
 *     发现就是按照传统的OutputStream进行IO的读取，但是只有文件流是开启了Channel的，所以文件流的读取速率快。
 * @author caimeng
 * @date 2025/2/28 18:05
 */
package com.example.boot3.io.channel;

import java.io.OutputStream;