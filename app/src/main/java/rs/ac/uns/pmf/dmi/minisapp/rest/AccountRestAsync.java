package rs.ac.uns.pmf.dmi.minisapp.rest;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import rs.ac.uns.pmf.dmi.minisapp.activity.MainActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MenuActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.User;

/**
 * Created by rayche on 8/12/16.
 */
public class AccountRestAsync extends AsyncTask<Void, Void, User> {
    private MyActivity activity;
    private HttpMethod httpMethod;

    public AccountRestAsync(){}

    public AccountRestAsync(MyActivity activity, Class<?> actClass, HttpMethod method){
        if (actClass == MenuActivity.class)
            this.activity = (MenuActivity)activity;

        httpMethod = method;
    }

    @Override
    protected User doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/account";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<User> responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), User.class);
            return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new User();
    }

    @Override
    protected void onPostExecute(User result) {
        activity.proceedResult(result);
    }
}