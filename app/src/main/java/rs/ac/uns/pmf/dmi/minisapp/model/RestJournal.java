package rs.ac.uns.pmf.dmi.minisapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayche on 8/12/16.
 */
public class RestJournal extends MinisModel{
    private List<Journal> journals;

    public RestJournal(){
        journals = new ArrayList<Journal>();
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }
}