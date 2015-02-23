package eu.epitech.epiandroid.models;

import android.text.Html;

/**
 * Created by guitte_a on 20/02/15.
 */
public class ModulesModel {

    String title;
    String grade;
    String credit;


    String code;
    String semester;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        if (grade == null) {
            return (this.title + "   " + "  Credits : " + this.credit);
        }
        return (this.title + "  Grade : " + this.grade + "  Credits : " + this.credit);
    }
}

