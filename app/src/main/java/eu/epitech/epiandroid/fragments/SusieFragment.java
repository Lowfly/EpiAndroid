package eu.epitech.epiandroid.fragments;

import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import eu.epitech.epiandroid.models.PlanningModel;
import eu.epitech.epiandroid.models.SusieModel;
import eu.epitech.epiandroid.services.APIService;

/**
 * Created by guitte_a on 20/02/15.
 */
public class SusieFragment extends Fragment {

    public   View       _view;
    private  View       headerView;
    private  View       progressView;
    private  View       listView;
    private  ListView   listview;
    private Button      next;
    private Button      prev;

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

                List<SusieModel> events = new LinkedList<SusieModel>();

                JSONObject ObjTMP;
                JSONObject ObjTMP2;

                int i = 0;

                while (i < response.length())
                {

                    ObjTMP = response.getJSONObject(i);
                    ObjTMP2 = ObjTMP.getJSONObject("maker");

                    SusieModel ModelTMP = new SusieModel();

                    ModelTMP.setTitle(ObjTMP.getString("title"));
                    ModelTMP.setDate(ObjTMP.getString("start"));
                    ModelTMP.setSusie(ObjTMP2.getString("title"));

                    events.add(ModelTMP);

                    i++;
                }

                ArrayAdapter<SusieModel> adapter = new ArrayAdapter<SusieModel>(_view .getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, events);
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


    public static SusieFragment newInstance(ConnexionModel connectModel) {
        SusieFragment af = new SusieFragment();
        af.connectModel = connectModel;
        return (af);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_planning, container, false);

        listView = _view.findViewById(R.id.listLayout);
        progressView = _view.findViewById(R.id.progressLayout);
        headerView = _view.findViewById(R.id.headerLayout);

        listview = (ListView) _view.findViewById(R.id.listMessage);
        interval = (TextView) _view.findViewById(R.id.textInterval);

        listview.setClickable(true);

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

                api = new APIService();
                api.initialize("http://epitech-api.herokuapp.com/");
                try {
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("token", connectModel.get_token());
                    requestParams.add("start", date_begin);
                    requestParams.add("end", date_end);
                    api.getRequest("susies", requestParams, responseHandler);
                } catch (Exception e) {

                }
            }
        });
        next = (Button)_view.findViewById(R.id.button3);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                localCalendar.add(Calendar.DATE, 1);
                date_begin = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
                localCalendar.add(Calendar.DATE, 6);
                date_end = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());

                progressView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);

                api = new APIService();
                api.initialize("http://epitech-api.herokuapp.com/");
                try {
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("token", connectModel.get_token());
                    requestParams.add("start", date_begin);
                    requestParams.add("end", date_end);
                    api.getRequest("susies", requestParams, responseHandler);
                } catch (Exception e) {

                }
            }
        });
        headerView.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);

        api = new APIService();
        api.initialize("http://epitech-api.herokuapp.com/");
        try {
            RequestParams requestParams = new RequestParams();
            requestParams.add("token", connectModel.get_token());
            requestParams.add("start", date_begin);
            requestParams.add("end", date_end);
            api.getRequest("susies", requestParams, responseHandler);
        } catch (Exception e) {

        }
        return _view;
    }
}