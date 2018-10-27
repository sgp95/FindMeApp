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
import android.view.View;
import android.widget.TextView;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.view.careTaker.beacons.BeaconsFragment;
import com.guillen.santiago.findmeapp.view.careTaker.patients.PatientListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    public static final String CURRENT_USER_ID = "currentUserId";

    @BindView(R.id.dl_main)
    protected DrawerLayout mainDrawerLayout;
    @BindView(R.id.nv_main)
    protected NavigationView mainNavigationView;
    @BindView(R.id.tb_main)
    protected Toolbar mainToolbar;

    private TextView tvUserName;
    private TextView tvUserEmail;

    private MainContract.Presenter presenter;
    private View headerView;
    private String currentUserId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_care_taker);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

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
                switch (item.getItemId()){
                    case R.id.option_home:
                        showDefaultFragment();
                        break;
                    case R.id.option_patients:
                        PatientListFragment patientListFragment = new PatientListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(CURRENT_USER_ID, currentUserId);
                        patientListFragment.setArguments(bundle);
                        showFragment(patientListFragment, "tag", false);
                        break;
                    case R.id.option_logout:
                        presenter.logout();

                }
                //TODO logic to obtain id of item clicked and show respective view
                mainDrawerLayout.closeDrawers();
                return true;
            }
        });

        headerView = mainNavigationView.getHeaderView(0);
        tvUserName = headerView.findViewById(R.id.tv_user_name);
        tvUserEmail = headerView.findViewById(R.id.tv_user_email);

        presenter.getCurrentUser();
    }

    public void setBarTitle(String title){
        mainToolbar.setTitle(title);
    }

    private void showDefaultFragment(){
        BeaconsFragment beaconsFragment = new BeaconsFragment();
        showFragment(beaconsFragment, BeaconsFragment.TAG, true);
    }

    private void showFragment(Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.addToBackStack(addToBackStack ? tag : null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCurrentUserLoaded(User currentUser) {
        String userName = currentUser.getName().getFirst()+" "+currentUser.getName().getLast();
        currentUserId = currentUser.getId();
        tvUserName.setText(userName);
        tvUserEmail.setText(currentUser.getEmail());
    }

    @Override
    public void onCurrentUserFailed() {
        presenter.logout();
        //TODO send To Login
    }

    @Override
    public void onLogout() {
        System.exit(0);
    }
}
