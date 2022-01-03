package com.inyestar.test.config;

import java.util.Optional;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.inyestar.test.config.property.RedisProperty;

import redis.embedded.RedisServer;

@Configuration
public class EmbeddedRedisConfig implements InitializingBean, DisposableBean{

	@Autowired RedisProperty redisProperty;
	
	private RedisServer redisServer;
	
	@Override
	public void destroy() throws Exception {
		Optional.ofNullable(redisServer).ifPresent(RedisServer::stop);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		redisServer = new RedisServer(redisProperty.getPort());
		redisServer.start();
	}

}
