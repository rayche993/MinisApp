package rs.ac.uns.pmf.dmi.minisapp.model;

/**
 * Created by rayche on 8/12/16.
 */
public class Journal extends MinisModel{
    private Long id;
    private String name;
    private String pissn;
    private String eissn;
    private String abbreviation;
    private String note;
    private String abstracT;
    private String uri;
    private String doi;
    private Language language;
    private Publisher publisher;
    private StatusType statusType;

    public Journal(){
        id = -1L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPissn() {
        return pissn;
    }

    public void setPissn(String pissn) {
        this.pissn = pissn;
    }

    public String getEissn() {
        return eissn;
    }

    public void setEissn(String eissn) {
        this.eissn = eissn;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAbstracT() {
        return abstracT;
    }

    public void setAbstracT(String abstracT) {
        this.abstracT = abstracT;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    @Override
    public String toString() {
        return name;
    }
}