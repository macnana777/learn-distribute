package info.macnana.disconf.listener.strategy.impl;

import info.macnana.disconf.listener.strategy.ListenerStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 22:05
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
@RequiredArgsConstructor
public class FileListenerStrategy implements ListenerStrategy {

    @Override
    public void start() {
        log.info("开启文件监听");
    }

    @Override
    public void create(File file) {
        log.info("monitor start scan files..");
    }

    @Override
    public void change(File file) {
        log.info(file.getName()+" file changed.");
    }

    @Override
    public void delete(File file) {
        log.info(file.getName()+" file deleted.");
    }

    @Override
    public void end() {
        log.info("结束文件监听");
    }
}
