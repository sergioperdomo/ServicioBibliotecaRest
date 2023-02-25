package com.ceiba.biblioteca.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table (name = "usuariobook")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "isbn")
    private String isbn;

    @NotNull
    @Column(name = "identificacion_usuario")
    private String identificacionUsuario;

    @Column(name = "tipo_usuario")
    private Long tipoUsuario;

    @Column(name = "fecha_final")
    private Date fechaMaximaDevolucion;

}
