package rs.ac.uns.pmf.dmi.minisapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayche on 8/13/16.
 */
public class RestPaperJournalAuthors extends MinisModel {
    private List<PaperJournalAuthors> paperJournalAuthorses;

    public RestPaperJournalAuthors(){
        setPaperJournalAuthorses(new ArrayList<PaperJournalAuthors>());
    }

    public List<PaperJournalAuthors> getPaperJournalAuthorses() {
        return paperJournalAuthorses;
    }

    public void setPaperJournalAuthorses(List<PaperJournalAuthors> paperJournalAuthorses) {
        this.paperJournalAuthorses = paperJournalAuthorses;
    }
}