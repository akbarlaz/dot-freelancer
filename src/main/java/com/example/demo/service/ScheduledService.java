package com.example.demo.service;

import com.example.demo.repository.mock.MockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ScheduledService {

    Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    MockRepository mockRepository;

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 10)
    public void deleteMocks() {
        logger.info("Deleting mocks data from database every 10 seconds");
        mockRepository.deleteAll();
    }
}
