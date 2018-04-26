
package MySQL;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Benny
 */
public class CargarDatos {
    public void CargarTabla(int columnas, ResultSet rs, DefaultTableModel model, JTable TablaDatos){
        
        try {
            Object[] filas = new Object[columnas];
            //ColorCelda celda = new ColorCelda();
            
           //Recorrer filas
            while(rs.next()){
                //Recorrer columnas
                for(int i=0;i<columnas;i++){
                    filas[i]=rs.getObject(i+1);
                    
                }
                model.addRow(filas);
            }
            TablaDatos.updateUI();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al Cargar Tabla Datos");
        }
    }
    
    
}
