package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

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

import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPersonNames;

/**
 * Created by rayche on 8/15/16.
 */
public class ACPersonNameAdapter extends ArrayAdapter<PersonName> implements Filterable {
    private ArrayList<PersonName> resultList;
    private String token;

    public ACPersonNameAdapter(Context context, int textViewResourceId, String token) {
        super(context, textViewResourceId);
        this.token = token;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public PersonName getItem(int index) {
        return resultList.get(index);
    }

    private ArrayList<PersonName> getRestPersonNames(CharSequence constraint){
        String url = "http://10.5.0.51:9000/api/_search/personNames/{id}";

        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.set("Accept","application/json");
        requestHeaders.set("Content-Type", "application/x-www-form-urlencoded");
        requestHeaders.set("X-Auth-Token", token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            ResponseEntity<PersonName[]> responseEntitys = null;
            responseEntitys = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), PersonName[].class, constraint);

            PersonName[] arr = responseEntitys.getBody();
            ArrayList<PersonName> personNames = new ArrayList<PersonName>();
            for (PersonName j : arr)
                personNames.add(j);

            return  personNames;
        }catch (HttpClientErrorException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        } catch (ResourceAccessException e) {
            Log.e("rest", e.getLocalizedMessage(), e);
        }
        return new ArrayList<PersonName>();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    resultList = getRestPersonNames(constraint);

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