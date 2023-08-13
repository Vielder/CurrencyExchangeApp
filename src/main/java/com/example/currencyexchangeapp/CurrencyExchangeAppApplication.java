package com.example.currencyexchangeapp;

import com.example.currencyexchangeapp.jobs.FetchData;
import com.example.currencyexchangeapp.services.InitialDBUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CurrencyExchangeAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeAppApplication.class, args);
    }

}
