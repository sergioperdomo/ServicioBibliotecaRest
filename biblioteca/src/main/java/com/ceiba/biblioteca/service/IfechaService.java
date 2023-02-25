package com.ceiba.biblioteca.service;

import com.ceiba.biblioteca.DTO.FechaDTO;

import java.util.Date;

public interface IfechaService {

    public FechaDTO sumarDiasHabilesAFecha(Date fecha, Integer diasHabiles) throws Exception;
}
