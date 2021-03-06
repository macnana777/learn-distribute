package info.macnana.disconf.process.impl;

import info.macnana.disconf.process.ProsProcess;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-06 9:06
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
public class ProsProcessImpl implements ProsProcess{

    @Override
    public void init(String childPath, String data) {
        log.info("初始化节点-->{}" ,childPath);
        log.info("初始化数据-->{}" , data);
    }

    @Override
    public void add(String childPath, String data) {
        log.info("新增节点-->{}" ,childPath);
        log.info("新增数据-->{}" , data);
    }

    @Override
    public void update(String childPath, String data) {
        log.info("变更节点-->{}" ,childPath);
        log.info("变更数据-->{}" , data);
    }

    @Override
    public void delete(String childPath, String data) {
        log.info("删除节点-->{}" ,childPath);
        log.info("删除数据-->{}" , data);
    }
}
