package info.macnana.disconf.process;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-06 9:05
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
public interface ProsProcess {
    /**
     * 初始化配置
     * @param childPath 子路径
     * @param data 变更数据
     */
    void init(String childPath,String data);
    /**
     * 添加节点属性
     * @param childPath 子路径
     * @param data 变更数据
     */
    void add(String childPath,String data);
    /**
     * 更新节点属性
     * @param childPath 子路径
     * @param data 变更数据
     */
    void update(String childPath,String data);
    /**
     * 节点删除
     * @param childPath 子路径
     * @param data 变更数据
     */
    void delete(String childPath,String data);
}
