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
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.activity.NewPaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.Language;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperType;
import rs.ac.uns.pmf.dmi.minisapp.model.RestLanguage;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPaperType;

/**
 * Created by rayche on 8/13/16.
 */
public class PaperTypeRestAsync extends AsyncTask<Void, Void, MinisModel> {
    private MyActivity activity;
    private HttpMethod httpMethod;
    private String id;

    public PaperTypeRestAsync(){}

    public PaperTypeRestAsync(MyActivity activity, Class<?> actClass, HttpMethod method, String id){
        if (actClass == NewPaperJournal.class)
            this.activity = (NewPaperJournal)activity;

        this.id = id;
        httpMethod = method;
    }

    @Override
    protected MinisModel doInBackground(Void... params){
        String url = "http://10.5.0.51:9000/api/paperTypes/{id}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        LoginInfo info = activity.getLoginInfo();
        requestHeaders.set("X-Auth-Token", info.getToken());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<RestPaperType> responseEntitys = null;
            ResponseEntity<PaperType> responseEntity = null;
            if (id.equals(""))
                responseEntitys = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), RestPaperType.class, id);
            else
                responseEntity = restTemplate.exchange(url, httpMethod, new HttpEntity<Object>(requestHeaders), PaperType.class, id);

            if (responseEntitys != null)
                return responseEntitys.getBody();
            else
                return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new RestPaperType();
    }

    @Override
    protected void onPostExecute(MinisModel result) {
        activity.proceedResult(result);
    }
}