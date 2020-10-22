package czx.me.app.model;


import java.io.Serializable;


public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String host;

    private String email;



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}