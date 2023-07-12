package com.mindspace.app.model.user;


import java.util.Date;

import lombok.Builder;
import lombok.Getter;

/**
 * CUANDO SE ENVIE DATOS A LA BD

 */
@Builder
@Getter
public class DiarioPost {

    private String titulo;
    private String cuerpo;


}

