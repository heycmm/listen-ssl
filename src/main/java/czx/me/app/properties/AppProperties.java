package czx.me.app.properties;

import czx.me.app.model.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author：czx.me 2020/10/22
 */
@ConfigurationProperties(prefix = "app")
@Component
@Validated
public class AppProperties {
    @NotEmpty
    @Email
    private String defaultEmailSender;
    @NotEmpty
    private List<User> userlist;

    public String getDefaultEmailSender() {
        return defaultEmailSender;
    }

    public void setDefaultEmailSender(String defaultEmailSender) {
        this.defaultEmailSender = defaultEmailSender;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }
}
