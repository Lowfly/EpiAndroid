package eu.epitech.epiandroid.models;

/**
 * Created by guitte_a on 20/02/15.
 */
public class MarksModel {

    String titleModule;
    String title;
    String note;
    String comment;

    public String getTitleModule() {
        return titleModule;
    }

    public void setTitleModule(String titleModule) {
        this.titleModule = titleModule;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return(this.titleModule + "/" + this.title + "   Note : " + this.note);
    }
}

