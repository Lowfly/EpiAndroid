package eu.epitech.epiandroid.fragments;

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
import android.widget.Button;
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
public class MessageFragment extends Fragment {


    public   View       _view;

    private  TextView   title;
    private  TextView   logTime;
    private  TextView   logTime2;
    private Button      back;
    private  MessagesModel message;
    private FragmentManager manager;

    public static MessageFragment newInstance(MessagesModel messageModel, FragmentManager fragmentManager) {
        MessageFragment af = new MessageFragment();
        af.manager = fragmentManager;
        af.message = messageModel;
        return (af);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_message, container, false);
        back = (Button) _view.findViewById(R.id.button4);
        title = (TextView) _view.findViewById(R.id.title);
        logTime = (TextView) _view.findViewById(R.id.logTime);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                manager.popBackStack();
            }
        });

        return _view;
    }
}
