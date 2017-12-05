package info.macnana.disconf.discover;

import info.macnana.disconf.model.PropertyConfig;

import java.util.Map;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 18:52
 * Description: 获取注册属性
 * Copyright(©) 2017 by zhengheng.
 */
public interface DiscoverProsConfig {

    /**
     * 获取各应用配置
     * @return
     */
    Map<String,Map<String,String>> getAppProsConfig(String appName);



}
