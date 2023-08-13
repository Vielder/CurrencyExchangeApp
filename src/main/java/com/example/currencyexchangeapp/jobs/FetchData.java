package com.example.currencyexchangeapp.jobs;

import com.example.currencyexchangeapp.services.InitialDBUpdate;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FetchData implements Job {
    private static final Logger logger = LoggerFactory.getLogger(FetchData.class);
    @Autowired
    private InitialDBUpdate initialDBUpdate;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("FetchData job is starting...");
        initialDBUpdate.start();
    }
}
