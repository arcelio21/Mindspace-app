package com.mindspace.app.model.user;

import java.util.List;

import lombok.Data;

/**
 * SE UTILIZAR CUANDO SE PIDA DATOS DEL USAURIO A LA BD
 */
@Data
public class UsuarioGet{

    private String email;
    private String nombre;
    private String apellido;
    private Integer edad;
}
