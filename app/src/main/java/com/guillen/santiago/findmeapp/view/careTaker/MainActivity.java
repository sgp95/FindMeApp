package com.guillen.santiago.findmeapp.view.careTaker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.view.careTaker.beacons.BeaconsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.dl_main)
    protected DrawerLayout mainDrawerLayout;
    @BindView(R.id.nv_main)
    protected NavigationView mainNavigationView;
    @BindView(R.id.tb_main)
    protected Toolbar mainToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_care_taker);
        ButterKnife.bind(this);

        setUpView();

        showDefaultFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mainDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mainDrawerLayout.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }

    private void setUpView(){

        setSupportActionBar(mainToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_main_drawer_menu);

        mainNavigationView.setCheckedItem(R.id.option_home);

        mainNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                //TODO logic to obtain id of item clicked and show respective view
                mainDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void setBarTitle(String title){
        mainToolbar.setTitle(title);
    }

    private void showDefaultFragment(){
        BeaconsFragment beaconsFragment = new BeaconsFragment();
        showFragment(beaconsFragment);
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
