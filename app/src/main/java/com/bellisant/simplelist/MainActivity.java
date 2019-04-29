package com.bellisant.simplelist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager
                .findFragmentById(R.id.place_fragment_here);

        if (fragment == null) {
            fragment = new MasterFragment();
            supportFragmentManager.beginTransaction()
                    .add(R.id.place_fragment_here, fragment)
                    .commit();
        } else {

        }
    }
}
