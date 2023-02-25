package com.ceiba.biblioteca.service.Impl;

import java.util.*;


public class CalculoFestivosUtilidad {
    private static final int FESTIVOS_AUTOGENERADOS = 10;
    private static final HashMap<Integer, List<Date>> FESTIVOS_PRECALCULADOS = generarFestivos();

    private CalculoFestivosUtilidad() {
        throw new IllegalStateException("Utility class");
    }

    public static String concatenate(String s1, String s2) {
        return s1 + s2;
    }

    private static HashMap<Integer, List<Date>> generarFestivos() {
        HashMap<Integer, List<Date>> festivosLocal = new HashMap();
        Calendar c = Calendar.getInstance();
        int anho = c.get(1);

        for(int i = anho - 10; i < anho + 10; ++i) {
            List<Date> festivosAnho = diasFestivos(i);
            festivosLocal.put(i, festivosAnho);
        }

        return festivosLocal;
    }

    public static List<Date> obtenerFestivos(int anho) {
        return (List)FESTIVOS_PRECALCULADOS.get(anho);
    }

    private static boolean esSabadoDomingo(Calendar fecha) {
        return fecha.get(7) == 7 || fecha.get(7) == 1;
    }

    private static List<Date> diasFestivos(int anio) {
        Date pascua = calcularPascua(anio);
        List<Date> diasFestivos = new ArrayList();
        GregorianCalendar primeroEnero = new GregorianCalendar(anio, 0, 1);
        if (!esSabadoDomingo(primeroEnero)) {
            incluirFecha(diasFestivos, primeroEnero.getTime());
        }

        GregorianCalendar reyesMagos = new GregorianCalendar(anio, 0, 6);
        incluirFecha(diasFestivos, siguienteDiaSemana(2, reyesMagos, false, true));
        GregorianCalendar sanJose = new GregorianCalendar(anio, 2, 19);
        incluirFecha(diasFestivos, siguienteDiaSemana(2, sanJose, false, true));
        Calendar juevesSanto = Calendar.getInstance();
        juevesSanto.setTime(pascua);
        incluirFecha(diasFestivos, siguienteDiaSemana(5, juevesSanto, true, true));
        Calendar viernesSanto = Calendar.getInstance();
        viernesSanto.setTime(pascua);
        incluirFecha(diasFestivos, siguienteDiaSemana(6, viernesSanto, true, true));
        GregorianCalendar diaTrabajo = new GregorianCalendar(anio, 4, 1);
        if (!esSabadoDomingo(diaTrabajo)) {
            incluirFecha(diasFestivos, diaTrabajo.getTime());
        }

        Calendar ascension = Calendar.getInstance();
        ascension.setTime(pascua);
        Date ascensionDate = siguienteDiaSemana(2, ascension, false, true);
        Calendar ascensionCal2 = Calendar.getInstance();
        ascensionCal2.setTime(ascensionDate);
        ascensionCal2.add(5, 42);
        if (!esSabadoDomingo(ascensionCal2)) {
            incluirFecha(diasFestivos, ascensionCal2.getTime());
        }

        Calendar corpusCal2 = Calendar.getInstance();
        corpusCal2.setTime(ascensionDate);
        corpusCal2.add(5, 63);
        if (!esSabadoDomingo(corpusCal2)) {
            incluirFecha(diasFestivos, corpusCal2.getTime());
        }

        Calendar sagradoCorazonCal2 = Calendar.getInstance();
        sagradoCorazonCal2.setTime(ascensionDate);
        sagradoCorazonCal2.add(5, 70);
        if (!esSabadoDomingo(sagradoCorazonCal2)) {
            incluirFecha(diasFestivos, sagradoCorazonCal2.getTime());
        }

        GregorianCalendar sanPedro = new GregorianCalendar(anio, 5, 29);
        incluirFecha(diasFestivos, siguienteDiaSemana(2, sanPedro, false, true));
        GregorianCalendar independencia = new GregorianCalendar(anio, 6, 20);
        if (!esSabadoDomingo(independencia)) {
            incluirFecha(diasFestivos, independencia.getTime());
        }

        GregorianCalendar batallaBoyaca = new GregorianCalendar(anio, 7, 7);
        if (!esSabadoDomingo(batallaBoyaca)) {
            incluirFecha(diasFestivos, batallaBoyaca.getTime());
        }

        GregorianCalendar asuncionVirgen = new GregorianCalendar(anio, 7, 15);
        incluirFecha(diasFestivos, siguienteDiaSemana(2, asuncionVirgen, false, true));
        GregorianCalendar raza = new GregorianCalendar(anio, 9, 12);
        incluirFecha(diasFestivos, siguienteDiaSemana(2, raza, false, true));
        GregorianCalendar todosSantos = new GregorianCalendar(anio, 10, 1);
        incluirFecha(diasFestivos, siguienteDiaSemana(2, todosSantos, false, true));
        GregorianCalendar independenciaCartagena = new GregorianCalendar(anio, 10, 11);
        incluirFecha(diasFestivos, siguienteDiaSemana(2, independenciaCartagena, false, true));
        GregorianCalendar inmaculadaConcepcion = new GregorianCalendar(anio, 11, 8);
        if (!esSabadoDomingo(inmaculadaConcepcion)) {
            incluirFecha(diasFestivos, inmaculadaConcepcion.getTime());
        }

        GregorianCalendar navidad = new GregorianCalendar(anio, 11, 25);
        if (!esSabadoDomingo(navidad)) {
            incluirFecha(diasFestivos, navidad.getTime());
        }

        return diasFestivos;
    }

