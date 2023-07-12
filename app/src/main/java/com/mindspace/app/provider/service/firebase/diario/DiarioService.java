package com.mindspace.app.provider.service.firebase.diario;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mindspace.app.model.user.DiarioPost;

public class DiarioService {

    private final FirebaseAuth authentication;
    private final FirebaseFirestore baseDatos;

    public DiarioService() {
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
        response.addOnSuccessListener(task->{

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