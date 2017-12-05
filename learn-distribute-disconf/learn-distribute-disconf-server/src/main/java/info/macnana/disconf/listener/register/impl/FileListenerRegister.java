package info.macnana.disconf.listener.register.impl;

import com.google.common.collect.Maps;
import info.macnana.disconf.listener.register.AbstractListenerRegister;
import info.macnana.disconf.listener.register.ListenerRegister;
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
 * Date: 2017-12-04 22:50
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
@RequiredArgsConstructor
public class FileListenerRegister extends AbstractListenerRegister {

    private final ListenerStrategy listenerStrategy;

    private final Integer interval;

    @Override
    public void register(String path) {
        File directory = new File(path);
        // 默认轮询间隔 1 秒
        int val = interval == null ? DEFAULT_INTERVAL : interval;
        // 创建一个文件观察器用于处理文件的格式
        FileAlterationObserver observer = new FileAlterationObserver(directory, FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),FileFilterUtils.suffixFileFilter(".properties")));
        observer.addListener(new FileAlterationListener(){
            @Override
            public void onStart(FileAlterationObserver fileAlterationObserver) {
                listenerStrategy.start();
            }
            @Override
            public void onDirectoryCreate(File file) {
               return;
            }
            @Override
            public void onDirectoryChange(File file) {
                return;
            }
            @Override
            public void onDirectoryDelete(File file) {
                return;
            }
            @Override
            public void onFileCreate(File file) {
                listenerStrategy.create(file);
            }
            @Override
            public void onFileChange(File file) {
                listenerStrategy.change(file);
            }
            @Override
            public void onFileDelete(File file) {
                listenerStrategy.delete(file);
            }
            @Override
            public void onStop(FileAlterationObserver fileAlterationObserver) {
                listenerStrategy.end();
            }
        });
        FileAlterationMonitor monitor = new FileAlterationMonitor(TimeUnit.SECONDS.toMillis(val),observer);
        monitorCache.put(path,monitor);
        try {
            monitor.start();
        } catch (Exception e) {
            log.error("创建文件夹监听出错", e);
        }

    }
}
