**learn-distribute-disconf 分布式配置中心模块**

模拟disconf实现

**learn-distribute-disconf-conf** 

应用配置文件

路径结构为：/disconf/应用名/该应用下的所有属性文件

**learn-distribute-disconf-server** 

分布式配置的服务端

负责将应用配置的下的所有属性文件注册到注册中心，并且监听应用配置文件的变化，从而修改注册中心对应的属性值

启动learn-distribute-disconf-server模块中的
serverMain主入口，实现对learn-distribute-disconf-conf模块的监听

**learn-distribute-disconf-client** 

分布式配置客户端

启动learn-distribute-disconf-client模块中的
ClientMain主入口，实现从注册中心获取对应应用的配置，同时开启对该应用下所有属性监听，当服务端的属性文件发生变化时候，会通知对应的应用发生变更的属性