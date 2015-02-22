package eu.epitech.epiandroid.activitys;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import eu.epitech.epiandroid.R;
import eu.epitech.epiandroid.models.ConnexionModel;
import eu.epitech.epiandroid.services.APIService;

public class LoginActivity extends ActionBarActivity {

    private  EditText   fieldLogin;
    private  EditText   fieldPassword;
    private  View       ProgressView;
    private  View       LoginFormView;

    private  APIService api;

    public   String     _login;
    public   String     _password;

    public JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Toast.makeText(getBaseContext(), "Connecté !", Toast.LENGTH_LONG).show();
                    LoginFormView.setVisibility(View.VISIBLE);
                    ProgressView.setVisibility(View.INVISIBLE);
                    ConnexionModel connectModel = new ConnexionModel(response, _login, _password);
                    Intent goHome = new Intent(LoginActivity.this, HomeActivity.class);
                    goHome.putExtra("token", connectModel.get_token());
                    goHome.putExtra("login", connectModel.get_login());
                    startActivityForResult(goHome, 1);
                }
                catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getBaseContext(), "Impossible de se connecter!", Toast.LENGTH_LONG).show();
                LoginFormView.setVisibility(View.VISIBLE);
                ProgressView.setVisibility(View.INVISIBLE);
            }
        };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fieldLogin = (EditText) findViewById(R.id.editText);
        fieldPassword = (EditText) findViewById(R.id.editText2);
        LoginFormView = findViewById(R.id.LoginFormView);
        ProgressView = findViewById(R.id.ProgressView);
        LoginFormView.setVisibility(View.VISIBLE);
        ProgressView.setVisibility(View.INVISIBLE);

        api = new APIService();
        api.initialize("http://epitech-api.herokuapp.com/");

        }

    public void onClickButton(View v) {
        String DataLogin = fieldLogin.getText().toString();
        String DataPassword = fieldPassword.getText().toString();

        if (DataLogin == null || DataLogin.isEmpty()) {
        }
        else if (DataPassword == null || DataPassword.isEmpty()) {
        }

        else {
            try {
                RequestParams requestParams = new RequestParams();
                requestParams.add("login", DataLogin);
                requestParams.add("password", DataPassword);
                api.postRequest("login", requestParams, responseHandler);

                LoginFormView.setVisibility(View.INVISIBLE);
                ProgressView.setVisibility(View.VISIBLE);

                _login = DataLogin;
                _password = DataPassword;
            } catch (Exception e) {
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {

    }

}
