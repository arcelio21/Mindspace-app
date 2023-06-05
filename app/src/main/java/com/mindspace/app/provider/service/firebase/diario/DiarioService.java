package com.mindspace.app.provider.service.firebase.diario;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mindspace.app.usecases.base.ListenerResponseFirabase;

public class DiarioService {

    private FirebaseFirestore firestore;
    private ListenerResponseFirabase responseFirabaseListener;

    public DiarioService(ListenerResponseFirabase responseFirabaseListener) {
        this.responseFirabaseListener = responseFirabaseListener;
        this.firestore = FirebaseFirestore.getInstance();
    }
}
