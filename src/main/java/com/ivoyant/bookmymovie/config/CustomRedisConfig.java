package com.ivoyant.bookmymovie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class CustomRedisConfig {

    @Value("${spring.data.redis.host}") // Get the Redis host from properties
    private String redisHost;

    @Value("${spring.data.redis.port}") // Get the Redis port from properties
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName(redisHost);
        connectionFactory.setPort(redisPort);
        return connectionFactory;
    }
}
