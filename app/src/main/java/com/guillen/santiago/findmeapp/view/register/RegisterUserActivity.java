package com.guillen.santiago.findmeapp.view.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.data.model.UserName;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUserActivity extends AppCompatActivity implements RegisterUserContract.View {
    @BindView(R.id.etUserFirstName)
    protected EditText etUserFirstName;
    @BindView(R.id.etUserLastName)
    protected EditText etUserLastName;
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
    @BindView(R.id.pbRegisterUser)
    protected ProgressBar pbRegisterUser;

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

    private void setLoadingView(boolean isLoading){
        pbRegisterUser.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void setUserTypeListener(){
        spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String userType = (String)adapterView.getItemAtPosition(pos);
                Log.d("rastro","User Type selected is "+userType);
                user.setType(userType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });
    }

    private boolean UserInfoIsComplete(User user){
        if(user.getEmail() == null || user.getEmail().trim().isEmpty()){
            return false;
        }
        if(user.getName() == null){
            if( user.getName().getFirst().trim().isEmpty() ||  user.getName().getLast().trim().isEmpty())
            return false;
        }
        if(user.getContactPhone() == null || user.getContactPhone().trim().isEmpty()){
            return false;
        }
        if(user.getType() == null || user.getType().trim().isEmpty()){
            return false;
        }
        if(user.getImageUrl() == null || user.getImageUrl().trim().isEmpty()){
            return false;
        }
        return true;
    }

    @OnClick(R.id.btnRegisterUser)
    public void RegisterUser(){
        setLoadingView(true);
        UserName name = new UserName();
        name.setFirst(etUserFirstName.getText().toString().trim());
        name.setLast(etUserLastName.getText().toString().trim());
        user.setName(name);
        user.setContactPhone(etUserPhone.getText().toString().trim());
        user.setEmail(etUserMail.getText().toString().trim());
        user.setImageUrl("Here is the Image");

        if(UserInfoIsComplete(user)){
            presenter.registerUser(user, etUserPassword.getText().toString().trim());
        }else {
            Toast.makeText(this, "Informacion del Usuario no esta completa", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRegisterSuccess(String userId) {
        setLoadingView(false);
        //TODO go back to the login and show the successful message
    }

    @Override
    public void onRegisterFailure() {
        setLoadingView(false);
        //TODO go back to login and show failure message
    }
}
