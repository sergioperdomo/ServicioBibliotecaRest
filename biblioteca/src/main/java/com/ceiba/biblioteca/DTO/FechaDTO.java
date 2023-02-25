package com.ceiba.biblioteca.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FechaDTO {
    private Date fechaInicial;
    private Date fechaFinal;
    private Date fecha;
    private Integer diasHabiles;
}
