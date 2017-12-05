package info.macnana.disconf.register;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import info.macnana.disconf.model.PropertyConfig;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 19:07
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRegisterProsConfig implements RegisterProsConfig{

    protected final CoordinatorRegistryCenter coordinatorRegistryCenter;

    @Override
    public void registry(PropertyConfig propertyConfig) {
        Preconditions.checkNotNull(propertyConfig,"应用配置为空");
        String appName = propertyConfig.getAppName();
        Preconditions.checkArgument(!Strings.isNullOrEmpty(appName),"应用名为空");
        String key = propertyConfig.getKey();
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key),"应用名 : %s,配置文件为空" , appName);
        log.info("开始注册配置到注册中心，应用名:{} ,属性文件:{} ", appName , key);
        Map<String,String> prosMap = propertyConfig.getProsMap();
        if(MapUtils.isNotEmpty(prosMap)){
            coordinatorRegistryCenter.addCacheData(PropertyConfig.ROOT_PATH);
            String path = Joiner.on("/").join(Arrays.asList(PropertyConfig.ROOT_PATH,appName,key));
            String content = JSON.toJSONString(prosMap);
            coordinatorRegistryCenter.persist(path,content);
        }
    }

    @Override
    public void registryListenable(TreeCacheListener treeCacheListener) {
        TreeCache cache = (TreeCache) coordinatorRegistryCenter.getRawCache(PropertyConfig.ROOT_PATH);
        cache.getListenable().addListener(treeCacheListener);
    }


}
