/**
 * AIO服务端的所有异步操作，几乎全部是基于回调完成的，
 * 客户端的请求，也是基于类似的模式。
 * AIO编写完成的服务端，暂时无法使用 telnet 命令进行测试。因为里面的回调操作很多。
 * @author caimeng
 * @date 2025/2/28 16:50
 */
package com.example.boot3.io.aio.server;