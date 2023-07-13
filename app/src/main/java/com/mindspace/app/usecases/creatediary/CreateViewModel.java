package com.mindspace.app.usecases.creatediary;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mindspace.app.model.user.DiarioPost;
import com.mindspace.app.provider.service.firebase.diario.DiarioService;
import com.mindspace.app.provider.service.firebase.user.UserService;
import com.mindspace.app.usecases.base.ListenerAuthentication;
import com.mindspace.app.usecases.base.ListenerResponseFirabase;

public class CreateViewModel extends ViewModel implements ListenerResponseFirabase {

    private DiarioService diarioService;
    private final MutableLiveData<Boolean> reponse = new MutableLiveData<>();


    public CreateViewModel() {
        this.diarioService = new DiarioService();
        this.diarioService.setResponseFirabase(this);
    }

    public void save(DiarioPost diarioPost){
        diarioService.save(diarioPost);
    }


    public MutableLiveData<Boolean> getReponse() {
        return reponse;
    }

    @Override
    public void notifyChange(Boolean response) {
        this.reponse.postValue(response);
    }
}
