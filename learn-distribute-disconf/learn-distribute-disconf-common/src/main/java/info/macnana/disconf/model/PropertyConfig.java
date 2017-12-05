package info.macnana.disconf.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 14:29
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@Setter
@Getter
public class PropertyConfig {
    public static final String ROOT_PATH = "/disconf";
    /**
     * 应用名
     */
    private String appName;

    /**
     * 配置文件名
     */
    private String key;

    /**
     * 配置文件内容
     */
    private Map<String,String> prosMap;

}
