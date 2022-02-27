package com.example.keepmock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableMongoAuditing
@EnableScheduling
public class KeepmockConfig {

//    @Bean
//    public MongoTemplate mongoTemplate(){
//        return new MongoTemplate();
//    }
}
