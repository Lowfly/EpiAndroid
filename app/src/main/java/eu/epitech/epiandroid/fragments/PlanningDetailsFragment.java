package eu.epitech.epiandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import eu.epitech.epiandroid.R;
import eu.epitech.epiandroid.models.MessagesModel;
import eu.epitech.epiandroid.models.PlanningModel;
import eu.epitech.epiandroid.services.APIService;

/**
 * Created by guitte_a on 20/02/15.
 */
public class PlanningDetailsFragment extends Fragment {


    public   View               _view;
    public   View               infosView;
    public   View               progressView;
    public   View               tokenView;

    private  TextView           title;
    private  TextView           module;
    private  TextView           date;
    private  TextView           validation;

    private  Button             back;
    private  Button             validate;

    private EditText            fieldToken;


    private  PlanningModel      event;
    private  FragmentManager    manager;

    private APIService          api;

    private String              error;

    public JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                progressView.setVisibility(View.INVISIBLE);
                infosView.setVisibility(View.VISIBLE);
                tokenView.setVisibility(View.VISIBLE);
                try {
                    error = response.getString("error");
                    Toast.makeText(_view.getContext(), "Erreur dans l'enregistrement du token", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(_view.getContext(), "Présence validée", Toast.LENGTH_LONG).show();
                }
        }catch (Exception e){}


    }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            try {
                progressView.setVisibility(View.INVISIBLE);
                infosView.setVisibility(View.VISIBLE);
                tokenView.setVisibility(View.VISIBLE);
                Toast.makeText(_view.getContext(), "Erreur dans l'enregistrement du token", Toast.LENGTH_LONG).show();
            }catch (Exception e){}
            }
    };

    public static PlanningDetailsFragment newInstance(PlanningModel planningModel, FragmentManager fragmentManager) {
        PlanningDetailsFragment af = new PlanningDetailsFragment();
        af.manager = fragmentManager;
        af.event =  planningModel;
        return (af);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        _view = inflater.inflate(R.layout.fragment_planningsdetails, container, false);
        infosView = _view.findViewById(R.id.infosLayout);
        progressView = _view.findViewById(R.id.progressLayout);
        tokenView = _view.findViewById(R.id.tokenLayout);

        title = (TextView) _view.findViewById(R.id.title);
        module = (TextView) _view.findViewById(R.id.module);
        date = (TextView) _view.findViewById(R.id.date);
        validation = (TextView) _view.findViewById(R.id.validation);


        validate = (Button) _view.findViewById(R.id.button5);
        back = (Button) _view.findViewById(R.id.back);

        fieldToken = (EditText) _view.findViewById(R.id.tokenInput);

        progressView.setVisibility(View.INVISIBLE);
        infosView.setVisibility(View.VISIBLE);
        tokenView.setVisibility(View.INVISIBLE);

        title.setText(event.getTitle());
        module.setText(event.getModule());
        date.setText(event.getDate());


        if (event.getNeedToken() == "true" && event.getValidate() == "registered") {
            tokenView.setVisibility(View.VISIBLE);
        }
        if (event.getValidate() != "null") {
                validation.setText("Présence : " + event.getValidate());
            }
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                manager.popBackStack();
            }
        });

        fieldToken.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    infosView.setVisibility(View.INVISIBLE);
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               event.setTokenvalidationcode(fieldToken.getText().toString());

                api = new APIService();
                api.initialize(getString(R.string.urlAPI));

                try {
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("token", event.getSessionToken());
                    requestParams.add("scolaryear", event.getScolaryear());
                    requestParams.add("codemodule",event.getCodemodule());
                    requestParams.add("codeinstance",event.getCodeinstance());
                    requestParams.add("codeacti",event.getCodeacti());
                    requestParams.add("codeevent",event.getCodeevent());
                    requestParams.add("tokenvalidationcode",event.getTokenvalidationcode());
                    api.getRequest("token", requestParams, responseHandler);
                    progressView.setVisibility(View.VISIBLE);
                    infosView.setVisibility(View.INVISIBLE);
                    tokenView.setVisibility(View.INVISIBLE);
                }

                catch (Exception e) {

                }
            }
        });

        return _view;
    }
}
