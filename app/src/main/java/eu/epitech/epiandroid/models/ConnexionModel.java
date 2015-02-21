package eu.epitech.epiandroid.models;

import org.json.JSONObject;

/**
 * Created by guitte_a on 20/02/15.
 */

public class ConnexionModel {

    private String  _token;
    private String  _login;
    private String  _password;

    public ConnexionModel() {
        this._token = "";
        this._login = "";
        this._password = "";
    }

    public ConnexionModel(JSONObject result, String mLogin, String mPassword) {
        try {
            this._token = result.getString("token");
            this._login = mLogin;
            this._password = mPassword;
        }
        catch (Exception e) {
        }
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

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
