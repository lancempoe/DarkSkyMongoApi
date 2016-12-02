package com.lancep.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.lancep.*"})
public class SpringRootConfiguration {

/*
  If you have two different implementations of an interface
  this this is an example of how you can define what to return.

      @Bean
      public DarkSkyService getDarkSkyService(){
          return new DarkSkyServiceImpl();
      }
 */

}
