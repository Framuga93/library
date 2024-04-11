package ru.framuga.aspect;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TimerProperties.class)
//@ConditionalOnProperty(value = "aspect.annotation.timer.enable", havingValue = "true")  //todo: Разобраться в ConditionalOnProperty
public class HttpTimerAutoConfiguration {

    @Bean
    TimerAspect timerAspectFilter(TimerProperties properties){
        return new TimerAspect(properties);
    }
}
