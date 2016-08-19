package rs.ac.uns.pmf.dmi.minisapp.model;

/**
 * Created by branko on 19.8.16..
 */
public class Problem extends MinisModel {
    private String message;

    public Problem(String message){
        this.message = message;
    }

    public Problem(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}