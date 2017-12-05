package info.macnana.disconf.storage;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.sun.istack.internal.NotNull;
import info.macnana.disconf.model.PropertyConfig;
import info.macnana.disconf.process.ProsProcess;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

import java.util.Arrays;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-05 15:30
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@RequiredArgsConstructor
@Slf4j
public class AppNodeStorage {

    private final CoordinatorRegistryCenter coordinatorRegistryCenter;

    @NonNull
    private final String appName;

    @NotNull
    private final ProsProcess process;

    public void addDataProcess(){
        String path = Joiner.on("/").join(Arrays.asList(PropertyConfig.ROOT_PATH,appName));
        coordinatorRegistryCenter.addCacheData(path);
        TreeCache treeCache = (TreeCache)coordinatorRegistryCenter.getRawCache(path);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                ChildData childData = treeCacheEvent.getData();
                if(childData == null){
                    return;
                }
                String path = childData.getPath();
                String data =  childData.getData() == null ? "" : new String(childData.getData(), Charsets.UTF_8);
                switch (treeCacheEvent.getType()){
                    case INITIALIZED:
                        process.init(path,data);
                        break;
                    case NODE_ADDED:
                        process.add(path,data);
                        break;
                    case NODE_UPDATED:
                        process.update(path,data);
                        break;
                    case NODE_REMOVED:
                        process.delete(path,data);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
