package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;

/**
 * Created by branko on 16.8.16..
 */
public class AuthorsAdapter extends BaseAdapter {
    private List<PersonName> data;

    public AuthorsAdapter(List<PersonName> data){
        this.data = data;
    }

    public void setData(List<PersonName> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.dropdown_item, parent, false);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.dropdown_item);
        textView.setText(data.get(position).toString());

        return convertView;
    }

    public void add(PersonName item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<PersonName> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public List<PersonName> getData(){
        return data;
    }
}
