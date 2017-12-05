package info.macnana.disconf.listener.strategy.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import info.macnana.disconf.listener.strategy.ListenerStrategy;
import info.macnana.disconf.model.PropertyConfig;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    private final CoordinatorRegistryCenter coordinatorRegistryCenter;

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
        String filePath = file.getAbsolutePath();
        try {
            String json = FileUtils.readFileToString(file, Charsets.UTF_8);
            List<String> strings = Splitter.on(File.separator).splitToList(filePath);
            String appName = strings.get(strings.size() - 2);
            String zkPath = Joiner.on("/").join(Arrays.asList(PropertyConfig.ROOT_PATH,appName));
            if(coordinatorRegistryCenter!=null){
                coordinatorRegistryCenter.persist(zkPath,json);
            }
        } catch (Exception e) {
            log.error("读取文件出错" , e);
        }

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
