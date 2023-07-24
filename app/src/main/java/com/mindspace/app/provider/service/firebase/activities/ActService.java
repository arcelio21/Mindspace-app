package com.mindspace.app.provider.service.firebase.activities;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mindspace.app.model.user.ActGet;
import com.mindspace.app.model.user.ActPost;
import com.mindspace.app.usecases.base.ListenerGetFirebase;
import com.mindspace.app.usecases.base.ListenerResponseFirabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ActService {

    private final FirebaseAuth authentication;
    private final FirebaseFirestore baseDatos;

    private ListenerResponseFirabase responseFirabase;

    public ActService() {
        this.baseDatos = FirebaseFirestore.getInstance();
        this.authentication = FirebaseAuth.getInstance();
    }

    public void setResponseFirabase(ListenerResponseFirabase responseFirabase) {
        this.responseFirabase = responseFirabase;
    }


    public void getById(ListenerGetFirebase<ActGet> listenerGetFirebase, String id){

        String idUser = this.validateIdCurrentUser("");
        this.baseDatos.collection("usuarios")
                .document(idUser)
                .collection("diarios")
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ActGet actGet = task.getResult().toObject(ActGet.class);
                        listenerGetFirebase.getData(actGet);
                    }
                })
                .addOnFailureListener(runnable -> {
                    Log.d("Error", runnable.getMessage());
                });
    }
    public void save(ActPost actPost) {

        String idCurrentUser = this.validateIdCurrentUser("");

        if(idCurrentUser.trim().isEmpty()){
            return;
        }

        Map<String,Object> diarioMap = this.convertDiarioPostToMap(actPost);
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
    public void getAll(ListenerGetFirebase<List<ActGet>> listener){
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
                        List<ActGet> diarios = new ArrayList<>();
                        query.getResult().forEach(data ->{
                            ActGet actGet= new ActGet();
                            actGet.setId(data.getId());
                            actGet.setCuerpo(data.get("cuerpo", String.class));
                            actGet.setTitulo(data.get("titulo", String.class));

                            diarios.add(actGet);

                        });
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

    private Map<String, Object> convertDiarioPostToMap(ActPost actPost){

        return Map.of("titulo", actPost.getTitulo(),
                "cuerpo", actPost.getCuerpo());
    }
}
