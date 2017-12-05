package info.macnana.reg.zookeeper;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import info.macnana.reg.exception.RegException;
import info.macnana.reg.exception.RegExceptionHandler;
import info.macnana.reg.zookeeper.config.ZookeeperConfiguration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 10:15
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
@RequiredArgsConstructor
public class ZookeeperRegistryCenter implements CoordinatorRegistryCenter {

    private final ZookeeperConfiguration configuration;

    /**
     * 用来存储监听的节点
     */
    private Map<String,TreeCache> treeCacheMap = Maps.newHashMap();

    @Getter
    private CuratorFramework client;

    @Override
    public void init() {
        log.info("zookeeper注册服务启动，开始连接zk主机，主机列表 : {} " , configuration.getServerLists());
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                //连接主机
                .connectString(configuration.getServerLists())
                //设置重连策略
                .retryPolicy(new ExponentialBackoffRetry(configuration.getBaseSleepTimeMilliseconds(),configuration.getMaxRetries(),configuration.getMaxSleepTimeMilliseconds()))
                .namespace(configuration.getNamespace());
        if( 0 != configuration.getSessionTimeoutMilliseconds()){
            builder.sessionTimeoutMs(configuration.getSessionTimeoutMilliseconds());
        }
        if( 0 != configuration.getConnectionTimeoutMilliseconds()){
            builder.connectionTimeoutMs(configuration.getConnectionTimeoutMilliseconds());
        }
        if(StringUtils.isNotBlank(configuration.getDigest())){
            builder.authorization("digest", configuration.getDigest().getBytes(Charsets.UTF_8))
                    .aclProvider(new ACLProvider() {
                        @Override
                        public List<ACL> getDefaultAcl() {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }

                        @Override
                        public List<ACL> getAclForPath(final String path) {
                            return ZooDefs.Ids.CREATOR_ALL_ACL;
                        }
                    });
        }
        client = builder.build();
        client.start();
        try {
            client.blockUntilConnected();
        } catch (InterruptedException e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public void close() {
        for (Map.Entry<String, TreeCache> each : treeCacheMap.entrySet()) {
            each.getValue().close();
        }
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            RegExceptionHandler.handleException(e);
        }
        CloseableUtils.closeQuietly(client);
    }

    @Override
    public String get(String key) {
        TreeCache cache = findTreeCache(key);
        if(cache != null){
            ChildData resultInCache = cache.getCurrentData(key);
            if (null != resultInCache) {
                return resultInCache.getData() != null ? new String(resultInCache.getData(), Charsets.UTF_8) : null;
            }
        }
        return getDirectly(key);
    }

    private TreeCache findTreeCache(final String key) {
        for (Map.Entry<String, TreeCache> entry : treeCacheMap.entrySet()) {
            if (key.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean isExisted(String key) {
        try {
            return client.checkExists().forPath(key) !=null;
        } catch (Exception e) {
            log.error("节点判断存在异常" , e);
            return false;
        }
    }

    @Override
    public void persist(String key, String value) {
        if(!isExisted(key)){
            try {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(key,value.getBytes(Charsets.UTF_8));
            } catch (Exception e) {
                RegExceptionHandler.handleException(e);
            }
        }
        else{
            update(key,value);
        }
    }

    @Override
    public void update(String key, String value) {
        try {
            client.inTransaction().check().forPath(key).and().setData().forPath(key, value.getBytes(Charsets.UTF_8)).and().commit();
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public void remove(String key) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(key);
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public Object getRawClient() {
        return client;
    }

    @Override
    public String getDirectly(String key) {
        try {
            return new String(client.getData().forPath(key),Charsets.UTF_8);
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
            return null;
        }
    }

    @Override
    public List<String> getChildrenKeys(String key) {
        try {
            List<String> result = client.getChildren().forPath(key);
            return result;
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
            return Collections.emptyList();
        }


    }

    @Override
    public int getNumChildren(String key) {
        int num = 0;
        try {
            Stat stat = client.checkExists().forPath(key);
            num = stat.getNumChildren();
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
       return num;
    }

    @Override
    public void persistEphemeral(String key, String value) {
        try {
            if(isExisted(key)){
                remove(key);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key,value.getBytes(Charsets.UTF_8));
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public String persistSequential(String key, String value) {
        try {
            if(isExisted(key)){
                remove(key);
            }
            return client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(key,value.getBytes(Charsets.UTF_8));
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
        return null;
    }

    @Override
    public void persistEphemeralSequential(String key, String value) {
        try {
            if(isExisted(key)){
                remove(key);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(key,value.getBytes(Charsets.UTF_8));
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
    }

    @Override
    public void addCacheData(String cachePath) {
        TreeCache cache = new TreeCache(client, cachePath);
        try {
            cache.start();
        } catch (Exception e) {
            RegExceptionHandler.handleException(e);
        }
        treeCacheMap.put(cachePath , cache);
    }

    @Override
    public void evictCacheData(String cachePath) {
        TreeCache cache = treeCacheMap.remove(cachePath);
        if(cache !=null){
            cache.close();
        }
    }

    @Override
    public Object getRawCache(String cachePath) {
        return treeCacheMap.get(cachePath);
    }
}
