package com.mindspace.app.usecases.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mindspace.app.R;
import com.mindspace.app.model.user.AuthenticationUser;
import com.mindspace.app.usecases.home.HomeActivity;
import com.mindspace.app.usecases.registrarse.RegistrarseActivity;

//TODO NOMENCLATURA DE ID DE ACUERDO A LA ACTIVIDAD
//TODO ESTRCUTURA DE NOMBRES DE LAYOUT DE ACUERDO A LA ACTIVIDAD
//TODO TIEMPO DE DISEÑO
public class LoginActivity extends AppCompatActivity {

    private Button btnRegister;
    private Button btnLogin;

    private EditText etEmail;
    private EditText etPassword;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Funcion que Oculta la barra de navegación y status
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
        }

        setContentView(R.layout.activity_login);

         this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        this.btnRegister = findViewById(R.id.buttonRegister);
        this.setListenerBtnRegister();

        this.btnLogin = findViewById(R.id.buttonLogin);
        this.setListenerBtnLogin();

        this.etEmail = findViewById(R.id.Login_etEmail);
        this.etPassword = findViewById(R.id.Login_etPassword);

        this.setListenerResponseAuthentication();
    }


    /**
     * Establece el listener para el botón "Registrarse".
     */
    private void setListenerBtnRegister(){
        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrarseActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Establece el listener para el botón "Iniciar sesión".
     */
    private void setListenerBtnLogin(){
        this.btnLogin.setOnClickListener(view -> validateLogin());
    }



    /**
     * Valida el inicio de sesión.
     */
    private void validateLogin(){
        String email = this.etEmail.getText().toString().trim();
        String password = this.etPassword.getText().toString();
        if ( !email.trim().isEmpty()
                && !password.trim().isEmpty() ){

            AuthenticationUser user = AuthenticationUser.builder()
                    .email(email)
                    .password(password)
                    .build();

            this.loginViewModel.login(user);


        }else {
            Toast.makeText(this,"Escriba un correo y contraseña correcto", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Establece el listener para la respuesta de autenticación.
     */
    private void setListenerResponseAuthentication(){
        this.loginViewModel.getResponseAuthenticacion().observe(this, responseAuth -> {
            if(responseAuth){
                Intent activity_home = new Intent(this, HomeActivity.class);
                startActivity(activity_home);
            }else {
                Toast.makeText(this,"Correo o contraseña no valido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}