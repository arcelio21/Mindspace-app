package com.mindspace.app.usecases.perfiluser;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mindspace.app.model.user.UsuarioGet;
import com.mindspace.app.provider.service.firebase.user.UserService;
import com.mindspace.app.usecases.base.ListenerGetFirebase;

public class PerfilViewModel extends ViewModel implements ListenerGetFirebase<UsuarioGet> {

    private MutableLiveData<UsuarioGet> usuario;
    private UserService userService;

    public PerfilViewModel() {
        this.usuario = new MutableLiveData<>();
        this.userService = new UserService(null);
    }

    public void getById(){
        this.userService.getUserCurrent(this);
    }

    @Override
    public void getData(UsuarioGet data) {
        this.usuario.postValue(data);
    }

    public MutableLiveData<UsuarioGet> getUsuario() {
        return usuario;
    }
}
