package com.zxw.sso.server.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @author wuhongyun
 * @date 2020/5/28 23:19
 */
//@Configuration
public class RedisConfig {

    @Profile(value = {"test", "dev"})
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisProperties.Cluster clusterProperties = redisProperties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());

        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        return new LettuceConnectionFactory(config);
    }

    @Profile(value = {"local"})
    @Bean
    public LettuceConnectionFactory redisConnectionFactory1(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setDatabase(redisProperties.getDatabase());
        if (StrUtil.isNotBlank(redisProperties.getPassword())) {
            config.setPassword(redisProperties.getPassword());
        }
        return new LettuceConnectionFactory(config);
    }
}
