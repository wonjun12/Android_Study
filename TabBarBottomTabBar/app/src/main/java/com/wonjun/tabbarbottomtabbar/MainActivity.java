package com.wonjun.tabbarbottomtabbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.wonjun.tabbarbottomtabbar.config.Config;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView buttonNavigationView;
    //각 프레그먼트들을 멤버변수로 만든다.
    Fragment firstFragment;
    Fragment secondFragment;
    Fragment thirdFragment;

    String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE, MODE_PRIVATE);
        accessToken = sp.getString(Config.ACCESS_TOKEN, "");
        if(accessToken.isEmpty()){
            Intent intent = new Intent(MainActivity.this, UserRegisterActivity.class);

            startActivity(intent);
            finish();
            return;
        }

        
        getSupportActionBar().setTitle("홈");

        buttonNavigationView = findViewById(R.id.buttonNavigationView);

        firstFragment = new FristFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();

        buttonNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Fragment fragment = null;

                if(itemId == R.id.firstFragment){
                    fragment = firstFragment;
                }else if(itemId == R.id.secondFragment){
                    fragment = secondFragment;
                }else if(itemId == R.id.thirdFragment){
                    fragment = thirdFragment;
                }

                return loadFragment(fragment);
            }
        });
    }

boolean loadFragment(Fragment fragment){
    if(fragment != null){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();

        return true;
    }else {
        return false;
    }
}

}