package com.mindspace.app.model.user;

import com.google.firebase.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CUANDO SE PIDA INFO AL LA BD SE UTILIZA ESTA CLASE
 */
@NoArgsConstructor
@Setter
@Getter
public class ActGet {

    private String id;
    private String titulo;
    private String cuerpo;
}