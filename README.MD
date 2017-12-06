**分布式学习**

目前有2个模块

**learn-distribute-reg 分布式注册中心模块**


**learn-distribute-reg-base**

注册中心通用接口

**learn-distribute-reg-zookeeper** 

为注册中心的zookeeper实现，才用curator框架


**learn-distribute-disconf 分布式配置中心模块**


**learn-distribute-disconf-conf** 

应用配置文件

路径结构为：/disconf/应用名/该应用下的所有属性文件


**learn-distribute-disconf-server** 

分布式配置的服务端

负责将应用配置的下的所有属性文件注册到注册中心，并且监听应用配置文件的变化，从而修改注册中心对应的属性值


**learn-distribute-disconf-client** 

分布式配置客户端

负责从注册中心获取对应的应用，同时监听应用的属性变化。