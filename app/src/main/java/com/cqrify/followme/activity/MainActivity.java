package com.cqrify.followme.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.cqrify.followme.R;
import com.cqrify.followme.fragment.ContactsFragment;
import com.cqrify.followme.fragment.ContactListFragment;
import com.cqrify.followme.fragment.FollowMeFragment;
import com.cqrify.followme.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Setup navbar
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_contacts:
                    Log.d(LOG_TAG, "Navigate contacts");
                    selectedFragment = ContactsFragment.newInstance();
                    break;
                case R.id.navigation_followme:
                    selectedFragment = FollowMeFragment.newInstance();
                    break;
                case R.id.navigation_settings:
                    Log.d(LOG_TAG, "Navigate settings");
                    selectedFragment = SettingsFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup navigations bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_contacts);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // set selected fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ContactListFragment.newInstance());
        transaction.commit();
    }

}
