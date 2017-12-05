package info.macnana.disconf.listener.register;

import info.macnana.disconf.listener.strategy.ListenerStrategy;

/**
 * author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 22:23
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
public interface ListenerRegister {

    void register(String path);

    void close(String path);

}
