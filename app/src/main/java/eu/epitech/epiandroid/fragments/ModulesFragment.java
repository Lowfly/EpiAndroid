package eu.epitech.epiandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import eu.epitech.epiandroid.R;
import eu.epitech.epiandroid.models.ConnexionModel;
import eu.epitech.epiandroid.models.MessagesModel;
import eu.epitech.epiandroid.models.ModulesModel;
import eu.epitech.epiandroid.models.PlanningModel;
import eu.epitech.epiandroid.models.UserModel;
import eu.epitech.epiandroid.services.APIService;
import eu.epitech.epiandroid.services.ImgDownloaderService;

/**
 * Created by guitte_a on 20/02/15.
 */
public class ModulesFragment extends Fragment {

    public   View           _view;
    private  View           progressView;
    private  View           listView;
    private  ListView       listview;
    private  RadioButton    subscribe;
    private  RadioButton    all;
    private  UserModel      userModel;


    private  APIService     api;
    private  ConnexionModel connectModel;

    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    Date currentTime = localCalendar.getTime();

    public JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONArray arr = response.getJSONArray("modules");

                List<ModulesModel> events = new LinkedList<ModulesModel>();

                JSONObject ObjTMP;

                int i = 0;

                while (i < arr.length())
                {

                    ObjTMP = arr.getJSONObject(i);

                    try {

                        ModulesModel ModelTMP = new ModulesModel();

                        ModelTMP.setTitle(ObjTMP.getString("title"));
                        ModelTMP.setGrade(ObjTMP.getString("grade"));
                        ModelTMP.setCredit(ObjTMP.getString("credits"));
                        ModelTMP.setCode(ObjTMP.getString("codemodule"));
                        ModelTMP.setSemester(ObjTMP.getString("semester"));

                        events.add(ModelTMP);
                    }

                    catch (Exception e){}

                    i++;
                }

                ArrayAdapter<ModulesModel> adapter = new ArrayAdapter<ModulesModel>(_view .getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, events);
                listview.setAdapter(adapter);
                progressView.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
            }
            catch (Exception e) {
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    public JsonHttpResponseHandler responseHandlerAll = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {


                JSONArray arr = response.getJSONArray("items");


                List<ModulesModel> events = new LinkedList<ModulesModel>();

                JSONObject ObjTMP;

                int i = 0;

                while (i < arr.length())
                {

                    ObjTMP = arr.getJSONObject(i);

                    try {

                        ModulesModel ModelTMP = new ModulesModel();

                        ModelTMP.setTitle(ObjTMP.getString("title"));
                        ModelTMP.setCredit(ObjTMP.getString("credits"));
                        ModelTMP.setSemester(ObjTMP.getString("semester"));

                        events.add(ModelTMP);
                    }

                    catch (Exception e){}

                    i++;
                }

                ArrayAdapter<ModulesModel> adapter = new ArrayAdapter<ModulesModel>(_view .getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, events);
                listview.setAdapter(adapter);
                progressView.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
            }
            catch (Exception e) {
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    public static ModulesFragment newInstance(ConnexionModel connectModel) {
        ModulesFragment af = new ModulesFragment();
        af.connectModel = connectModel;

        return (af);
    }

    public void getAllModule()
    {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_modules, container, false);

        listView = _view.findViewById(R.id.listLayout);
        progressView = _view.findViewById(R.id.progressLayout);

        listview = (ListView) _view.findViewById(R.id.listMessage);
        all = (RadioButton) _view.findViewById(R.id.radioButton);
        subscribe= (RadioButton) _view.findViewById(R.id.radioButton2);

        listview.setClickable(true);

        subscribe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                all.setChecked(false);
                progressView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                api = new APIService();
                api.initialize(getString(R.string.urlAPI));

                try {
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("token", connectModel.get_token());
                    api.getRequest("modules", requestParams, responseHandler);
                } catch (Exception e) {

                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subscribe.setChecked(false);
                progressView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                api = new APIService();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                api.initialize("http://epitech-api.herokuapp.com/");

                try {
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("token", connectModel.get_token());
                    requestParams.add("scolaryear", Integer.toString(year));
                    requestParams.add("location", "FR/REN");
                    requestParams.add("course", "bachelor/classic");

                    api.getRequest("allmodules", requestParams, responseHandlerAll);
                } catch (Exception e) {

                }
            }
        });

        subscribe.callOnClick();
        return _view;
    }
}