package com.guillen.santiago.findmeapp.view.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUserActivity extends AppCompatActivity implements RegisterUserContract.View {
    @BindView(R.id.etUserMail)
    protected EditText etUserMail;
    @BindView(R.id.etUserPhone)
    protected EditText etUserPhone;
    @BindView(R.id.etUserPassword)
    protected EditText etUserPassword;
    @BindView(R.id.spUserType)
    protected Spinner spUserType;
    @BindView(R.id.btnRegisterUser)
    protected Button btnRegisterUser;
    @BindView(R.id.tvUserTypeDescription)
    protected TextView tvUserTypeDescription;

    private RegisterUserPresenter presenter;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);

        setUpView();

        setUserTypeListener();
    }

    private void setUpView(){
        presenter = new RegisterUserPresenter(this);

        user = new User();
    }

    private void setUserTypeListener(){
        spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String userType = (String)adapterView.getItemAtPosition(pos);
                Log.d("rastro","User Type selected is "+userType);
                user.setUserType(userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });
    }

    @OnClick(R.id.btnRegisterUser)
    public void RegisterUser(){
        //TODO set data to user model and register
    }

    @Override
    public void onRegisterSuccess(String userId) {
        //TODO go back to the login and show the successful message
    }

    @Override
    public void onRegisterFailure() {
        //TODO go back to login and show failure message
    }
}
