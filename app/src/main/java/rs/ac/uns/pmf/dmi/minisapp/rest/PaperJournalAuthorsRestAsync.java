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
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperJournalAuthors;
import rs.ac.uns.pmf.dmi.minisapp.model.RestJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPaperJournalAuthors;

/**
 * Created by rayche on 8/13/16.
 */
public class PaperJournalAuthorsRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private String id;

    public PaperJournalAuthorsRestAsync(){}

    public PaperJournalAuthorsRestAsync(MyActivity activity, Class<?> actClass, HttpMethod method, String id){
        if (actClass == MainActivity.class)
            this.activity = (MainActivity)activity;

        this.id = id;
        httpMethod = method;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/paperJournalAuthors/{id}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<PaperJournalAuthors[]> responseEntitys = null;
            ResponseEntity<PaperJournalAuthors> responseEntity = null;
            if (id.equals(""))
                responseEntitys = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), PaperJournalAuthors[].class, id);
            else
                responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), PaperJournalAuthors.class, id);

            if (responseEntitys != null) {
                PaperJournalAuthors[] arr = responseEntitys.getBody();
                List<PaperJournalAuthors> paperJournalAuthorses = new ArrayList<PaperJournalAuthors>();
                for (PaperJournalAuthors j : arr)
                    paperJournalAuthorses.add(j);

                RestPaperJournalAuthors rest = new RestPaperJournalAuthors();
                rest.setPaperJournalAuthorses(paperJournalAuthorses);
                return rest;
            }else
                return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new PaperJournalAuthors();
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        activity.proceedResult(result);
    }
}