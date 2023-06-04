package com.mindspace.app.usecases.registrarse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mindspace.app.model.user.AuthenticationUser;
import com.mindspace.app.model.user.UsuarioPost;
import com.mindspace.app.usecases.login.LoginActivity;
import com.mindspace.app.R;

public class RegistrarseActivity extends AppCompatActivity {

    private Button btnRegister;
    private Button btnBackLogin;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etName;
    private EditText etApellido;
    private EditText etEdad;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
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

        this.registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        this.etEmail = findViewById(R.id.etEmai);
        this.etPassword = findViewById(R.id.etPassword);
        this.etName = findViewById(R.id.etNOmbre);
        this.etApellido = findViewById(R.id.etApellido);
        this.etEdad = findViewById(R.id.etEdad);

        this.btnBackLogin = findViewById(R.id.buttonReturnLogin);
        this.setListenerButtonBackLogin();

        this.btnRegister = findViewById(R.id.buttonRegistrarme);
        this.setListenerButtonRegistrarme();

        this.setListenerResponseAuthentication();


    }


    /**
     * Establece el listener para el botón "Volver al inicio de sesión".
     */
    private void setListenerButtonBackLogin(){
        this.btnBackLogin.setOnClickListener(view -> {
            // Acción a realizar al hacer clic en el botón "ya tengo cuenta"
            // Por ejemplo, iniciar una nueva actividad
            Intent intent = new Intent(RegistrarseActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Establece el listener para el botón "Registrarme".
     */
    //TODO CAMBIAR IMPLEMENTACION DE OBTENCION DE DATOS DE USUAIORS
    private void setListenerButtonRegistrarme(){

        this.btnRegister.setOnClickListener(view ->{

            String email = this.etEmail.getText().toString().trim();
            String password = this.etPassword.getText().toString();

            if(!email.trim().isEmpty() && !password.trim().isEmpty()){

                String nombre = this.etName.getText().toString();
                String apellido = this.etApellido.getText().toString();
                //TODO MANEJAR EXEPCION
                Integer edad = Integer.valueOf(this.etEdad.getText().toString());

                if(!nombre.trim().isEmpty() && !apellido.trim().isEmpty() && edad>0){

                    AuthenticationUser userAuth = AuthenticationUser.builder()
                            .email(email)
                            .password(password)
                            .build();

                    UsuarioPost usuarioPost = UsuarioPost.builder()
                            .email(email)
                            .nombre(nombre)
                            .apellido(apellido)
                            .edad(edad)
                            .build();

                    this.registerViewModel.createAccount(userAuth, usuarioPost);

                }else {
                    Toast.makeText(this,"Ingrese los datos completos", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"Ingrese un correo electronico y contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Establece el listener para la respuesta de autenticación.
     */
    private void setListenerResponseAuthentication(){
        this.registerViewModel.getResponseAuthenticacion().observe(this, responseAuth -> {
            if(responseAuth){
                Intent test = new Intent(this, LoginActivity.class);
                startActivity(test);
            }else {
                Toast.makeText(this,"Correo o contraseña no valido", Toast.LENGTH_SHORT).show();
            }
        });
    }

}