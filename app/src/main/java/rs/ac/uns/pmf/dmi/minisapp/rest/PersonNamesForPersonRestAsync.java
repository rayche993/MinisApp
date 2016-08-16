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
import rs.ac.uns.pmf.dmi.minisapp.activity.PersonNameAdapter;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.Person;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPersonNames;

/**
 * Created by rayche on 8/14/16.
 */
public class PersonNamesForPersonRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity = null;
    private HttpMethod httpMethod;
    private String id;
    private PersonNameAdapter adapter = null;
    private PersonName personName;

    public PersonNamesForPersonRestAsync(){}

    public PersonNamesForPersonRestAsync(MyActivity activity, Class<?> actClass, PersonNameAdapter adapter, HttpMethod method, PersonName personName){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;

        this.id = personName.getPerson().getId().toString();
        httpMethod = method;
        this.adapter = adapter;
        this.personName = personName;
    }

    public PersonNamesForPersonRestAsync(PersonNameAdapter adapter, HttpMethod method, String id){
        this.id = id;
        httpMethod = method;
        this.adapter = adapter;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/personNames/{id}/for_person";

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
            personNames.add(personName);

            for (PersonName j : arr) {
                if (!personName.equals(j))
                    personNames.add(j);
            }

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
        if (adapter == null)
            activity.proceedResult(result);
        else
            adapter.proceedResult((RestPersonNames) result);
    }
}