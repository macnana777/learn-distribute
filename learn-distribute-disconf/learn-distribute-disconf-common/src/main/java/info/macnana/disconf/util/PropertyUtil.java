package info.macnana.disconf.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.event.Event;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 14:54
 * Description: 属性文件读取工具
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
public class PropertyUtil {

    private static FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
            new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class);

    public static Map<String,String> getProperties(File propertiesFile){
        Preconditions.checkNotNull(propertiesFile, "参数->属性文件为空");
        Preconditions.checkArgument(propertiesFile.exists(), "参数->属性文件:%s 不存在" ,propertiesFile.getPath());
        Parameters params = new Parameters();
        builder.configure(
                params.fileBased()
                .setEncoding("UTF-8")
                .setFile(propertiesFile));
//        builder.addEventListener(ConfigurationEvent.ANY,new EventListener<ConfigurationEvent>(){
//
//            @Override
//            public void onEvent(ConfigurationEvent event) {
//                System.out.println(event.getEventType());
//            }
//        });
        try {
            Configuration config = builder.getConfiguration();
            if(config!=null){
                Map<String,String> prosMap = Maps.newHashMap();
                Iterator<String> keys = config.getKeys();
                while (keys.hasNext()){
                    String key = keys.next();
                    String value = config.getString(key);
                    prosMap.put(key,value);
                }
                return prosMap;
            }
        } catch (ConfigurationException e) {
           log.error("读取配置文件失败", e);
        }
        return null;
    }
}
