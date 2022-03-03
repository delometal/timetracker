package com.perigea.tracker.timesheet.service;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.enums.TipoScheduleEvent;
import com.perigea.tracker.commons.exception.NotificationSchedulerException;
import com.perigea.tracker.commons.model.Email;
import com.perigea.tracker.timesheet.job.UserNotificationJob;

@Service
public class SchedulerService {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	Logger logger;

	public void scheduleNotifica(Date dataEsecuzione, Email email) {
		JobDetail detail = buildJobDetail(email, TipoScheduleEvent.ISTANTANEA.toString());
		Trigger trigger = buildJobTrigger(detail, dataEsecuzione);
		try {
			Date nextFire = scheduler.scheduleJob(detail, trigger);
			logger.info(String.format("Notifica schedulata in data: %s", nextFire));
		} catch (Exception e) {
			throw new NotificationSchedulerException(e.getMessage());
		}
		
	}

	public void scheduleNotificaPeriodica(String cron, Email email) {
		JobDetail detail = buildJobDetail(email, TipoScheduleEvent.PERIODICO.toString());
		Trigger trigger = buildCronJobTrigger(detail, cron);
		try {
			Date nextFire = scheduler.scheduleJob(detail, trigger);
			logger.info(String.format("Notifica periodica in data: ", nextFire));
		} catch (Exception e) {
			throw new NotificationSchedulerException(e.getMessage());
		}

	}

	public boolean disactiveNotification(String id) {
		try {
			JobKey key = new JobKey(id, "calendar");
			return scheduler.deleteJob(key);
		} catch (Exception e) {
			throw new NotificationSchedulerException(e.getMessage());
		}
	}

	private JobDetail buildJobDetail(Email email, String type) {
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("email", email);
		dataMap.put("type", type);
		return JobBuilder.newJob(UserNotificationJob.class).withIdentity(email.getEventId(), "tracker")
				.usingJobData(dataMap).withDescription("Job scheduler for notification").build();
	}
	


	private Trigger buildJobTrigger(JobDetail detail, Date dataEsecuzione) {
		return buildJobTrigger(detail, dataEsecuzione, detail.getKey().getName());
	}

	private Trigger buildJobTrigger(JobDetail detail, Date dataEsecuzione, String id) {
		return TriggerBuilder.newTrigger().forJob(detail).withIdentity(id, detail.getKey().getGroup())
				.withDescription(detail.getDescription()).startAt(Date.from(dataEsecuzione.toInstant())).build();
	}

	private CronTrigger buildCronJobTrigger(JobDetail detail, String cron) {
		return buildCronJobTrigger(detail, cron, detail.getKey().getName());
	}

	private CronTrigger buildCronJobTrigger(JobDetail detail, String cron, String id) {
		return TriggerBuilder.newTrigger().forJob(detail).withIdentity(id, detail.getKey().getGroup())
				.withDescription(detail.getDescription()).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
	}
}
