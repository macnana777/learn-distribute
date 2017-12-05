package info.macnana.disconf.discover.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import info.macnana.disconf.discover.DiscoverProsConfig;
import info.macnana.disconf.model.PropertyConfig;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 18:56
 * Description: 获取应用属性
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
@RequiredArgsConstructor
public class DiscoverProsConfigImpl implements DiscoverProsConfig{

    private final CoordinatorRegistryCenter coordinatorRegistryCenter;

    @Override
    public Map<String,Map<String,String>> getAppProsConfig(String appName) {
        String path = Joiner.on("/").join(Arrays.asList(PropertyConfig.ROOT_PATH,appName));
        List<String> keys = coordinatorRegistryCenter.getChildrenKeys(path);
        if(CollectionUtils.isNotEmpty(keys)){
            coordinatorRegistryCenter.addCacheData(path);
            Map<String,Map<String,String>> appProsMap = Maps.newHashMap();
            for (String key: keys) {
                path = Joiner.on("/").join(Arrays.asList(path,key));
                String value = coordinatorRegistryCenter.get(path);
                if(!Strings.isNullOrEmpty(value)){
                    Map<String,String> prosMap = JSON.parseObject(value,new TypeReference<Map<String,String>>(){});
                    appProsMap.put(key,prosMap);
                }
            }
            return appProsMap;
        }
        return null;
    }
}
