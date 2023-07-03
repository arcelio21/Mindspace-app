package com.mindspace.app.model.user;

import lombok.Builder;
import lombok.Getter;

/**
 * SE UTILIZARA CUANDP SE ENVIE DATOS A LA BD
 */
@Builder
@Getter
public class UsuarioPost{

    private String email;
    private String nombre;
    private String apellido;
    private Integer edad;
}
