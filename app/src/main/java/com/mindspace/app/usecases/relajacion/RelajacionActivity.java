package com.mindspace.app.usecases.relajacion;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mindspace.app.R;

public class RelajacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relajacion);
    }


    public void listenerBtn(View view){
        Toast.makeText(this,"Funcionalidad no diponible",Toast.LENGTH_SHORT).show();
    }
}
