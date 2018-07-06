package cn.jzyunqi.config;

import cn.jzyunqi.common.exception.resolver.GlobalExceptionHandler;
import org.springframework.boot.web.servlet.error.ErrorController;

/**
 * @author wiiyaya
 * @date 2018/7/2.
 */
public class GlobalExceptionHandlerPlus extends GlobalExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
