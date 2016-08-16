package rs.ac.uns.pmf.dmi.minisapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayche on 8/13/16.
 */
public class RestPaperJournal extends MinisModel{
    private List<PaperJournal> paperJournals;

    public RestPaperJournal(){
        paperJournals = new ArrayList<PaperJournal>();
    }

    public List<PaperJournal> getPaperJournals() {
        return paperJournals;
    }

    public void setPaperJournals(List<PaperJournal> paperJournals) {
        this.paperJournals = paperJournals;
    }
}