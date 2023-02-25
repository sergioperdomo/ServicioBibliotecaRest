package com.ceiba.biblioteca.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBookInDTO {

    private String isbn;
    private String identificacionUsuario;
    private Long tipoUsuario;

}
