#### Dubbo
[dubbo技术文档](https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/)

步骤：
1. 将服务提供者注册到注册中心
   > 导入dubbo依赖
   > 配置服务提供者
2. 让服务消费者去注册中心订阅服务提供者的服务地址  

启动环境
启动zookeeper客户端和服务端
```cmd
D:\apache\apache-zookeeper-3.6.4-bin\bin\zkServer.cmd
D:\apache\apache-zookeeper-3.6.4-bin\bin\zkCli.cmd
```
启动dubbo管理中心（已编译）
jar包地址：D:\apache\dubbo-admin\jar\dubbo-admin-0.7.0-SNAPSHOT.jar
```cmd
java -jar dubbo-admin-0.7.0-SNAPSHOT.jar
```
登录地址：http://localhost:38080
root / root