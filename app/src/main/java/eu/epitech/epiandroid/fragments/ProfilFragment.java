package eu.epitech.epiandroid.fragments;

import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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
public class ProfilFragment extends Fragment {


    public   View       _view;
    private  View       infosView;
    public   View       progressView;
    private  TextView   title;
    private  TextView   logTime;

    public  ListView listview;
    private  ImageView  profilPicture;
    private  Bitmap     bitmap;

    FragmentManager manager;


    private APIService api;
    private ConnexionModel connectModel;

    public JsonHttpResponseHandler responseHandlerInfos = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                UserModel userModel = new UserModel(response, connectModel.get_token());
                bitmap = new ImgDownloaderService().execute(userModel.get_img_profil_path()).get();
                title.setText(userModel.get_title());
                progressView.setVisibility(View.INVISIBLE);
                infosView.setVisibility(View.VISIBLE);
                profilPicture.setImageBitmap(bitmap);

                logTime.setText("Temps de log : " + userModel.get_current_log());
            }
            catch (Exception e) {
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    public JsonHttpResponseHandler responseHandlerMessages = new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            super.onSuccess(statusCode, headers, response);
            try
            {
                String line;
                List<MessagesModel> messages = new LinkedList<MessagesModel>();
                JSONObject ObjTMP;
                JSONObject ObjTMPUser;

                int i = 0;
                while (i < response.length())
                {
                    MessagesModel ModelTMP = new MessagesModel();

                    ObjTMP = response.getJSONObject(i);
                    ObjTMPUser = ObjTMP.getJSONObject("user");

                    ModelTMP.setTitle(ObjTMP.getString("title"));
                    ModelTMP.setContent(ObjTMP.getString("content"));
                    ModelTMP.setDate(ObjTMP.getString("date"));
                    ModelTMP.setDate(ObjTMPUser.getString("title"));

                    messages.add(ModelTMP);
                    i++;
                }

                ArrayAdapter<MessagesModel> adapter = new ArrayAdapter<MessagesModel>(_view .getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, messages);
                listview.setAdapter(adapter);

            }
            catch (Exception e) {
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
        }
    };

    public static ProfilFragment newInstance(ConnexionModel connectModel, FragmentManager fragmentManager) {
        ProfilFragment af = new ProfilFragment();
        af.connectModel = connectModel;
        af.manager = fragmentManager;
        return (af);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_home, container, false);

        infosView = _view.findViewById(R.id.infosLayout);
        progressView = _view.findViewById(R.id.progressLayout);

        profilPicture = (ImageView) _view.findViewById(R.id.profilpicture);
        title = (TextView) _view.findViewById(R.id.title);
        logTime = (TextView) _view.findViewById(R.id.logTime);
        listview = (ListView) infosView.findViewById(R.id.listMessage);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = listview.getItemAtPosition(position);

                Fragment fragment =  null;
                fragment = MessageFragment.newInstance((MessagesModel) o, manager);
                manager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        infosView.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.VISIBLE);

        api = new APIService();
        api.initialize(getString(R.string.urlAPI));
        try {
            RequestParams requestParams = new RequestParams();
            requestParams.add("token", connectModel.get_token());
            api.postRequest("infos", requestParams, responseHandlerInfos);
        } catch (Exception e) {
        }
        APIService MessageApi = new APIService();
        APIService.initialize("http://epitech-api.herokuapp.com/");
        try {
            RequestParams requestParamsMessages = new RequestParams();
            requestParamsMessages.put("token", connectModel.get_token());
            MessageApi.getRequest("messages", requestParamsMessages, responseHandlerMessages);
        } catch (Exception e) {
        }
        return _view;
    }



}