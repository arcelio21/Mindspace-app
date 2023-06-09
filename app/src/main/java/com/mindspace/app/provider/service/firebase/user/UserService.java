package com.mindspace.app.provider.service.firebase.user;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mindspace.app.model.user.AuthenticationUser;
import com.mindspace.app.model.user.UsuarioGet;
import com.mindspace.app.model.user.UsuarioPost;
import com.mindspace.app.usecases.base.ListenerAuthentication;
import com.mindspace.app.usecases.base.ListenerGetFirebase;
import com.mindspace.app.usecases.base.ListenerResponseFirabase;

import java.util.Date;
import java.util.Map;

public class UserService {

    private final FirebaseAuth authentication;
    private final FirebaseFirestore baseDatos;

    private final ListenerAuthentication listenerAuth;



    public UserService(ListenerAuthentication authentication) {
        this.baseDatos = FirebaseFirestore.getInstance();
        this.authentication = FirebaseAuth.getInstance();
        this.listenerAuth=authentication;
    }


    public void getUserCurrent(ListenerGetFirebase<UsuarioGet> listenerGetFirebase){
        String idCurrentUser = this.validateIdCurrentUser();

        this.baseDatos.collection("usuarios")
                .document(idCurrentUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String email = task.getResult().get("email",String.class);
                        Integer edad = task.getResult().get("edad", Integer.class);
                        String nombre = task.getResult().get("nombre", String.class);
                        String apellido = task.getResult().get("apellido", String.class);

                        UsuarioGet usuarioGet = new UsuarioGet();
                        usuarioGet.setEmail(email);
                        usuarioGet.setNombre(nombre);
                        usuarioGet.setApellido(apellido);
                        usuarioGet.setEdad(edad);

                        listenerGetFirebase.getData(usuarioGet);
                    }
                })
                .addOnFailureListener(runnable -> {
                    Log.d("Erro", runnable.getMessage());
                });
    }

    /**
     Crea una cuenta de usuario utilizando el servicio de autenticación.
     @param authenticationUser Objeto que contiene la información de autenticación del usuario.
     @return N/A
     */
    public void creacionCuenta(AuthenticationUser authenticationUser, UsuarioPost usuarioPost){


        if(authenticationUser==null
            ||authenticationUser.getEmail()==null ||authenticationUser.getEmail().trim().isEmpty()
            || authenticationUser.getPassword()==null ||authenticationUser.getPassword().trim().isEmpty()
        ){
            this.listenerAuth.notifyResponse(false);
            return;
        }

        Task<AuthResult> response = this.authentication.createUserWithEmailAndPassword(
                authenticationUser.getEmail(), authenticationUser.getPassword()
        );

        response.addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                this.save(usuarioPost);
            }
        });
    }

    /**
     * Guarda un objeto UsuarioPost en la base de datos.
     * @param usuarioPost El objeto UsuarioPost a guardar.
     */
    private void save(UsuarioPost usuarioPost){

        if(usuarioPost==null
             || usuarioPost.getEmail()==null || usuarioPost.getEmail().trim().isEmpty()
             || usuarioPost.getNombre()==null || usuarioPost.getNombre().trim().isEmpty()
             || usuarioPost.getApellido()==null || usuarioPost.getApellido().trim().isEmpty()
             || usuarioPost.getEdad()==null || usuarioPost.getEdad()<=0
                || this.authentication.getUid()==null
        ){
            this.listenerAuth.notifyResponse(false);
            return;
        }

        Task<Void> response = this.baseDatos.collection("usuarios")
                .document(this.authentication.getUid())
                .set(this.usuarioPostToMapToSave(usuarioPost));

        response.addOnCompleteListener(task -> {
            this.listenerAuth.notifyResponse(task.isSuccessful());
        });

        //EN CASO DE QUE NO SE GUARDA LOS DATOS EN LA COLECCION DE USUARIO
        //SE ELIMINA EL USUARIO QUE SE CREO EN LA AUTENTIFICACION
        response.addOnFailureListener(runnable -> {
            if(this.authentication.getCurrentUser()!=null){
                this.authentication.getCurrentUser().delete();
            }
        });
    }

    /**
     * Convierte un objeto UsuarioPost a un Map<String, Object> para guardar en la base de datos.
     *
     * @param user El objeto UsuarioPost a convertir.
     * @return Un Map con los datos del usuario en el formato requerido para la base de datos.
     */
    private Map<String, Object> usuarioPostToMapToSave(UsuarioPost user){
        return Map.of("nombre", user.getNombre(),
                "email",user.getEmail(),
                "apellido", user.getApellido(),
                "edad", user.getEdad(),
                "fechaCreacion", new Timestamp(new Date()));

    }



    /**
     * Inicia sesión de usuario.
     * @param authenticationUser El objeto AuthenticationUser con los datos de autenticación del usuario.
     * @return N/A
     */
    public void login(AuthenticationUser authenticationUser){

        if(authenticationUser==null
                ||authenticationUser.getEmail()==null ||authenticationUser.getEmail().trim().isEmpty()
                || authenticationUser.getPassword()==null ||authenticationUser.getPassword().trim().isEmpty()
        ){
            this.listenerAuth.notifyResponse(false);
            return;
        }


        this.authentication.
                signInWithEmailAndPassword(authenticationUser.getEmail(), authenticationUser.getPassword())
                .addOnCompleteListener(task -> {
                    this.listenerAuth.notifyResponse(task.isSuccessful());
                });

    }

    //TODO ESPERAR CREACION DE PANTALLA DE CONFIGURACION DE USUARIO PARA AGREGAR EVENTO
    public void update(UsuarioPost usuarioPost, ListenerResponseFirabase listenerResponseFirabase){

        if(usuarioPost==null
                || usuarioPost.getEmail()==null || usuarioPost.getEmail().trim().isEmpty()
                || usuarioPost.getNombre()==null || usuarioPost.getNombre().trim().isEmpty()
                || usuarioPost.getApellido()==null || usuarioPost.getApellido().trim().isEmpty()
                || usuarioPost.getEdad()==null || usuarioPost.getEdad()<=0
        ){
            listenerResponseFirabase.notifyChange(false);
            return;
        }

        Map<String,Object> mapUser=this.usuarioPostToMapUpdate(usuarioPost);

        this.baseDatos.collection("usuarios")
                .document(usuarioPost.getEmail())
                .update(mapUser)
                .addOnCompleteListener(task -> {
                    listenerResponseFirabase.notifyChange(task.isSuccessful());
                });
    }

    private Map<String,Object> usuarioPostToMapUpdate(UsuarioPost usuarioPost){
        return Map.of("apellido", usuarioPost.getApellido(),
                "nombre", usuarioPost.getNombre(),
                "edad", usuarioPost.getNombre(),
                "ultimaActualizacion", new Timestamp(new Date())
        );
    }

    /**
     * VALIDAR EMAIL DEL USUARIO ACTUAL
     * @return response
     */
    private String validateIdCurrentUser(){

        String id ="";

        if (this.authentication.getCurrentUser() == null) {
            return "";
        }
        id= this.authentication.getCurrentUser().getUid();

        if (id.trim().isEmpty()){
            return "";
        }

        return id;
    }
}
