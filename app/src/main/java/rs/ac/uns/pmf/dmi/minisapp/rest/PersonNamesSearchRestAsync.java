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

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.pmf.dmi.minisapp.activity.MainActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.NewPaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPersonNames;

/**
 * Created by rayche on 8/14/16.
 */
public class PersonNamesSearchRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private String id;

    public PersonNamesSearchRestAsync(){}

    public PersonNamesSearchRestAsync(MyActivity activity, Class<?> actClass, HttpMethod method, String id){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;

        this.id = id;
        httpMethod = method;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/_search/personNames/{id}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<PersonName[]> responseEntitys = null;
            responseEntitys = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), PersonName[].class, id);

            PersonName[] arr = responseEntitys.getBody();
            List<PersonName> personNames = new ArrayList<PersonName>();
            for (PersonName j : arr)
                personNames.add(j);

            RestPersonNames rest = new RestPersonNames();
            rest.setPersonNames(personNames);
            return rest;
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new RestPersonNames();
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        activity.proceedResult(result);
    }
}