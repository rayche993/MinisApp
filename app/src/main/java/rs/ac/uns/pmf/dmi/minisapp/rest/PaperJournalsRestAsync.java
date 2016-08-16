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

import rs.ac.uns.pmf.dmi.minisapp.activity.JournalsActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MainActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MenuActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.NewPaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPaperJournal;

/**
 * Created by rayche on 8/13/16.
 */
public class PaperJournalsRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private String id;
    private PaperJournal paperJournal = null;

    public PaperJournalsRestAsync(){}

    public PaperJournalsRestAsync(MyActivity activity, Class<?> actClass, HttpMethod method, String id){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;

        this.id = id;
        httpMethod = method;
    }

    public PaperJournalsRestAsync(MyActivity activity, Class<?> actClass, PaperJournal paperJournal){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;

        this.paperJournal = paperJournal;
        httpMethod = HttpMethod.POST;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://192.168.1.3:9000/api/paperJournals/{id}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        HttpEntity<PaperJournal> requestEntity = null;
        if (paperJournal != null)
            requestEntity = new HttpEntity<PaperJournal>(paperJournal, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<PaperJournal[]> responseEntitys = null;
            ResponseEntity<PaperJournal> responseEntity = null;
            if (requestEntity != null){
                responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, PaperJournal.class);
            }else {
                if (id.equals(""))
                    responseEntitys = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), PaperJournal[].class, id);
                else
                    responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), PaperJournal.class, id);
            }

            if (responseEntitys != null) {
                PaperJournal[] arr = responseEntitys.getBody();
                List<PaperJournal> paperJournals = new ArrayList<PaperJournal>();
                for (PaperJournal j : arr)
                    paperJournals.add(j);

                RestPaperJournal rest = new RestPaperJournal();
                rest.setPaperJournals(paperJournals);
                return rest;
            }else
                return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new PaperJournal();
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        if (paperJournal == null)
            activity.proceedResult(result);
        else
            activity.proceedPost(result);
    }
}