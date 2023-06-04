package com.mindspace.app.usecases.registrarse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mindspace.app.model.user.AuthenticationUser;
import com.mindspace.app.model.user.UsuarioGet;
import com.mindspace.app.provider.service.firebase.user.UserService;
import com.mindspace.app.usecases.base.ListenerAuthentication;
import com.mindspace.app.util.Encrypt;

public class RegisterViewModel extends ViewModel implements ListenerAuthentication {

    private UserService userService;
    private final MutableLiveData<Boolean> responseAuthenticacion = new MutableLiveData<>();

    public RegisterViewModel() {

        this.userService = new UserService(this);
    }

    public void createAccount(AuthenticationUser user){

        AuthenticationUser userFinal =AuthenticationUser.builder()
                .email(user.getEmail())
                .password(Encrypt.encrypt(user.getPassword()))
                .build();


        this.userService.creacionCuenta(userFinal);
    }


    public UsuarioGet getDataUser(String email){

        email = Encrypt.encrypt(email);
        return null;
    }

    @Override
    public void notifyResponse(Boolean response) {
        this.responseAuthenticacion.postValue(response);
    }

    public MutableLiveData<Boolean> getResponseAuthenticacion() {
        return responseAuthenticacion;
    }
}
