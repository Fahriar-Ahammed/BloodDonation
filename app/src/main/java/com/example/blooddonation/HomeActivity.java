package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.Auth.LoginActivity;
import com.example.blooddonation.MainFragments.fragFindBloodDonor;
import com.example.blooddonation.MainFragments.fragFindHospitals;
import com.example.blooddonation.MainFragments.fragHome;
import com.example.blooddonation.MainFragments.fragProfile;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Cannot invoke method length() on null object";

    public DrawerLayout drawer;
    public NavigationView navigation;
    public Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    public ExtendedFloatingActionButton btnFloatingActionButton;
    String token, name, number;
    // Change from github

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        name = sharedPreferences.getString("name", "");
        number = sharedPreferences.getString("number", "");

        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        btnFloatingActionButton = findViewById(R.id.btnFloatingButton);


        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new fragHome()).commit();
        navigation.setCheckedItem(R.id.nav_home);

        navigationListner();

        headerText();

    }


    public void navigationListner() {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new fragHome()).commit();
                        Log.d(TAG, "onNavigationItemSelected: @@@@@@@@@@@@@@@                   Home Fragment Clicked");
                        break;
                    case R.id.nav_findBloodDonor:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new fragFindBloodDonor()).commit();
                        Log.d(TAG, "onNavigationItemSelected: @@@@@@@@@@@@@@@                   Find Blood Donor Fragment Clicked");
                        break;
                    case R.id.nav_findHospital:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new fragFindHospitals()).commit();
                        Log.d(TAG, "onNavigationItemSelected: @@@@@@@@@@@@@@@                   Find Hospitals Fragment Clicked");
                        break;
                    case R.id.nav_profile:

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new fragProfile()).commit();
                        Log.d(TAG, "onNavigationItemSelected: @@@@@@@@@@@@@@@@@@@@@                            Profile Fragment Clicked");

//                        Snackbar bar = Snackbar.make(findViewById(R.id.drawer), " ", Snackbar.LENGTH_LONG);
//                        bar.setText("Profile Coming Soon");
//                        bar.show();

                        //Toast.makeText(HomeActivity.this, "Profile Will update soon", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:

                        SharedPreferences accessToken = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editAccessToken = accessToken.edit();
                        editAccessToken.clear();
                        editAccessToken.apply();

                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                        break;
                    case R.id.nav_share:

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String body = "Download This App";
//                        String discription = "https://shahir.xyz";
                        intent.putExtra(Intent.EXTRA_TEXT, body);
//                        intent.putExtra(Intent.EXTRA_TEXT, discription);
                        startActivity(Intent.createChooser(intent, "Share Blood Request"));

                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void headerText() {
        View view = navigation.getHeaderView(0);
        TextView userName = view.findViewById(R.id.tvUserNameForHeader);
        TextView userEmail = view.findViewById(R.id.tvUserEmailForHeader);

//        userName.setText("Shahir Islam");
//        userEmail.setText("lifeofdekisugi@gmail.com");

        userName.setText(name);
        userEmail.setText(number);
    }

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)){
//            drawer.closeDrawer(GravityCompat.START);
//        }else{
//            super.onBackPressed();
//        }
//    }
}
