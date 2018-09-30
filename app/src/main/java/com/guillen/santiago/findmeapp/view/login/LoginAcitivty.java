package com.guillen.santiago.findmeapp.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;
import com.guillen.santiago.findmeapp.view.register.RegisterUserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginAcitivty extends AppCompatActivity implements LoginContract.View {
    @BindView(R.id.et_email_login)
    protected EditText etEmailLogin;
    @BindView(R.id.et_password_login)
    protected EditText etPasswordLogin;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClicked(){
        presenter.loginUser(
                etEmailLogin.getText().toString(),
                etPasswordLogin.getText().toString()
        );
    }

    @OnClick(R.id.btn_register)
    public void registerButtonClicked(){
        startActivity(new Intent(LoginAcitivty.this, RegisterUserActivity.class));
    }

    @Override
    public void onLoginSuccess(User userInfo) {
        Log.d("rastro","user name: "+userInfo.getName()+" user email: "+userInfo.getEmail());
        if(userInfo.getType().trim().equals("Cuidador")){
            startActivity(new Intent(LoginAcitivty.this, MainActivity.class));
        }else {
            startActivity(new Intent(LoginAcitivty.this, com.guillen.santiago.findmeapp.view.patient.MainActivity.class));
        }
    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(this,"login Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.dispose();
        super.onDestroy();
    }
}
