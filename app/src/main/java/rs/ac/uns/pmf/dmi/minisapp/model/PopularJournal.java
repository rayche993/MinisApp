package rs.ac.uns.pmf.dmi.minisapp.model;

/**
 * Created by android on 31.8.16..
 */
public class PopularJournal extends MinisModel {
    private int id;
    private int journal_id;
    private int number;

    public PopularJournal(){

    }

    public PopularJournal(int id, int journal_id, int number){
        this.id = id;
        this.journal_id = journal_id;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJournal_id() {
        return journal_id;
    }

    public void setJournal_id(int journal_id) {
        this.journal_id = journal_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}