    private static void incluirFecha(List<Date> listaDias, Date fecha) {
        if (!listaDias.contains(fecha)) {
            listaDias.add(fecha);
        }

    }

    private static Date siguienteDiaSemana(int diaSemana, Calendar fecha, boolean haciaAtras, boolean inclusive) {
        Calendar fechaNueva = fecha;
        if (inclusive) {
            if (fecha.get(7) == diaSemana) {
                return fecha.getTime();
            }
        } else if (haciaAtras) {
            fecha.add(5, -1);
        } else {
            fecha.add(5, 1);
        }

        while(fecha.get(7) != diaSemana) {
            if (haciaAtras) {
                fechaNueva.add(5, -1);
            } else {
                fechaNueva.add(5, 1);
            }
        }

        return fechaNueva.getTime();
    }

    private static Date calcularPascua(int anho) {
        int m = 24;
        int n = 5;
        if (anho >= 1583 && anho <= 1699) {
            m = 22;
            n = 2;
        } else if (anho >= 1700 && anho <= 1799) {
            m = 23;
            n = 3;
        } else if (anho >= 1800 && anho <= 1899) {
            m = 23;
            n = 4;
        } else if (anho >= 1900 && anho <= 2099) {
            m = 24;
            n = 5;
        } else if (anho >= 2100 && anho <= 2199) {
            m = 24;
            n = 6;
        } else if (anho >= 2200 && anho <= 2299) {
            m = 25;
            n = 0;
        }

        int a = anho % 19;
        int b = anho % 4;
        int c = anho % 7;
        int d = (a * 19 + m) % 30;
        int e = (2 * b + 4 * c + 6 * d + n) % 7;
        int dia = d + e;
        return obtenerFechaPascua(dia, Calendar.getInstance(), anho, d, e, a);
    }

    public static Date obtenerFechaPascua(int dia, Calendar calendario, int anho, int d, int e, int a) {
        if (dia < 10) {
            calendario.set(anho, 2, dia + 22, 0, 0, 0);
        } else {
            if (dia == 26) {
                dia = 19;
            } else if (dia == 25 && d == 28 && e == 6 && a > 10) {
                dia = 18;
            } else {
                dia -= 9;
            }

            calendario.set(anho, 3, dia, 0, 0, 0);
        }

        return calendario.getTime();
    }

    public static int numeroDiasFestivos(Calendar fechaInicial, Calendar fechaFinal) {
        int anhoFechaInicial = fechaInicial.get(1);
        int anhoFechaFinal = fechaFinal.get(1);
        List<Date> diasFestivosAnhoInicial = null;
        List<Date> diasFestivosAnhoFinal = null;
        diasFestivosAnhoInicial = (List)FESTIVOS_PRECALCULADOS.get(anhoFechaInicial);
        if (anhoFechaInicial != anhoFechaFinal) {
            diasFestivosAnhoFinal = (List)FESTIVOS_PRECALCULADOS.get(anhoFechaFinal);
        }

        int dias = 0;
        dias += festivoEntreFechas(diasFestivosAnhoInicial, fechaInicial, fechaFinal);
        if (diasFestivosAnhoFinal != null) {
            dias += festivoEntreFechas(diasFestivosAnhoFinal, fechaInicial, fechaFinal);
        }

        return dias;
    }

    private static int festivoEntreFechas(List<Date> diasFestivos, Calendar fechaInicial, Calendar fechaFinal) {
        int dias = 0;
        Iterator var4 = diasFestivos.iterator();

        while(true) {
            Calendar c;
            do {
                do {
                    if (!var4.hasNext()) {
                        return dias;
                    }

                    Date fechaFestivo = (Date)var4.next();
                    c = Calendar.getInstance();
                    c.setTime(fechaFestivo);
                } while(!c.equals(fechaInicial) && !c.after(fechaInicial));
            } while(!c.equals(fechaFinal) && !c.before(fechaFinal));

            ++dias;
        }
    }

    public static int getCantidadSabadosDomingos(Calendar fechaInicial, Calendar fechaFinal) {
        int diasFinSemana;
        for(diasFinSemana = 0; fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal); fechaInicial.add(5, 1)) {
            if (fechaInicial.get(7) == 1 || fechaInicial.get(7) == 7) {
                ++diasFinSemana;
            }
        }

        return diasFinSemana;
    }
}
