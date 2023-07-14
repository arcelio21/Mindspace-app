package com.mindspace.app.usecases.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.mindspace.app.R;
import com.mindspace.app.usecases.listdiario.DiarioAcitivity;
import com.mindspace.app.usecases.perfiluser.PerfilActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnListDiario;
    private ImageButton btnPerfilUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.btnListDiario = findViewById(R.id.home_btnListDiario);
        this.btnListDiario.setOnClickListener(view -> {
            Intent listDiarioActivity = new Intent(this, DiarioAcitivity.class);
            startActivity(listDiarioActivity);
        });

        this.btnPerfilUser = findViewById(R.id.Home_BtnUsuario);

        this.btnPerfilUser.setOnClickListener(view -> {
            Intent perfilActivity = new Intent(this, PerfilActivity.class);
            startActivity(perfilActivity);
        });
    }


}