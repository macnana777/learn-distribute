package info.macnana.disconf.listener;

import info.macnana.disconf.listener.register.ListenerRegister;
import info.macnana.disconf.listener.register.impl.DirListenerRegister;
import info.macnana.disconf.listener.register.impl.FileListenerRegister;
import info.macnana.disconf.listener.strategy.impl.DirListenerStrategy;
import info.macnana.disconf.listener.strategy.impl.FileListenerStrategy;
import org.junit.Test;

import java.io.IOException;

/**
 * author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 23:05
 * Description:
 * Copyright(©) 2017 by zhengheng.
 */
public class ListenerTest {

    @Test
    public void dirListener() throws IOException {
        ListenerRegister listenerRegister = new DirListenerRegister(new DirListenerStrategy(),5);
        listenerRegister.register("C:\\WorkSpace\\learn\\learn-distribute\\learn-distribute-disconf\\learn-distribute-disconf-conf\\disconf");
        System.in.read();
    }

    @Test
    public void fileListener() throws IOException{
        ListenerRegister listenerRegister = new FileListenerRegister(new FileListenerStrategy(),5);
        listenerRegister.register("C:\\WorkSpace\\learn\\learn-distribute\\learn-distribute-disconf\\learn-distribute-disconf-conf\\disconf");
        System.in.read();
    }

}
