package rs.ac.uns.pmf.dmi.minisapp.rest;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.pmf.dmi.minisapp.activity.JournalsActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MainActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.NewJournalActivity;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.RestJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.User;

/**
 * Created by rayche on 8/12/16.
 */
public class JournalsRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private String id;
    private Journal journal = null;

    public JournalsRestAsync(){}

    public JournalsRestAsync(MyActivity activity, Class<?> actClass, String id){
        if (actClass == JournalsActivity.class)
            this.activity = (JournalsActivity)activity;
        if (actClass == NewJournalActivity.class)
            this.activity = (NewJournalActivity)activity;

        this.id = id;
        httpMethod = HttpMethod.GET;
    }

    public JournalsRestAsync(MyActivity activity, Class<?> actClass, HttpMethod method, Journal journal){
        if (actClass == JournalsActivity.class)
            this.activity = (JournalsActivity)activity;
        if (actClass == NewJournalActivity.class)
            this.activity = (NewJournalActivity)activity;

        this.journal = journal;
        httpMethod = method;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/journals/";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/json");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        HttpEntity<Journal> requestEntity = null;
        if (journal != null) {
            journal.setId(null);
            requestEntity = new HttpEntity<Journal>(journal, requestHeaders);
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Journal[]> responseEntitys = null;
            ResponseEntity<Journal> responseEntity = null;
            if (requestEntity != null){
                responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, Journal.class);
            }else {
                if (id.equals(""))
                    responseEntitys = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), Journal[].class);
                else
                    responseEntity = restTemplate.exchange(url + "{id}", httpMethod, new HttpEntity<Object>(requestHeaders), Journal.class, id);
            }

            if (responseEntitys != null) {
                Journal[] arr = responseEntitys.getBody();
                List<Journal> journals = new ArrayList<Journal>();
                for (Journal j : arr)
                    journals.add(j);

                RestJournal rest = new RestJournal();
                rest.setJournals(journals);
                return rest;
            }else {
                Log.e("JOURNAL_ID", responseEntity.getBody().getId().toString());
                return responseEntity.getBody();
            }
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new Journal();
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        if (journal != null)
            activity.proceedPost(result);
        else
            activity.proceedResult(result);
    }
}
