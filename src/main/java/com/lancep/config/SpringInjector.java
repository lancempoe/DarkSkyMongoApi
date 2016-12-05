package com.lancep.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * If you have two different implementations of an interface
 * this this is an example of how you can define what to return.
 *
 *     @Bean
 *     public DarkSkyService getDarkSkyService(){
 *         return new DarkSkyServiceImpl();
 *     }
 *
 */
@SuppressWarnings("JavaDoc")
@Configuration
@ComponentScan(value={"com.lancep.*"})
public class SpringInjector {

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(), MongoProperties.DARK_SKY_COLLECTION);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}
