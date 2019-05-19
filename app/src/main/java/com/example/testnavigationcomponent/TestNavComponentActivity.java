package com.example.testnavigationcomponent;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.testnavigationcomponent.fragments.OnActivityInteractionToFragmentListener;
import com.example.testnavigationcomponent.fragments.OnFragmentInteractionListener;
import com.example.testnavigationcomponent.utils.ConstantsUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestNavComponentActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.base_view)
    CoordinatorLayout mBaseView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    NavController mNavController;
    private static final String TAG = "MainActivityLogData";
    private boolean isBackPressed = false;

    private Context mContext;
    private OnActivityInteractionToFragmentListener mListener;

    @Override
    protected void onPause() {
        super.onPause();
        //mListener = null;
    }

    public void onButtonPressed(String data) {
        if (mListener != null) {
            mListener.onActivityInteraction(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nav_component);
        mContext = TestNavComponentActivity.this;

        ButterKnife.bind(this);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(mToolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.one_menu:
                        if (!isBackPressed) {
                            if (!isCurrentSelected(R.id.fragmentOne)) {
                                mNavController.navigate(R.id.fragmentOne);
                            }
                        } else {
                            isBackPressed = false;
                            Log.d(TAG, "onNavigationItemSelected onBackPressed: fragmentOne");
                        }
                        break;
                    case R.id.two_menu:
                        if (!isBackPressed) {
                            if (!isCurrentSelected(R.id.fragmentTwo)) {
                                mNavController.navigate(R.id.fragmentTwo);
                            }
                        } else {
                            isBackPressed = false;
                            Log.d(TAG, "onNavigationItemSelected onBackPressed: fragmentTwo");
                        }
                        break;
                    case R.id.three_menu:
                        if (!isBackPressed) {
                            if (!isCurrentSelected(R.id.fragmentThree)) {
                                mNavController.navigate(R.id.fragmentThree);
                            }
                        } else {
                            isBackPressed = false;
                            Log.d(TAG, "onNavigationItemSelected onBackPressed: fragmentThree");
                        }
                        break;
                    default:
                        //mNavController.navigate(R.id.fragmentOne);
                        break;
                }
                return true;
            }
        });


    }

    private boolean isCurrentSelected(int fragmentSelected) {
        NavDestination navDestination = mNavController.getCurrentDestination();
        if (navDestination != null) {
            return navDestination.getId() == fragmentSelected;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        detectAndSetBottomNavState();

    }

    private void detectAndSetBottomNavState() {
        NavDestination navDestination = mNavController.getCurrentDestination();

        if (navDestination != null) {
            switch (navDestination.getId()) {
                case R.id.fragmentOne:
                    Log.d(TAG, "onBackPressed: fragmentOne");
                    isBackPressed = true;
                    bottomNavigationView.setSelectedItemId(R.id.one_menu);
                    break;
                case R.id.fragmentTwo:
                    Log.d(TAG, "onBackPressed: fragmentTwo");
                    isBackPressed = true;
                    bottomNavigationView.setSelectedItemId(R.id.two_menu);
                    break;
                case R.id.fragmentThree:
                    Log.d(TAG, "onBackPressed: fragmentThree");
                    isBackPressed = true;
                    bottomNavigationView.setSelectedItemId(R.id.three_menu);
                    break;
                default:
                    Log.d(TAG, "onBackPressed: default");
                    break;
            }
        } else {
            Log.d(TAG, "onBackPressed: null");
        }
    }

    @Override
    public void onFragmentInteraction(Bundle data) {
        if (data != null) {
            String dataStr = data.getString(ConstantsUtils.data, "Not Found");
            boolean isFromNext = data.getBoolean(ConstantsUtils.isFromNext, false);
            Snackbar.make(mBaseView, dataStr, Snackbar.LENGTH_SHORT).show();
            if (isFromNext)
                detectAndSetBottomNavState();
        }
    }

    @Override
    public void onFragmentStarted(Fragment context, Bundle data) {
        if (context instanceof OnActivityInteractionToFragmentListener) {
            mListener = (OnActivityInteractionToFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "'s fragments must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onFragmentStop(Fragment context, Bundle data) {
        mListener = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_to_fragment_menu:
                Bundle bundle = new Bundle();
                bundle.putString("This is Data from Activity", ConstantsUtils.dataFromActivity);
                onButtonPressed("This is Data from Activity");
                return true;
        }

        return true;
    }
}
