package rs.ac.uns.pmf.dmi.minisapp.model;

/**
 * Created by rayche on 8/9/16.
 */
public class LoginInfo extends MinisModel{
    private String token;
    private String expires;

    public LoginInfo(){
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
