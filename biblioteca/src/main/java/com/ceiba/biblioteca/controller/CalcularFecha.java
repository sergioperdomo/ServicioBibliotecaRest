package com.ceiba.biblioteca.controller;

import com.ceiba.biblioteca.DTO.FechaDTO;
import com.ceiba.biblioteca.service.IfechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fecha")
public class CalcularFecha {

    @Autowired
    IfechaService fechaService;
    @PostMapping(path = "/sumar-dias-habiles-fecha", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FechaDTO> sumarDiasHabilesAFecha(@RequestBody FechaDTO fechaDTO)
            throws Exception {
        return new ResponseEntity<>(
                fechaService.sumarDiasHabilesAFecha(fechaDTO.getFechaInicial(), fechaDTO.getDiasHabiles()),
                HttpStatus.OK);
    }
}
