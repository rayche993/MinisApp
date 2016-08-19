package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.Person;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPersonNames;
import rs.ac.uns.pmf.dmi.minisapp.rest.PersonNamesForPersonRestAsync;

/**
 * Created by rayche on 8/15/16.
 */
public class PersonNameAdapter extends BaseAdapter {
    private List<List<PersonName>> data;
    private NewPaperJournal activity;

    public PersonNameAdapter(NewPaperJournal activity, List<List<PersonName>> personNames){
        data = personNames;
        this.activity = activity;
    }

    public void setData(List<List<PersonName>> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.paper_journal_list_item, parent, false);
        }

        Button btnRemove = (Button)convertView.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });

        Button btnMoveUp = (Button)convertView.findViewById(R.id.btnMoveUp);
        btnMoveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveUpItem(position);
            }
        });

        Button btnMoveDown = (Button)convertView.findViewById(R.id.btnMoveDown);
        btnMoveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveDownItem(position);
            }
        });

        Button btnAdd = (Button)convertView.findViewById(R.id.btnAddPersonName);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addPersonName(position, data.get(position).get(0).getPerson());
            }
        });

        Spinner authorsSpinner = (Spinner)convertView.findViewById(R.id.authorsSpinner);
        AuthorsAdapter adapter = new AuthorsAdapter(data.get(position));
        authorsSpinner.setAdapter(adapter);

        authorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                PersonName personName = (PersonName)parent.getAdapter().getItem(i);
                //Collections.swap(((AuthorsAdapter)parent.getAdapter()).getData(), i, 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return convertView;
    }

    public void proceedResult(RestPersonNames personNames){
        data.add(personNames.getPersonNames());
        notifyDataSetChanged();
    }

    public void proceedResult(RestPersonNames personNames, int person){
        data.add(personNames.getPersonNames());
        notifyDataSetChanged();
    }

    public void add(PersonName item) {
        new PersonNamesForPersonRestAsync(activity, NewPaperJournal.class, this, HttpMethod.GET, item).execute();
    }

    public void add(PersonName item, int person) {
        data.get(person).add(0, item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<List<PersonName>> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public List<List<PersonName>> getData(){
        return data;
    }

    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    public void moveUpItem(int position){
        if (position > 0) {
            Collections.swap(data, position, position - 1);
            notifyDataSetChanged();
        }
    }

    public  void moveDownItem(int position){
        if (position < data.size() - 1){
            Collections.swap(data, position, position + 1);
            notifyDataSetChanged();
        }
    }
}