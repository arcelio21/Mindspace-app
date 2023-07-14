package com.mindspace.app.usecases.listdiario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindspace.app.R;
import com.mindspace.app.model.user.DiarioGet;
import com.mindspace.app.usecases.base.ListenerClickItemAdapter;
import com.mindspace.app.usecases.creatediary.CreateDiarioActivity;

import java.util.ArrayList;
import java.util.List;

public class DiarioAcitivity extends AppCompatActivity implements ListenerClickItemAdapter {

    private DiarioViewModel diarioViewModel;
    private DiarioAdapterRV diarioAdapterRV;
    private RecyclerView recyclerView;
    private List<DiarioGet> diarioList;

    private Button btnCreateNote;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        this.btnCreateNote = findViewById(R.id.diarios_btnAddNote);

        this.btnCreateNote.setOnClickListener(view -> {
            Intent addNoteActivity = new Intent(this, CreateDiarioActivity.class);
            startActivity(addNoteActivity);
        });


        this.diarioList = new ArrayList<>();

        this.diarioViewModel = new ViewModelProvider(this).get(DiarioViewModel.class);
        this.recyclerView = findViewById(R.id.diarios_RVlistDiarios);

        this.diarioViewModel.getAll();

        this.diarioAdapterRV = new DiarioAdapterRV(this.diarioList, this);

        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.diarioAdapterRV);

        this.setObserverListDiario(this.diarioViewModel);
    }

    private void setObserverListDiario(DiarioViewModel diarioVm){
        diarioVm.getDiarosList().observe(this, data ->{

            if (data!=null && !data.isEmpty()){
                this.diarioList = data;
                this.diarioAdapterRV.setDiarioList(diarioList);
                this.diarioAdapterRV.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void clickItem(Integer position) {
        Intent updateNote = new Intent(this, CreateDiarioActivity.class);
        Log.d("IDlis", this.diarioList.get(position).getId().toString());
        updateNote.putExtra("idNote", this.diarioList.get(position).getId());
        startActivity(updateNote);
    }
}
