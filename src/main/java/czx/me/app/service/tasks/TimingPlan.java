package czx.me.app.service.tasks;

import czx.me.app.service.email.EmailService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.logging.Logger;


/**
 *
 * @author：czx.me 2020/10/20
 */

@Component
@Configurable
@EnableScheduling
public class TimingPlan {
    Logger log = Logger.getLogger("TimingPlan");
    private final EmailService emailService;

    public TimingPlan(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "${timePlan.scheduler}")
    public void doTimingPlan() throws MessagingException, IOException {
        emailService.taskStart();
        log.info("域名检查任务已启动！");
    }
}
