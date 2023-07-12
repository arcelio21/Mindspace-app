package com.mindspace.app.provider.service.firebase.diario;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mindspace.app.model.user.DiarioPost;
import com.mindspace.app.model.user.UsuarioPost;
import com.mindspace.app.usecases.base.ListenerAuthentication;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DiarioService {

    private final FirebaseAuth authentication;
    private final FirebaseFirestore baseDatos;

    public DiarioService() {
        this.baseDatos = FirebaseFirestore.getInstance();
        this.authentication = FirebaseAuth.getInstance();
    }

    public void save(DiarioPost diarioPost) {
        if (this.authentication.getCurrentUser() == null) {
            return;
        }
        String email = this.authentication.getCurrentUser().getEmail();

        if (email==null||email.trim().isEmpty()){
            return;
        }
        Task<Void> response  = baseDatos
                .collection("usuarios").document(email)
                .collection("diarios").document().set(diarioPost);
        response.addOnSuccessListener(task->{

        });
    }
}