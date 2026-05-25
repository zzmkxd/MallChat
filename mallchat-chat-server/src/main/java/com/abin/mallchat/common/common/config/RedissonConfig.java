package com.abin.mallchat.common.common.config;
    import lombok.RequiredArgsConstructor;
    import org.redisson.Redisson;
    import org.redisson.api.RedissonClient;
    import org.redisson.config.Config;
    import org.redisson.config.SingleServerConfig;
    import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    /**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-04-22
 */
@Configuration
@RequiredArgsConstructor
public class RedissonConfig {    
    private final RedisProperties redisProperties;
    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
    SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setDatabase(redisProperties.getDatabase());
    // 只在密码不为空时才设置
        String password = redisProperties.getPassword();
    if (password != null && !password.isEmpty()) {
            singleServerConfig.setPassword(password);
    }

        return Redisson.create(config);
    }
}
