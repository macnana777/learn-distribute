package info.macnana.disconf;

import info.macnana.disconf.discover.DiscoverProsConfig;
import info.macnana.disconf.discover.impl.DiscoverProsConfigImpl;
import info.macnana.disconf.process.ProsProcess;
import info.macnana.disconf.process.impl.ProsProcessImpl;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import info.macnana.reg.zookeeper.ZookeeperRegistryCenter;
import info.macnana.reg.zookeeper.config.ZookeeperConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-05 14:19
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
public class ClientMain {

    private static final String serverHosts = "116.196.87.216:2181,116.196.87.216:2182,116.196.87.216:2183";

    private static final String appName = "app1";

    public static void main(String[] args) throws IOException {
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(serverHosts,"demo");
        CoordinatorRegistryCenter coordinatorRegistryCenter = new ZookeeperRegistryCenter(configuration);
        coordinatorRegistryCenter.init();
        DiscoverProsConfig discover = new DiscoverProsConfigImpl(coordinatorRegistryCenter,new ProsProcessImpl());
        Map<String,Map<String,String>> prosMap = discover.getAppProsConfig(appName);
        log.info("应用{} , 当前获取属性 :{}",appName,prosMap);
        System.in.read();
    }
}
