package com.mindspace.app.model.user;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

/**
 * CUANDO SE PIDA INFO AL LA BD SE UTILIZA ESTA CLASE
 */
@Data
public class DiarioGet {

    private Long id;
    private String titulo;
    private String cuerpo;
    private  String fecha;
}
