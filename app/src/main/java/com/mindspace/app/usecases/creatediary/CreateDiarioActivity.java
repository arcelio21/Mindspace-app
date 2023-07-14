package com.mindspace.app.usecases.creatediary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mindspace.app.R;
import com.mindspace.app.model.user.DiarioPost;
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

        String idNote = getIntent().getStringExtra("idNote");
        Log.d("ID", idNote);

        this.createViewModel = new ViewModelProvider(this).get(CreateViewModel.class);

        this.btnCrearNota = findViewById(R.id.btnGuardarNota);


        this.etTitulo = findViewById(R.id.crear_nota_titulo);
        this.etCuerpo = findViewById(R.id.crear_nota_desc);

        if(idNote.trim().isEmpty()){
            this.setOberverCreateNoteResponse();
            this.setListenerBtnCrearNota();
        }else {
            this.createViewModel.getById(idNote);

            this.btnCrearNota.setOnClickListener(view -> {
                Toast.makeText(this,"Funcionalidad no disponible", Toast.LENGTH_SHORT).show();
            });
            this.btnCrearNota.setText("Actualizar");


            this.createViewModel.getDiario().observe(this,diarioGet -> {
                if(diarioGet!=null){
                    this.etCuerpo.setText(diarioGet.getCuerpo());
                    this.etTitulo.setText(diarioGet.getTitulo());
                }else {
                    Toast.makeText(this,"Error de carga de datos", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setListenerBtnCrearNota(){
        btnCrearNota.setOnClickListener(view ->{

            String titulo = this.etTitulo.getText().toString();
            String cuerpo = this.etCuerpo.getText().toString();

            DiarioPost diarioPost = DiarioPost.builder()
                    .cuerpo(cuerpo)
                    .titulo(titulo)
                    .build();

            this.createViewModel.save(diarioPost);
        });
    }

    private void setOberverCreateNoteResponse(){
        this.createViewModel.getReponse().observe(this,response -> {

            if(response){
                Intent diarioList = new Intent(this, DiarioAcitivity.class);
                startActivity(diarioList);
            }else {
                Toast.makeText(this,"No se puedo guardar informacion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

