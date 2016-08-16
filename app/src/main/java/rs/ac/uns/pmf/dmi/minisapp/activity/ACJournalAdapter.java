package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

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

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.RestJournal;

/**
 * Created by rayche on 8/15/16.
 */
public class ACJournalAdapter extends ArrayAdapter<Journal> implements Filterable {
    private ArrayList<Journal> resultList;
    private String token;

    public ACJournalAdapter(Context context, int textViewResourceId, String token) {
        super(context, textViewResourceId);
        this.token = token;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Journal getItem(int index) {
        return resultList.get(index);
    }

    private ArrayList<Journal> getRestJournals(CharSequence constraint){
        String url = "http://192.168.1.3:9000/api/_search/journals/{id}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        requestHeaders.set("X-Auth-Token", token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<Journal[]> responseEntitys = null;
            responseEntitys = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Journal[].class, constraint);

            Journal[] arr = responseEntitys.getBody();
            ArrayList<Journal> journals = new ArrayList<Journal>();
            for (Journal j : arr)
                journals.add(j);

            return journals;
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new ArrayList<Journal>();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = getRestJournals(constraint);

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}