package eu.epitech.epiandroid.models;

import org.json.JSONObject;

/**
 * Created by guitte_a on 19/02/15.
 */
public class UserModel {

    private String  _token;
    private String  _login;
    private String  _title;
    private String  _img_profil_path;
    private String  _promo;
    private String  _semester;
    private String  _location;
    private String  _log_min;
    private String  _log_max;
    private String  _current_log;

    public UserModel() {
        this._token = "";
        this._login = "";
        this._log_min = "";
        this._log_max = "";
        this._current_log = "";
        this._img_profil_path = "";
        this._title = "";
        this._promo = "";
        this._semester = "";
        this._location = "";
    }

    public UserModel(JSONObject result, String token) {

        try {
            JSONObject current = result.getJSONObject("current");
            JSONObject infos = result.getJSONObject("infos");

            this._token = token;
            this._login = infos.getString("login");
            this._img_profil_path = "https://cdn.local.epitech.eu/userprofil/profilview/" + this._login + ".jpg";
            this._title = infos.getString("title");
            this._promo = infos.getString("promo");
            this._semester = infos.getString("semester");
            this._location = infos.getString("location");
            this._log_min = current.getString("nslog_min");
            this._log_max = current.getString("nslog_norm");
            this._current_log = current.getString("active_log");

        }
        catch (Exception e) {
        }
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_promo() {
        return _promo;
    }

    public void set_promo(String _promo) {
        this._promo = _promo;
    }

    public String get_semester() {
        return _semester;
    }

    public void set_semester(String _semester) {
        this._semester = _semester;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_token() {
        return _token;
    }

    public void set_token(String _token) {
        this._token = _token;
    }

    public String get_login() {
        return _login;
    }

    public void set_login(String _login) {
        this._login = _login;
    }

    public String get_log_max() {
        return _log_max;
    }

    public void set_log_max(String _log_max) {
        this._log_max = _log_max;
    }

    public String get_log_min() {
        return _log_min;
    }

    public void set_log_min(String _log_min) {
        this._log_min = _log_min;
    }

    public String get_current_log() {
        return _current_log;
    }

    public void set_current_log(String _current_log) {
        this._current_log = _current_log;
    }

    public String get_img_profil_path() {
        return _img_profil_path;
    }

    public void set_img_profil_path(String _img_profil_path) {
        this._img_profil_path = _img_profil_path;
    }
}
