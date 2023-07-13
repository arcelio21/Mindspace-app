package com.mindspace.app.usecases.listdiario;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mindspace.app.model.user.DiarioGet;
import com.mindspace.app.provider.service.firebase.diario.DiarioService;
import com.mindspace.app.usecases.base.ListenerGetFirebase;

import java.util.List;

public class DiarioViewModel  extends ViewModel implements ListenerGetFirebase<DiarioGet> {

    private MutableLiveData<List<DiarioGet>> diarosList ;

    private DiarioService service;

    public DiarioViewModel() {
        this.diarosList = new MutableLiveData<>();
        this.service = new DiarioService();
    }

    public void getAll(){
        this.service.getAll(this);
    }

    @Override
    public void getData(List<DiarioGet> data) {
        if(data==null ||data.isEmpty()) return;

        diarosList.postValue(data);
    }

    public MutableLiveData<List<DiarioGet>> getDiarosList() {
        return diarosList;
    }
}
