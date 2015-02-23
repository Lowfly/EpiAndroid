package eu.epitech.epiandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import eu.epitech.epiandroid.models.PlanningModel;
import eu.epitech.epiandroid.models.UserModel;
import eu.epitech.epiandroid.services.APIService;
import eu.epitech.epiandroid.services.ImgDownloaderService;

/**
 * Created by guitte_a on 20/02/15.
 */
public class PlanningFragment extends Fragment {

    public   View       _view;
    private  View       headerView;
    private  View       progressView;
    private  View       listView;
    private  ListView   listview;
    private Button      next;
    private Button      prev;
    private  RadioButton    subscribe;
    private  RadioButton    all;
    FragmentManager manager;
    private boolean subscribed = false;

    private  APIService api;
    private  ConnexionModel connectModel;
    private  String     date_begin;
    private  String     date_end;
    private  TextView     interval;

    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    Date currentTime = localCalendar.getTime();

    public JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            try {

                List<PlanningModel> events = new LinkedList<PlanningModel>();

                JSONObject ObjTMP;

                int i = 0;

                while (i < response.length())
                {

                    ObjTMP = response.getJSONObject(i);

                    try {
                        if (subscribed == true) {
                            if (ObjTMP.getString("module_registered") == "true") {
                                PlanningModel ModelTMP = new PlanningModel();

                                ModelTMP.setTitle(ObjTMP.getString("acti_title"));
                                ModelTMP.setModule(ObjTMP.getString("titlemodule"));
                                ModelTMP.setDate(ObjTMP.getString("start"));
                                ModelTMP.setValidate(ObjTMP.getString("event_registered"));
                                ModelTMP.setNeedToken(ObjTMP.getString("allow_token"));
                                ModelTMP.setScolaryear(ObjTMP.getString("scolaryear"));
                                ModelTMP.setCodemodule(ObjTMP.getString("codemodule"));
                                ModelTMP.setCodeinstance(ObjTMP.getString("codeinstance"));
                                ModelTMP.setCodeacti(ObjTMP.getString("codeacti"));
                                ModelTMP.setCodeevent(ObjTMP.getString("codeevent"));
                                ModelTMP.setSessionToken(connectModel.get_token());
                                events.add(ModelTMP);
                            }
                        }
                        else {
                            PlanningModel ModelTMP = new PlanningModel();

                            ModelTMP.setTitle(ObjTMP.getString("acti_title"));
                            ModelTMP.setModule(ObjTMP.getString("titlemodule"));
                            ModelTMP.setDate(ObjTMP.getString("start"));
                            ModelTMP.setValidate(ObjTMP.getString("event_registered"));
                            ModelTMP.setNeedToken(ObjTMP.getString("allow_token"));
                            ModelTMP.setScolaryear(ObjTMP.getString("scolaryear"));
                            ModelTMP.setCodemodule(ObjTMP.getString("codemodule"));
                            ModelTMP.setCodeinstance(ObjTMP.getString("codeinstance"));
                            ModelTMP.setCodeacti(ObjTMP.getString("codeacti"));
                            ModelTMP.setCodeevent(ObjTMP.getString("codeevent"));
                            ModelTMP.setSessionToken(connectModel.get_token());
                            events.add(ModelTMP);

                        }
                    }
                    catch (Exception e){}

                    i++;
                }


                ArrayAdapter<PlanningModel> adapter = new ArrayAdapter<PlanningModel>(_view .getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, events);
                listview.setAdapter(adapter);

                interval.setText(date_begin + "  " + date_end);
                headerView.setVisibility(View.VISIBLE);
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


    public static PlanningFragment newInstance(ConnexionModel connectModel, FragmentManager fragmentManager) {
        PlanningFragment af = new PlanningFragment();
        af.connectModel = connectModel;
        af.manager = fragmentManager;
        return (af);
    }

    public void request()
    {
        progressView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);

        api = new APIService();
        api.initialize(getString(R.string.urlAPI));

        try {
            RequestParams requestParams = new RequestParams();
            requestParams.add("token", connectModel.get_token());
            requestParams.add("start", date_begin);
            requestParams.add("end", date_end);
            api.getRequest("planning", requestParams, responseHandler);
        } catch (Exception e) {

        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_planning, container, false);

        listView = _view.findViewById(R.id.listLayout);
        progressView = _view.findViewById(R.id.progressLayout);
        headerView = _view.findViewById(R.id.headerLayout);

        listview = (ListView) _view.findViewById(R.id.listMessage);
        interval = (TextView) _view.findViewById(R.id.textInterval);

        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = listview.getItemAtPosition(position);

                Fragment fragment =  null;
                fragment = PlanningDetailsFragment.newInstance((PlanningModel) o, manager);
                manager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date_begin = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
        localCalendar.add(Calendar.DATE, 6);
        date_end = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());

        prev = (Button)_view.findViewById(R.id.button2);
        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localCalendar.add(Calendar.DATE, -13);
                date_begin = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
                localCalendar.add(Calendar.DATE, 6);
                date_end = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());

                progressView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                request();

            }
        });
        next = (Button)_view.findViewById(R.id.button3);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localCalendar.add(Calendar.DATE, 1);
                date_begin = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
                localCalendar.add(Calendar.DATE, 6);
                date_end = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
                request();
            }
        });

        all = (RadioButton) _view.findViewById(R.id.radioButton4);
        subscribe= (RadioButton) _view.findViewById(R.id.radioButton3);

        all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subscribe.setChecked(false);
                subscribed = false;
                request();
            }
        });
        subscribe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                all.setChecked(false);
                subscribed = true;
                request();
            }
        });
        headerView.setVisibility(View.VISIBLE);
        request();
        return _view;
    }
}