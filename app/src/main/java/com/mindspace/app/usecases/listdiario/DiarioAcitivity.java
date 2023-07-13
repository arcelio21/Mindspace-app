package com.mindspace.app.usecases.listdiario;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindspace.app.R;
import com.mindspace.app.model.user.DiarioGet;

import java.util.ArrayList;
import java.util.List;

public class DiarioAcitivity extends AppCompatActivity {

    private DiarioViewModel diarioViewModel;
    private DiarioAdapterRV diarioAdapterRV;
    private RecyclerView recyclerView;
    private List<DiarioGet> diarioList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        this.diarioList = new ArrayList<>();

        this.diarioViewModel = new ViewModelProvider(this).get(DiarioViewModel.class);
        this.recyclerView = findViewById(R.id.diarios_RVlistDiarios);

        this.diarioViewModel.getAll();

        this.diarioAdapterRV = new DiarioAdapterRV(this.diarioList);

        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.diarioAdapterRV);

        this.setObserverListDiario(this.diarioViewModel);
    }

    private void setObserverListDiario(DiarioViewModel diarioVm){
        diarioVm.getDiarosList().observe(this, data ->{

            Log.d("OBSERVER", data.get(0).getCuerpo());
            if (data!=null && !data.isEmpty()){
                this.diarioList = data;
                this.diarioAdapterRV.setDiarioList(diarioList);
                this.diarioAdapterRV.notifyDataSetChanged();
            }
        });
    }
}
