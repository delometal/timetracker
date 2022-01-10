package com.perigea.tracker.timesheet.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class LoggerResolver {
	
    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
    	return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

}