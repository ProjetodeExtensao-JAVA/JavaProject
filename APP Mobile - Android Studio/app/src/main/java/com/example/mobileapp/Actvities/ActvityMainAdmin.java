package com.example.mobileapp.Actvities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobileapp.Fragment.CadastroFragment;
import com.example.mobileapp.Fragment.FotosFragment;
import com.example.mobileapp.Fragment.HomeFragment;
import com.example.mobileapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActvityMainAdmin extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_screen);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.mihome:
                fragment = new HomeFragment();
                break;
            case R.id.mifotos:
                fragment = new FotosFragment();
                break;
            case R.id.micadastro:
                fragment = new CadastroFragment();
                break;
        }
        if (fragment != null){
            loadFragment(fragment);
        }
        return true;
    }
    void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
    }
}