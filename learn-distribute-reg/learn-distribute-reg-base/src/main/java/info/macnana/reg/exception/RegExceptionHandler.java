package info.macnana.reg.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 10:52
 * Description: 注册中心异常处理
 * Copyright(©) 2017 by zhengheng.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegExceptionHandler {

    public static void handleException(final Exception cause){
        if (null == cause) {
            return;
        }
        else if (cause instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        } else {
            log.error("注册中心异常" , cause);
            throw new RegException(cause);
        }
    }

}
