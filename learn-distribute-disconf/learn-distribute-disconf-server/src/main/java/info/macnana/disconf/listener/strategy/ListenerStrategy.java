package info.macnana.disconf.listener.strategy;

import java.io.File;

/**
 * author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 21:24
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
public interface ListenerStrategy {

    /**
     * 初始化监听
     */
    void start();

    /**
     * 创建file
     */
    void create(File file);

    /**
     * 改变
     */
    void change(File file);

    /**
     * 删除
     */
    void delete(File file);

    /**
     * 结束监听
     */
    void end();


}
