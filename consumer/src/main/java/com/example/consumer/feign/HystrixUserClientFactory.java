package com.example.consumer.feign;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import feign.hystrix.FallbackFactory;

@Component
public class HystrixUserClientFactory implements FallbackFactory<UserFeignClient> {

  private static final Logger LOGGER = LoggerFactory.getLogger(HystrixUserClientFactory.class);

  @Override
  public UserFeignClient create(Throwable cause) {
    HystrixUserClientFactory.LOGGER.info("fallback; reason was: {}", cause.getMessage());
    return (UserFeignClientWithFactory) id -> {
      User user = new User();
      user.setId(-1L);
      return user;
    };
  }
}
