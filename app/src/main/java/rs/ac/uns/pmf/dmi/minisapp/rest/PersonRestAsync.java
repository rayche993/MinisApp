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

import rs.ac.uns.pmf.dmi.minisapp.activity.MenuActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.NewPersonNameActivity;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.Person;
import rs.ac.uns.pmf.dmi.minisapp.model.User;

/**
 * Created by branko on 19.8.16..
 */
public class PersonRestAsync extends AsyncTask<Void, Void, Person> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private String id;

    public PersonRestAsync(){}

    public PersonRestAsync(MyActivity activity, Class<?> actClass, String id){
        if (actClass == NewPersonNameActivity.class)
            this.activity = (NewPersonNameActivity)activity;

        httpMethod = HttpMethod.GET;
        this.id = id;
    }

    @Override
    protected Person doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/persons/{id}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/json");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Person> responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), Person.class, id);
            return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new Person();
    }

    @Override
    protected void onPostExecute(Person result) {
        activity.proceedResult(result);
    }
}