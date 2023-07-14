package com.mindspace.app.provider.service.firebase.diario;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mindspace.app.model.user.DiarioGet;
import com.mindspace.app.model.user.DiarioPost;
import com.mindspace.app.usecases.base.ListenerGetFirebase;
import com.mindspace.app.usecases.base.ListenerResponseFirabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DiarioService {

    private final FirebaseAuth authentication;
    private final FirebaseFirestore baseDatos;

    private  ListenerResponseFirabase responseFirabase;

    public DiarioService() {
        this.baseDatos = FirebaseFirestore.getInstance();
        this.authentication = FirebaseAuth.getInstance();
    }

    public void setResponseFirabase(ListenerResponseFirabase responseFirabase) {
        this.responseFirabase = responseFirabase;
    }


    public void save(DiarioPost diarioPost) {

        String idCurrentUser = this.validateIdCurrentUser("");

        if(idCurrentUser.trim().isEmpty()){
            return;
        }

        Map<String,Object> diarioMap = this.convertDiarioPostToMap(diarioPost);
        Task<Void> response  = baseDatos
                .collection("usuarios").document(idCurrentUser)
                .collection("diarios").document().set(diarioMap);

        response.addOnCompleteListener(task -> {
            this.responseFirabase.notifyChange(task.isSuccessful());
        });
        response.addOnFailureListener(runnable -> {
            Log.d("ERROR", runnable.getMessage());
        });
    }

    /**
     * OBTENER TODOS LOS DIARIOS CREADOS POR UN USUARIO
     */
    public void getAll(ListenerGetFirebase<List<DiarioGet>> listener){
        String idCurrentUser=this.validateIdCurrentUser("");

        if(idCurrentUser.trim().isEmpty()){
            return;
        }

        this.baseDatos.collection("usuarios")
                .document(idCurrentUser)
                .collection("diarios")
                .get()
                .addOnCompleteListener(query -> {
                    Log.d("SUCCESS", query.isSuccessful()+"");
                    if(query.isSuccessful()){
                        Log.d("GET DIARIOS", query.isSuccessful()+"");
                        List<DiarioGet> diarios = query.getResult().toObjects(DiarioGet.class);
                        listener.getData(diarios);
                    }
                })
                .addOnFailureListener(runnable -> {
                    Log.d("ERROR1", runnable.getCause().getMessage());
                    Log.d("ERROR", runnable.getMessage());
                });


    }

    /**
     * VALIDAR EMAIL DEL USUARIO ACTUAL
     * @param id Almacenara id del usuario actual
     * @return response
     */
    private String validateIdCurrentUser(String id){
        Boolean response = false;

        if (this.authentication.getCurrentUser() == null) {
            return id;
        }
        id = this.authentication.getCurrentUser().getUid();

        if (id==null||id.trim().isEmpty()){
            return "";
        }

        return id;
    }

    private Map<String, Object> convertDiarioPostToMap(DiarioPost diarioPost){

        return Map.of("titulo", diarioPost.getTitulo(),
                "cuerpo", diarioPost.getCuerpo(),
                "fechaCreacion", new Timestamp(new Date()),
                "ultimaActualizacion", new Timestamp(new Date())
        );
    }
}