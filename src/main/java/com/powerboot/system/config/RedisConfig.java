package com.powerboot.system.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Component
public class RedisConfig {
    private Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Autowired
    private RedisProperties properties;

    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        JedisPool pool;
        if(StringUtils.isNotBlank(properties.getPassword())){
            pool = new JedisPool(config,properties.getHost(),properties.getPort(),5000,properties.getPassword());
        }else{
            pool = new JedisPool(config,properties.getHost(),properties.getPort(),5000);
        }
        return pool;
    }

}
