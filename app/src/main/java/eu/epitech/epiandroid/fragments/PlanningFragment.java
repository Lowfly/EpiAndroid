package eu.epitech.epiandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import eu.epitech.epiandroid.R;
import eu.epitech.epiandroid.models.ConnexionModel;
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
    private  APIService api;
    private  ConnexionModel connectModel;
    private  String     date_begin;
    private  String     date_end;


    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    Date currentTime = localCalendar.getTime();

    public JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
              Log.d("JSOn", response.toString());
            }
            catch (Exception e) {
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };


    public static PlanningFragment newInstance(ConnexionModel connectModel) {
        PlanningFragment af = new PlanningFragment();
        af.connectModel = connectModel;
        return (af);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_planning, container, false);

        listView = _view.findViewById(R.id.listLayout);
        progressView = _view.findViewById(R.id.progressLayout);
        headerView = _view.findViewById(R.id.headerLayout);

        localCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date_begin = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
        localCalendar.add(Calendar.DATE, 6);
        date_end = new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());

        //infosView.setVisibility(View.VISIBLE);
        //progressView.setVisibility(View.VISIBLE);

        api = new APIService();
        api.initialize("http://epitech-api.herokuapp.com/");
        Log.d("planning", "planning");

        try {
            RequestParams requestParams = new RequestParams();
            requestParams.add("token", connectModel.get_token());
            requestParams.add("start", date_begin);
            requestParams.add("end", date_end);
            api.getRequest("planning", requestParams, responseHandler);
        } catch (Exception e) {
            Log.d("error", "error");

        }
        return _view;
    }
}