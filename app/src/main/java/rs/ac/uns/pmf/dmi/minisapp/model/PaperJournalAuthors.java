package rs.ac.uns.pmf.dmi.minisapp.model;

/**
 * Created by rayche on 8/12/16.
 */
public class PaperJournalAuthors extends MinisModel {
    private Long id;
    private Integer numOrder;
    private PersonName personName;
    private PaperJournal paperJournal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOrder() {
        return numOrder;
    }

    public void setNumOrder(Integer numOrder) {
        this.numOrder = numOrder;
    }

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public PaperJournal getPaperJournal() {
        return paperJournal;
    }

    public void setPaperJournal(PaperJournal paperJournal) {
        this.paperJournal = paperJournal;
    }
}