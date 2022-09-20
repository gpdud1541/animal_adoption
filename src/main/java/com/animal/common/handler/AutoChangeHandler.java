package com.animal.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AutoChangeHandler {

    private static final Logger logger = LoggerFactory.getLogger(AutoChangeHandler.class);

    //    @Scheduled(cron = "0 50 23 ? * MON-FRI") // 월-금 23시 59분 (초, 분, 시, 일, 월, 요일)
    //    @Scheduled(cron = "0 5 * * * *") // 5분마다
    //    public void change() {
    //
    //    }
}
