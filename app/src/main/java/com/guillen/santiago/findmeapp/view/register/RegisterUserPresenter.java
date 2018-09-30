package com.guillen.santiago.findmeapp.view.register;

import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.domain.Register;

public class RegisterUserPresenter implements RegisterUserContract.Presenter {
    private Register registerInteractor;
    private RegisterUserContract.View view;

    public RegisterUserPresenter(RegisterUserContract.View view) {
        registerInteractor = new Register();
        this.view = view;
    }

    @Override
    public void RegisterUser(User user) {


    }
}
