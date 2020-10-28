package czx.me.app.service.email;

import czx.me.app.model.User;
import czx.me.app.properties.AppProperties;
import czx.me.app.service.tasks.SSLCertExpiry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author：czx.me 2020/10/20
 */
@Service
public class EmailService {
    Logger log = Logger.getLogger("EmailService");
    private final JavaMailSender mailSender;

    private final String defaultSender;
    private final List<User> users;

    public EmailService(JavaMailSender mailSender, AppProperties appProperties, List<User> users) {
        this.mailSender = mailSender;
        this.defaultSender = appProperties.getDefaultEmailSender();
        this.users = appProperties.getUserlist();
    }
    public  void taskStart() throws IOException, MessagingException {
        for (User user : users) {
            int remainingDay = SSLCertExpiry.getRemainingDay(user.getHost());
            if (remainingDay<7){
                String title="你的域名:"+user.getHost()+"ssl证书仅剩"+remainingDay+"天";
                String text="certificate expires on :"+remainingDay+" days!";
                String toEmail=user.getEmail();
                this.sendEmail(title,toEmail,text);
            }
        }
    }

    public void sendEmail(String title, String toEmail, String text) throws MessagingException{
        MimeMessage message = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(this.defaultSender);
        helper.setTo(toEmail);
        helper.setText("<h1>"+text+"</h1>", true);
        helper.setSubject(title);
        try {
            this.mailSender.send(message);
            log.info("Text邮件已经发送。");
        } catch (Exception e) {
            log.throwing("发送Text邮件时发生异常！","sendEmail", e);
        }
    }
}
