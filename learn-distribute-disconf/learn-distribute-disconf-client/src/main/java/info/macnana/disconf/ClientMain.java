package info.macnana.disconf;

import info.macnana.disconf.discover.DiscoverProsConfig;
import info.macnana.disconf.discover.impl.DiscoverProsConfigImpl;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import info.macnana.reg.zookeeper.ZookeeperRegistryCenter;
import info.macnana.reg.zookeeper.config.ZookeeperConfiguration;

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
public class ClientMain {

    public static void main(String[] args) {
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(
                "116.196.87.216:2181,116.196.87.216:2182,116.196.87.216:2183","demo"
        );
        CoordinatorRegistryCenter coordinatorRegistryCenter = new ZookeeperRegistryCenter(configuration);
        coordinatorRegistryCenter.init();
        DiscoverProsConfig discover = new DiscoverProsConfigImpl(coordinatorRegistryCenter);
        Map<String,Map<String,String>> prosMap = discover.getAppProsConfig("app1");
        System.out.println(prosMap);
    }
}
