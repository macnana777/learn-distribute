package info.macnana.disconf.register;

import info.macnana.disconf.register.impl.LocalServerRegisterPropertyConfig;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import info.macnana.reg.zookeeper.ZookeeperRegistryCenter;
import info.macnana.reg.zookeeper.config.ZookeeperConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 15:51
 * Description: 扫描本地server的属性文件，将属性注册到注册中心中
 * Copyright(©) 2017 by zhengheng.
 */
public class RegisterPropertyConfigTest {

    CoordinatorRegistryCenter coordinatorRegistryCenter;

    private RegisterProsConfig registerPropertyConfig;

    private String path ;

    @Before
    public void init(){
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(
            "116.196.87.216:2181,116.196.87.216:2182,116.196.87.216:2183","demo"
        );
        coordinatorRegistryCenter = new ZookeeperRegistryCenter(configuration);
        coordinatorRegistryCenter.init();

        path = "C:\\WorkSpace\\learn\\learn-distribute\\learn-distribute-disconf\\learn-distribute-disconf-conf\\disconf";
    }

    @Test
    public void localServerRegisterTest(){
        registerPropertyConfig = new LocalServerRegisterPropertyConfig(coordinatorRegistryCenter,path);
        registerPropertyConfig.scanPros();
    }


}
