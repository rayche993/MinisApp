package rs.ac.uns.pmf.dmi.minisapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayche on 8/13/16.
 */
public class RestPersonNames extends MinisModel {
    private List<PersonName> personNames;

    public RestPersonNames(){
        personNames = new ArrayList<PersonName>();
    }

    public List<PersonName> getPersonNames() {
        return personNames;
    }

    public void setPersonNames(List<PersonName> personNames) {
        this.personNames = personNames;
    }
}