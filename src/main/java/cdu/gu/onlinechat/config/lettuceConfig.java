package cdu.gu.onlinechat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@PropertySource("classpath:application.yml")
public class lettuceConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Value("${spring.data.redis.password}")
    private String redisPassword;
    @Value("${spring.data.redis.database}")
    private int redisDatabase;

    @Value("${spring.data.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.data.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.data.redis.lettuce.pool.max-wait}")
    private long maxWaitMillis;
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);
        return new LettuceConnectionFactory(config);
    }
}