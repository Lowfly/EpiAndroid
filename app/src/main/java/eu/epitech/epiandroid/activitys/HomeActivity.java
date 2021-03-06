package eu.epitech.epiandroid.activitys;

import android.app.Activity;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import eu.epitech.epiandroid.R;
import eu.epitech.epiandroid.fragments.MarksFragment;
import eu.epitech.epiandroid.fragments.ModulesFragment;
import eu.epitech.epiandroid.fragments.ProfilFragment;
import eu.epitech.epiandroid.fragments.PlanningFragment;
import eu.epitech.epiandroid.fragments.SusieFragment;
import eu.epitech.epiandroid.models.ConnexionModel;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    public  ConnexionModel connectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Bundle bundle = getIntent().getExtras();
        connectModel = new ConnexionModel();

        if(bundle.getString("token") != null)
        {
            connectModel.set_token(bundle.getString("token"));
        }
        if(bundle.getString("login") != null)
        {
            connectModel.set_login(bundle.getString("login"));
        }



    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment =  null;

        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                fragment = ProfilFragment.newInstance(connectModel, fragmentManager);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                fragment = PlanningFragment.newInstance(connectModel, fragmentManager);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                fragment = ModulesFragment.newInstance(connectModel);
                break;
            case 4:
                mTitle = getString(R.string.title_section6);
                fragment = MarksFragment.newInstance(connectModel);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                fragment = SusieFragment.newInstance(connectModel);
                break;
            case 6:
                mTitle = getString(R.string.title_section5);
                this.finish();
                break;
        }

        if (fragment != null)
        {fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment)
                .commit();}
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onBackPressed() {
    }

}
