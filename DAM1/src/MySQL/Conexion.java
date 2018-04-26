
package MySQL;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Benny
 */
public class Conexion {
    
    Connection con=null;
    
    public Conexion(){  
        con = Conexion.RealizaConexion();
    }

    public Connection getCon() {
        return con;
    }
    
    public static Connection RealizaConexion(){
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dam", "root", "");
            System.out.println("Conectado conexion");
            JOptionPane.showMessageDialog(null, "CONECTADO CON BASE DE DATOS", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.out.println("Error: "+e);
            JOptionPane.showMessageDialog(null, "FAVOR DE INICIALIZAR MySQL Y APACHE(XAMPP)", "MENSAJE DE ERROR", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } 
        return c;
    }
    
    public boolean EjecutarSQL(PreparedStatement sentencia){
        try {
            sentencia.execute();
            sentencia.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al ejecutar SQL: "+e);
            return false;
        }
    }
    
    public ResultSet EjecutarSQLSelect(String sql){
        ResultSet resultado;
        try {
            PreparedStatement sentencia=con.prepareStatement(sql);
            resultado = sentencia.executeQuery();
            return resultado;
        } catch (SQLException e) {
            System.out.println("Error al ejecutar consulta: "+e);
            return null;
        }
    }
    
}
