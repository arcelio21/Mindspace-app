package com.mindspace.app.usecases.creatediary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mindspace.app.R;
import com.mindspace.app.usecases.listdiario.DiarioAcitivity;

public class CreateDiarioActivity extends AppCompatActivity {

    private Button btnCrearNota;
    private EditText etTitulo;
    private EditText etCuerpo;
    private CreateViewModel createViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearnota);

        this.createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);

        this.btnCrearNota = findViewById(R.id.btnGuardarNota);
        this.setListenerBtnCrearNota();

        this.etTitulo = findViewById(R.id.crear_nota_titulo);
        this.etCuerpo = findViewById(R.id.crear_nota_desc);
    }

    private void setListenerBtnCrearNota(){
        btnCrearNota.setOnClickListener(view ->{
            Intent intent = new Intent (CreateDiarioActivity.this, DiarioAcitivity.class);
            startActivity(intent);
        });
    }
}

