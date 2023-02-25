package com.ceiba.biblioteca.service.Impl;

import com.ceiba.biblioteca.DTO.FechaDTO;
import com.ceiba.biblioteca.service.IfechaService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class FechaServiceImpl implements IfechaService {

    @Override
    public FechaDTO sumarDiasHabilesAFecha(Date fecha, Integer diasHabiles) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        fecha = calendar.getTime();

        Integer totalDiasHabiles = 0;
        while (totalDiasHabiles < diasHabiles) {
            Boolean esDiaFinDeSemana = esSabadoDomingo(calendar);
            Boolean esFestivo = false;
            if (!esDiaFinDeSemana && !esFestivo) {
                totalDiasHabiles++;
            }
            if (totalDiasHabiles != diasHabiles) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                fecha = calendar.getTime();
            }
        }
        FechaDTO fechaObject = new FechaDTO();
        fechaObject.setDiasHabiles(totalDiasHabiles);
        fechaObject.setFecha(fecha);
        return fechaObject;
    }

    public Boolean esSabadoDomingo(Calendar fecha) {
        return fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }
}
