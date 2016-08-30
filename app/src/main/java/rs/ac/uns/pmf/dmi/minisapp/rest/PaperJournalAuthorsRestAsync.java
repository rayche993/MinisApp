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
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperJournalAuthors;
import rs.ac.uns.pmf.dmi.minisapp.model.Problem;
import rs.ac.uns.pmf.dmi.minisapp.model.RestJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPaperJournalAuthors;

/**
 * Created by rayche on 8/13/16.
 */
public class PaperJournalAuthorsRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private int id;
    private PaperJournalAuthors authors;

    public PaperJournalAuthorsRestAsync(){}

    public PaperJournalAuthorsRestAsync(MyActivity activity, Class<?> actClass, PaperJournalAuthors authors, int id){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;

        this.authors = authors;
        this.id = id;
        httpMethod = HttpMethod.POST;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://10.5.0.51:9000/api/paperJournalAuthorss/";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/json");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        authors.setId(null);
        HttpEntity<PaperJournalAuthors> requestEntity = new HttpEntity<PaperJournalAuthors>(authors, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<PaperJournalAuthors> responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, PaperJournalAuthors.class);

            return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            return new Problem("Paper journal author isn't saved!");
        } catch (ResourceAccessException e) {
            return new Problem("Paper journal author isn't saved!");
        }
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        activity.proceedPost(result, id + 1);
    }
}