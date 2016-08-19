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

import java.util.List;

import rs.ac.uns.pmf.dmi.minisapp.activity.MainActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.Problem;

/**
 * Created by rayche on 8/12/16.
 */
public class AuthRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private String username;
    private String password;
    private HttpMethod httpMethod;

    public AuthRestAsync(){}

    public AuthRestAsync(MyActivity activity, Class<?> actClass, HttpMethod method, String username, String password){
        if (actClass == MainActivity.class)
            this.activity = (MainActivity)activity;

        this.username = username;
        this.password = password;

        httpMethod = method;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/authenticate?username={user}&password={pass}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<LoginInfo> responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), LoginInfo.class, username, password);
            return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            return new Problem("Bad credentials!");
        } catch (ResourceAccessException e) {
            return new Problem("Network problem!");
        }
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        activity.proceedResult(result);
    }
}
