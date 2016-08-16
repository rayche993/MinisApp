package rs.ac.uns.pmf.dmi.minisapp.model;

/**
 * Created by rayche on 8/12/16.
 */
public class StatusType extends MinisModel {
    private Long id;
    private Integer orderNum;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}