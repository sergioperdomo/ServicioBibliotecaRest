package com.ceiba.biblioteca.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UsuarioBookDTO {

    private Long id;
    private  String isbn;
    private String identificacionUsuario;
    private Long tipoUsuario;
    private String fechaMaximaDevolucion;


}
