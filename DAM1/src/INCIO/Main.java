
package INICIO;

import FRAME.INTERFAZ;
import MySQL.Conexion;

/**
 *
 * @author Benny
 */
public class Main {
    public static Conexion hc;
    
    public static void main(String[] args) {
        try {
            hc = new Conexion();
            
            System.out.println("Main conectado");
            INTERFAZ intz = new INTERFAZ();
            intz.setVisible(true);
            
        } catch (Exception e) {
            System.out.println("Error en Main: "+e);
        }
    }
}
