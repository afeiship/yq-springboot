package cn.jzyunqi.config;

import cn.jzyunqi.common.support.spring.DefaultOAuth2ExceptionRendererPlus;
import cn.jzyunqi.common.support.spring.RedisTokenStoreJDK8;
import cn.jzyunqi.ms.uaa.service.ResourceService;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * @author wiiyaya
 * @date 2018/5/3
 */
@Configuration
@EnableWebSecurity
public class OAuth2ResourceConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStoreJDK8(redisConnectionFactory);
    }

    public OAuth2ResourceConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) {
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**", "/webjars/**", "**/favicon.ico");
    }

    /**
     * 资源服务器配置
     */
    @Configuration
    @EnableResourceServer
    public static class RestResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Resource
        private MessageSource messageSource;

        @Resource
        private ResourceService resourceService;

        @Resource
        private HttpMessageConverters messageConverters;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            DefaultOAuth2ExceptionRendererPlus rendererPlus = new DefaultOAuth2ExceptionRendererPlus();
            rendererPlus.setMessageConverters(messageConverters.getConverters());
            rendererPlus.setMessageSource(messageSource);

            //异常处理
            OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
            entryPoint.setExceptionRenderer(rendererPlus);
            resources.authenticationEntryPoint(entryPoint);

            //权限处理
            OAuth2AccessDeniedHandler accessDeniedHandler = new OAuth2AccessDeniedHandler();
            accessDeniedHandler.setExceptionRenderer(rendererPlus);
            resources.accessDeniedHandler(accessDeniedHandler);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**");
            String[] noNeedAuths = resourceService.getNoNeedAuthResources();
            http.authorizeRequests().antMatchers(noNeedAuths).permitAll();

            //Map<String, String[]> needAuths = resourceService.getNeedAuthResources();
            //for (Map.Entry<String, String[]> entity : needAuths.entrySet()) {
            //    http.authorizeRequests().antMatchers(entity.getKey()).hasAnyAuthority(entity.getValue());
            //}
            //
            //http.authorizeRequests().anyRequest().authenticated();
        }
    }
}
