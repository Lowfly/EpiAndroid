package eu.epitech.epiandroid.models;

import android.text.Html;

/**
 * Created by guitte_a on 20/02/15.
 */
public class PlanningModel {

    private String title;
    private String module;
    private String date;
    private String validate;
    private String needToken;
    private String sessionToken;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    private String scolaryear;
    private String codemodule;
    private String codeinstance;
    private String codeacti;
    private String codeevent;
    private String tokenvalidationcode;

    public String getNeedToken() {
        return needToken;
    }

    public void setNeedToken(String needToken) {
        this.needToken = needToken;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTokenvalidationcode() {
        return tokenvalidationcode;
    }

    public void setTokenvalidationcode(String tokenvalidationcode) {
        this.tokenvalidationcode = tokenvalidationcode;
    }

    public String getCodeevent() {
        return codeevent;
    }

    public void setCodeevent(String codeevent) {
        this.codeevent = codeevent;
    }

    public String getCodeacti() {
        return codeacti;
    }

    public void setCodeacti(String codeacti) {
        this.codeacti = codeacti;
    }

    public String getCodeinstance() {
        return codeinstance;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
    }

    public String getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return (this.module + " : " + this.title + "  " + this.date);
    }
}

