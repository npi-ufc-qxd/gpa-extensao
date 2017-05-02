package ufc.quixada.npi.gpa.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ufc.quixada.npi.gpa.service.NotificationService;

@Component
public class EmailTask {
	
	@Autowired
	private NotificationService notificationService;

	@SuppressWarnings("deprecation")
	@Scheduled(cron = "0 1 0 * * ?")
    public void executarEmRegineCronologico() {
		Date hoje = new Date();
		hoje.setDate(hoje.getDate()+1);
		notificationService.notificarPareceristaRelatorPrazo(hoje);
    }
}
