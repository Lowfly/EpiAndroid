package eu.epitech.epiandroid.models;

/**
 * Created by guitte_a on 20/02/15.
 */
public class SusieModel {

    private String title;
    private String susie;
    private String date;
    private String id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSusie() {
        return susie;
    }

    public void setSusie(String susie) {
        this.susie = susie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return (this.title + " by " + this.susie + "  at " + this.date);
    }
}

