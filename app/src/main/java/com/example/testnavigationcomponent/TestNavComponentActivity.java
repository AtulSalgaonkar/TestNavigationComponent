package com.example.testnavigationcomponent;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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

    NavController mNavController;
    private static final String TAG = "MainActivityLogData";
    private boolean isBackPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nav_component);
        ButterKnife.bind(this);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

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
}
