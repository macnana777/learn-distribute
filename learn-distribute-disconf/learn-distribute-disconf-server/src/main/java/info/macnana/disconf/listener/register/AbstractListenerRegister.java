package info.macnana.disconf.listener.register;

import com.google.common.collect.Maps;
import info.macnana.disconf.listener.strategy.ListenerStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 23:19
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
public abstract class AbstractListenerRegister implements ListenerRegister{

    protected static Map<String,FileAlterationMonitor> monitorCache = Maps.newHashMap();

    protected static int DEFAULT_INTERVAL = 5;

    @Override
    public void close(String path) {
        FileAlterationMonitor monitor = monitorCache.remove(path);
        if(monitor != null){
            try {
                monitor.stop();
            } catch (Exception e) {
                log.error("停止监听出错", e);
            }
        }
    }


}
