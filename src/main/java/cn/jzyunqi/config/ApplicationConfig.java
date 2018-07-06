package cn.jzyunqi.config;

import cn.jzyunqi.ServiceAdminRestStarter;
import cn.jzyunqi.common.helper.NoGenHelper;
import cn.jzyunqi.common.helper.RedisHelper;
import cn.jzyunqi.common.persistence.dao.impl.BaseDaoImpl;
import cn.jzyunqi.common.support.SnowflakeIdWorker;
import cn.jzyunqi.common.utils.CurrentUserUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author wiiyaya
 * @date 2018/1/21.
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = ServiceAdminRestStarter.class, repositoryBaseClass = BaseDaoImpl.class)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
public class ApplicationConfig {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public RedisHelper redisHelper() {
        return new RedisHelper(redisTemplate);
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> {
            return Optional.ofNullable(CurrentUserUtils.currentUserIdWithNull());
        };
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(0, 0);
    }

    @Bean
    public NoGenHelper noGenHelper() {
        return new NoGenHelper("T", snowflakeIdWorker());
    }
}
