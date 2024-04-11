package ru.framuga.aspect;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("aspect.timer")
class TimerProperties {

    private boolean enableTimer = false;

    //todo:Возможность выбирать метод либо класс


}
