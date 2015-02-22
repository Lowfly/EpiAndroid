package eu.epitech.epiandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import eu.epitech.epiandroid.R;
import eu.epitech.epiandroid.models.MessagesModel;
import eu.epitech.epiandroid.models.PlanningModel;

/**
 * Created by guitte_a on 20/02/15.
 */
public class PlanningDetailsFragment extends Fragment {


    public   View       _view;

    private  TextView       user;
    private  TextView       date;
    private  TextView       content;
    private  Button         back;
    private  PlanningModel  event;
    private  FragmentManager manager;

    public static PlanningDetailsFragment newInstance(PlanningModel planningModel, FragmentManager fragmentManager) {
        PlanningDetailsFragment af = new PlanningDetailsFragment();
        af.manager = fragmentManager;
        af.event =  planningModel;
        return (af);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.fragment_planningsdetails, container, false);
        back = (Button) _view.findViewById(R.id.button4);

//        content.setMovementMethod(new ScrollingMovementMethod());

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                manager.popBackStack();
            }
        });

        return _view;
    }
}
