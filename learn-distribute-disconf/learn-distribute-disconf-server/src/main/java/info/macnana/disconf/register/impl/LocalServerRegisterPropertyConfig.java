package info.macnana.disconf.register.impl;

import info.macnana.disconf.model.PropertyConfig;
import info.macnana.disconf.register.AbstractRegisterProsConfig;
import info.macnana.disconf.util.PropertyUtil;
import info.macnana.reg.base.CoordinatorRegistryCenter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 15:51
 * Description: 扫描属性文件，将属性注册到注册中心中
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
public class LocalServerRegisterPropertyConfig extends AbstractRegisterProsConfig {

    @Getter
    private final String scanPath;

    /**
     * 所有应用的属性<应用名，<属性文件名，属性>>
     */
    //private Table<String,String,Map<String,String>> prosTable =

    public LocalServerRegisterPropertyConfig(CoordinatorRegistryCenter coordinatorRegistryCenter,String scanPath){
        super(coordinatorRegistryCenter);
        this.scanPath = scanPath;
    }

    @Override
    public void scanPros() {
        //遍历SCAN_PATH文件夹下面的文件夹
        //目录结构为： /disconf/应用名/属性文件名
        Path rootPath = Paths.get(scanPath);
        try {
            DirectoryStream<Path> appPaths = Files.newDirectoryStream(rootPath);
            for (Path appPath : appPaths) {
                File appFile = appPath.toFile();
                if(appFile.isDirectory()){
                    String appName = appFile.getName();
                    //遍历应用文件夹下面的文件
                    DirectoryStream<Path> paths = Files.newDirectoryStream(appPath,"*.properties");
                    for (Path path: paths) {
                        String key = path.toFile().getName();
                        Map<String,String> prosMap = PropertyUtil.getProperties(path.toFile());
                        PropertyConfig propertyConfig = new PropertyConfig();
                        propertyConfig.setAppName(appName);
                        propertyConfig.setKey(key);
                        propertyConfig.setProsMap(prosMap);
                        super.registry(propertyConfig);
                    }
                }
            }
        } catch (IOException e) {
            log.error("初始化配置到注册中心出错" , e);
        }

    }


}
