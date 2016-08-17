package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;

/**
 * Created by rayche on 8/14/16.
 */
public class JournalsAdapter extends BaseAdapter {
    private List<Journal> data;

    public JournalsAdapter(List<Journal> journals){
        data = journals;
    }

    public void setData(List<Journal> data) {
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
            convertView = inflater.inflate(R.layout.journals_list_item, parent, false);
        }

        TextView lblInfo = (TextView)convertView.findViewById(R.id.lblInfo);
        Journal j = data.get(position);
        String info = "ID: " + j.getId() + " | " + "Name: " + j.getName() + " | " + "EIssn: " + j.getEissn();
        lblInfo.setText(info);

        return convertView;
    }

    public void add(Journal item) {
        boolean update = false;
        for (Journal j : data){
            if (j.equals(item)){
                int id = data.indexOf(j);
                data.remove(id);
                data.add(id, item);
                update = true;
            }
        }

        if (!update)
            data.add(item);

        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Journal> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Journal> getData(){
        return data;
    }
}