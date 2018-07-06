package cn.jzyunqi.config;

import cn.jzyunqi.common.exception.resolver.GlobalExceptionHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author wiiyaya
 * @date 2018/1/21.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public GlobalExceptionHandlerPlus globalExceptionHandler(){
        GlobalExceptionHandlerPlus globalExceptionHandler = new GlobalExceptionHandlerPlus();
        globalExceptionHandler.setShowRealError(true);
        return globalExceptionHandler;
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        GlobalExceptionHandlerExceptionResolver exceptionResolver = new GlobalExceptionHandlerExceptionResolver(exceptionResolvers);
        exceptionResolver.addIgnoredExceptionHandlers(TokenEndpoint.class, AuthorizationEndpoint.class, CheckTokenEndpoint.class);
        exceptionResolver.afterPropertiesSet();
    }
}
