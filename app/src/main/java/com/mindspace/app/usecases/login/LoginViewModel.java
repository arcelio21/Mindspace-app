package com.mindspace.app.usecases.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mindspace.app.model.user.AuthenticationUser;
import com.mindspace.app.model.user.UsuarioGet;
import com.mindspace.app.provider.service.firebase.user.UserService;
import com.mindspace.app.usecases.base.ListenerAuthentication;
import com.mindspace.app.util.Encrypt;

/**
 * ViewModel para el inicio de sesión.
 */
public class LoginViewModel extends ViewModel implements ListenerAuthentication {

    private UserService userService;
    private final MutableLiveData<Boolean> responseAuthenticacion = new MutableLiveData<>();

    public LoginViewModel() {
        this.userService = new UserService(this);
    }

    /**
     * Realiza el inicio de sesión del usuario.
     *
     * @param user Objeto AuthenticationUser que contiene la información de autenticación del usuario.
     */
    public void login(AuthenticationUser user){

        AuthenticationUser userFinal =AuthenticationUser.builder()
                .email(user.getEmail())
                .password(Encrypt.encrypt(user.getPassword()))
                .build();

        this.userService.login(userFinal);
    }


    /**
     * Obtener datos del usuario.
     *
     * @param email Email del usuario para obtener sus datos.
     * @return Objeto UsuarioGet con los datos del usuario.
     */
    public  UsuarioGet getUser(String email){
        email = Encrypt.encrypt(email);
        //TODO SE APLICA LOGICA PARA OBETNEER USUARIO
        return null;
    }

    /**
     * Método de notificación de respuesta de autenticación.
     *
     * @param response Respuesta de autenticación.
     */
    @Override
    public void notifyResponse(Boolean response) {
        this.responseAuthenticacion.postValue(response);
    }


    /**
     * Obtener el LiveData de respuesta de autenticación.
     *
     * @return LiveData<Boolean> con la respuesta de autenticación.
     */
    public MutableLiveData<Boolean> getResponseAuthenticacion() {
        return responseAuthenticacion;
    }
}
