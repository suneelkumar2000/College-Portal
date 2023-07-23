package com.project.college_portal;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//@ConditionalOnProperty(value = "email", havingValue = "true", matchIfMissing = false)
public class MailScheduler {
	// 1000 * 60 * 2 = 2 mins
	@Scheduled(fixedDelayString = "12000")
	public void notifyMail() {
		LocalDateTime todayDateTime = LocalDateTime.now();
		System.out.println("notifymail :" + todayDateTime);
	}

	@Scheduled(cron = "0 */1 * * * *")
	public void notifyMailCron() {
		LocalDateTime todayDateTime = LocalDateTime.now();
		System.out.println("notifymailcron :" + todayDateTime);
	}

}
