
package CONTROL;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechaYHora {
    
    String hora,minutos,segundos;
    Thread hilo;
    
//    public FechaYHora() {
//        hilo = new Thread(this);
//        hilo.start();
//    }
    
    public void hora(){
        Calendar calendario = new GregorianCalendar();
        Date HoraActual = new Date();
        calendario.setTime(HoraActual);
        hora = calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND);
    }
    
    public String fecha(){
        Date fecha = new Date();
        SimpleDateFormat formatofecha = new SimpleDateFormat("dd/MM/YYYY");
        return formatofecha.format(fecha);
    }
    
    public String Dia() {
        String Dia = fecha().substring(0, 2);
        return Dia;
    }

    public String Mes() {
        String Mesp = fecha().substring(3);
        String Mes = Mesp.substring(0, 2);
        return Mes;
    }

    public String Anual() {
        String An = fecha().substring(6);
        return An;
    }
}
