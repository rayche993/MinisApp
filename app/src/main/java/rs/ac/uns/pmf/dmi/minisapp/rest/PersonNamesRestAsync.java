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
import rs.ac.uns.pmf.dmi.minisapp.activity.NewPersonNameActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.PersonNameAdapter;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;
import rs.ac.uns.pmf.dmi.minisapp.model.Problem;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPersonNames;

/**
 * Created by rayche on 8/13/16.
 */
public class PersonNamesRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private PersonName personName;
    private String id;
    private int person;

    public PersonNamesRestAsync(){}

    public PersonNamesRestAsync(MyActivity activity, Class<?> actClass, PersonName personName){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;
        if (actClass == NewPersonNameActivity.class)
            this.activity = (NewPersonNameActivity)activity;

        httpMethod = HttpMethod.POST;
        this.personName = personName;
    }

    public PersonNamesRestAsync(MyActivity activity, Class<?> actClass, String id, int person){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;

        if (actClass == NewPersonNameActivity.class)
            this.activity = (NewPersonNameActivity)activity;

        httpMethod = HttpMethod.GET;
        this.id = id;
        this.person = person;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/personNames/";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/json");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        HttpEntity<PersonName> requestEntity = null;

        if (personName != null) {
            personName.setId(null);
            requestEntity = new HttpEntity<PersonName>(personName, requestHeaders);
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<PersonName> responseEntity = null;
            if (personName != null)
                responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, PersonName.class);
            else
                responseEntity = restTemplate.exchange(url + "{id}", httpMethod, new HttpEntity<Object>(requestHeaders), PersonName.class, id);

            return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            return new Problem("No response!");
        } catch (ResourceAccessException e) {
            return new Problem("No response!");
        }
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        if (personName != null)
            activity.proceedPost(result);
        else
            activity.proceedResult(result, person);
    }
}