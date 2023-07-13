package com.mindspace.app.usecases.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.mindspace.app.R;
import com.mindspace.app.usecases.listdiario.DiarioAcitivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnListDiario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.btnListDiario = findViewById(R.id.home_btnListDiario);
        this.btnListDiario.setOnClickListener(view -> {
            Intent listDiarioActivity = new Intent(this, DiarioAcitivity.class);
            startActivity(listDiarioActivity);
        });
    }


}