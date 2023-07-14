package com.mindspace.app.usecases.perfiluser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mindspace.app.R;

public class PerfilActivity extends AppCompatActivity {

    private PerfilViewModel perfilViewModel;
    private EditText etNOmbre;
    private EditText etApellido;
    private EditText etEmail;
    private EditText etEdad;

    private Button btnSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        this.perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        this.etEdad = findViewById(R.id.Perfil_ed_edad);
        this.etApellido = findViewById(R.id.Perfil_ed_apellido);
        this.etNOmbre = findViewById(R.id.Perfil_ed_nombre);
        this.etEmail = findViewById(R.id.Perfil_ed_email);
        this.btnSave = findViewById(R.id.Perfil_btnGuardar);

        this.perfilViewModel.getById();

        this.perfilViewModel.getUsuario().observe(this,data ->{
            if(data!=null){
                this.etEmail.setText(data.getEmail());
                this.etApellido.setText(data.getApellido());
                this.etNOmbre.setText(data.getNombre());
                this.etEdad.setText(String.valueOf(data.getEdad()));
            }else {
                Toast.makeText(this,"Error carga de datos", Toast.LENGTH_SHORT).show();
            }
        });

        this.btnSave.setOnClickListener(view -> {
            Toast.makeText(this,"Funcionalidad no diponible",Toast.LENGTH_SHORT).show();
        });

    }
}
