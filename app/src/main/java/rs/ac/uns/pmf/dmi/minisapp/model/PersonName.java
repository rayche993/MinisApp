package rs.ac.uns.pmf.dmi.minisapp.model;

/**
 * Created by rayche on 8/12/16.
 */
public class PersonName extends MinisModel {
    private Long id;
    private String firstname;
    private String lastname;
    private String middleName;
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return firstname + " " + middleName + " " + lastname;
    }

    @Override
    public boolean equals(Object o) {
        return id == ((PersonName)o).getId();
    }
}