package info.macnana.disconf;

import info.macnana.disconf.listener.register.ListenerRegister;
import info.macnana.disconf.listener.register.impl.FileListenerRegister;
import info.macnana.disconf.listener.strategy.impl.FileListenerStrategy;
import info.macnana.disconf.register.RegisterProsConfig;
import info.macnana.disconf.register.impl.LocalServerRegisterPropertyConfig;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import info.macnana.reg.zookeeper.ZookeeperRegistryCenter;
import info.macnana.reg.zookeeper.config.ZookeeperConfiguration;

import java.io.IOException;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 17:20
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
public class ServerMain {

    /**
     *  zk远程主机，部署在云主机上
     */
    private static final String serverHosts = "116.196.87.216:2181,116.196.87.216:2182,116.196.87.216:2183";

    /**
     * 扫描文件，路径为工程的learn-distribute-disconf-conf的模块
     */
    private static final String scanPath = "C:\\WorkSpace\\learn\\learn-distribute\\learn-distribute-disconf\\learn-distribute-disconf-conf\\disconf";

    public static void main(String[] args) throws IOException {
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(serverHosts,"demo");
        CoordinatorRegistryCenter coordinatorRegistryCenter = new ZookeeperRegistryCenter(configuration);
        coordinatorRegistryCenter.init();
        RegisterProsConfig registerPropertyConfig = new LocalServerRegisterPropertyConfig(coordinatorRegistryCenter,scanPath);
        registerPropertyConfig.scanPros();
        System.in.read();
    }
}
