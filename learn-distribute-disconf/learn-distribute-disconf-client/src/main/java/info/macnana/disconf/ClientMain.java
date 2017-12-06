package info.macnana.disconf;

import info.macnana.disconf.discover.DiscoverProsConfig;
import info.macnana.disconf.discover.impl.DiscoverProsConfigImpl;
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

    public static void main(String[] args) throws IOException {
        ZookeeperConfiguration configuration = new ZookeeperConfiguration(serverHosts,"demo");
        CoordinatorRegistryCenter coordinatorRegistryCenter = new ZookeeperRegistryCenter(configuration);
        coordinatorRegistryCenter.init();
        DiscoverProsConfig discover = new DiscoverProsConfigImpl(coordinatorRegistryCenter,new ProsProcessImpl());
        String app1 = "app1";
        Map<String,Map<String,String>> app1ProsMap = discover.getAppProsConfig(app1);
        log.info("应用{} , 当前获取属性 :{}",app1,app1ProsMap);
        String app2 = "app2";
        Map<String,Map<String,String>> app2ProsMap = discover.getAppProsConfig(app2);
        log.info("应用{} , 当前获取属性 :{}",app2,app2ProsMap);
        System.in.read();
    }
}
