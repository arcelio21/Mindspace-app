package com.mindspace.app.provider.service.firebase.diario;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mindspace.app.model.user.DiarioPost;
import com.mindspace.app.usecases.base.ListenerResponseFirabase;

public class DiarioService {

    private final FirebaseAuth authentication;
    private final FirebaseFirestore baseDatos;
    private final ListenerResponseFirabase responseFirabase;

    public DiarioService(ListenerResponseFirabase responseFirabase) {
        this.responseFirabase = responseFirabase;
        this.baseDatos = FirebaseFirestore.getInstance();
        this.authentication = FirebaseAuth.getInstance();
    }

    public void save(DiarioPost diarioPost) {

        String emailCurrentUser = "";

        if(!this.validateEmailCurrentUser(emailCurrentUser)){
            return;
        }

        Task<Void> response  = baseDatos
                .collection("usuarios").document(emailCurrentUser)
                .collection("diarios").document().set(diarioPost);
        response.addOnCompleteListener(task -> {
            this.responseFirabase.notifyChange(task.isSuccessful());
        });
    }

    /**
     * VALIDAR EMAIL DEL USUARIO ACTUAL
     * @param email Almacenara email del usuario actual
     * @return response
     */
    private Boolean validateEmailCurrentUser(String email){
        Boolean response = false;

        if (this.authentication.getCurrentUser() == null) {
            return response;
        }
        email = this.authentication.getCurrentUser().getEmail();

        if (email==null||email.trim().isEmpty()){
            return response;
        }

        return true;
    }
}