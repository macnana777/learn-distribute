package info.macnana.disconf.register;

import info.macnana.disconf.model.PropertyConfig;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;

/**
 * author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 14:35
 * Description: 注册属性到注册中心
 * Copyright(©) 2017 by zhengheng.
 */
public interface RegisterProsConfig {
    /**
     * 初始化配置到注册中心
     */
    void scanPros();
    /**
     * 将属性文件内容注册中心
     * @param propertyConfig
     */
    void registry(PropertyConfig propertyConfig);
    /**
     * 注册节点监听
     * @param treeCacheListeners
     */
    void registryListenable(TreeCacheListener treeCacheListeners);
}
