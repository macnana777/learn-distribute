package info.macnana.reg.exception;

/**
 * author: zhengheng
 * github: https://github.com/macnana777
 * email: 517862340@qq.com
 * <p>
 * Date: 2017-12-04 10:55
 * Description: 注册中心异常
 * Copyright(©) 2017 by zhengheng.
 */
public class RegException extends RuntimeException{

    private static final long serialVersionUID = -6417179023552012152L;

    public RegException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    public RegException(final Exception cause) {
        super(cause);
    }

}
