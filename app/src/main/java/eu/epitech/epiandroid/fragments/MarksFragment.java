package eu.epitech.epiandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import eu.epitech.epiandroid.R;
import eu.epitech.epiandroid.models.ConnexionModel;
import eu.epitech.epiandroid.models.MarksModel;
import eu.epitech.epiandroid.models.ModulesModel;
import eu.epitech.epiandroid.services.APIService;

/**
 * Created by guitte_a on 20/02/15.
 */
public class MarksFragment extends Fragment {

    public   View           _view;
    private  View           progressView;
    private  View           listView;
    private  ListView       listview;


    private  APIService     api;
    private  ConnexionModel connectModel;

    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    Date currentTime = localCalendar.getTime();

    public JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONArray arr = response.getJSONArray("notes");

                List<MarksModel> events = new LinkedList<MarksModel>();

                JSONObject ObjTMP;

                int i = 0;

                while (i < arr.length())
                {

                    ObjTMP = arr.getJSONObject(i);

                    try {

                        MarksModel ModelTMP = new MarksModel();

                        ModelTMP.setTitleModule(ObjTMP.getString("titlemodule"));
                        ModelTMP.setTitle(ObjTMP.getString("title"));
                        ModelTMP.setNote(ObjTMP.getString("final_note"));
                        ModelTMP.setComment(ObjTMP.getString("comment"));

                        events.add(ModelTMP);
                    }

                    catch (Exception e){}

                    i++;
                }

                ArrayAdapter<MarksModel> adapter = new ArrayAdapter<MarksModel>(_view .getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, events);
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


    public static MarksFragment newInstance(ConnexionModel connectModel) {
        MarksFragment af = new MarksFragment();
        af.connectModel = connectModel;
        return (af);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_marks, container, false);

        listView = _view.findViewById(R.id.listLayout);
        progressView = _view.findViewById(R.id.progressLayout);

        listview = (ListView) _view.findViewById(R.id.listMessage);

        listview.setClickable(true);

        progressView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);

        api = new APIService();
        api.initialize("http://epitech-api.herokuapp.com/");

        try {
            RequestParams requestParams = new RequestParams();
            requestParams.add("token", connectModel.get_token());
            api.getRequest("marks", requestParams, responseHandler);
        } catch (Exception e) {

        }
        return _view;
    }
}