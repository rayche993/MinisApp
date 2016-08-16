package rs.ac.uns.pmf.dmi.minisapp.model;

import java.util.List;

/**
 * Created by rayche on 8/12/16.
 */
public class RestLanguage extends MinisModel{
    private List<Language> content;
    private boolean last;
    private int totalElements;
    private int totalPages;
    private String sort;
    private int numberOfElements;
    private boolean first;
    private int size;
    private int number;

    public List<Language> getContent() {
        return content;
    }

    public void setContent(List<Language> content) {
        this.content = content;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}