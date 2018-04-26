package MySQL;

import CONTROL.FechaYHora;
import INICIO.Main;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DatosDeMySQL {

    Conexion con = Main.hc;
    CargarDatos c = new CargarDatos();

    public void MostrarLista(DefaultTableModel model, JTable TablaDatos) {

        try {
            String sql;
            sql = "SELECT `Matricula`, `Nombre`, `Semestre`, `Celular`, `Medico`,"
                    + " `Alergias`, `Medicamentos` FROM `alumnos` "
                    + "WHERE 1 ORDER BY`Matricula` ASC";
            ResultSet rs = con.EjecutarSQLSelect(sql);
            c.CargarTabla(7, rs, model, TablaDatos);
        } catch (Exception e) {
            System.out.println("Error al Cargar Tabla Dal: " + e);
        }
    }

    public void MostrarListaIMC(DefaultTableModel model, JTable TablaDatos) {

        try {
            String sql;
            sql = "SELECT `Matricula`, `Nombre`, `IMC`, `Situacion`"
                    + " FROM `alumnos` WHERE  1 ORDER BY `Matricula`";
            ResultSet rs = con.EjecutarSQLSelect(sql);
            c.CargarTabla(4, rs, model, TablaDatos);
        } catch (Exception e) {
            System.out.println("Error al Cargar Tabla Dal: " + e);
        }
    }

    public void BuscarLista(DefaultTableModel model, JTable TablaDatos, String dato) {

        try {
            String sql;
            sql = "SELECT `Matricula`, `Nombre`, `Semestre`, `Celular`"
                    + ", `Medico`, `Alergias`, `Medicamentos` FROM `alumnos` "
                    + "WHERE 1 AND `Nombre` LIKE \"%" + dato + "%\" OR `Matricula` LIKE\"%" + dato + "%\"";
            ResultSet rs = con.EjecutarSQLSelect(sql);
            c.CargarTabla(5, rs, model, TablaDatos);
        } catch (Exception e) {
            System.out.println("Error al Cargar Tabla Dal: " + e);
        }
    }

    public void InsertarDatos(Usuario u) {
        try {
            String sql;
            sql = "INSERT INTO `alumnos`(`Matricula`, `Nombre`, `Semestre`, `Celular`, `Medico`, `Alergias`,"
                    + " `Medicamentos`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, u.getMatricula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getSemestre());
            ps.setString(4, u.getCelular());
            ps.setString(5, u.getMedico());
            ps.setString(6, u.getAlergias());
            ps.setString(7, u.getMedicamentos());
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "Usuario correctamente registrado");
            } else if (ejecucion = false) {
                JOptionPane.showMessageDialog(null, "Error al insertar Usuario");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al insertar en DatosDeMySQL:" + e);
        }
    }

    public void InsertarIMC(Usuario u) {
        try {
            String Mat = String.valueOf(u.getMatricula());
            String sql;
            sql = "UPDATE `alumnos` SET `IMC`=?,`Situacion`=? WHERE `Matricula`=" + Mat;
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getIMC());
            ps.setString(2, u.getSituacion());
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "IMC correctamente registrado");
            } else if (ejecucion = false) {
                JOptionPane.showMessageDialog(null, "Error al insertar IMC");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al insertar IMC en DatosDeMySQL:" + e);
        }
    }

    public void ModificarDatos(Usuario u) {
        try {
            String sql;
            sql = "UPDATE `alumnos` SET `Nombre`=?,"
                    + "`Semestre`=?,`Celular`=?,`Medico`=?,"
                    + "`Alergias`=?,`Medicamentos`=?"
                    + " WHERE `Matricula` = " + u.getMatricula();
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getSemestre());
            ps.setString(3, u.getCelular());
            ps.setString(4, u.getMedico());
            ps.setString(5, u.getAlergias());
            ps.setString(6, u.getMedicamentos());
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "Usuario correctamente registrado");
            } else if (ejecucion = false) {
                JOptionPane.showMessageDialog(null, "Error al insertar Usuario");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al insertar en DatosDeMySQL:" + e);
        }

    }

    public Object[] ConsultarPorMatricula(int Matricula) {
        Object[] datos = new Object[6];
        try {
            String sql;
            sql = "SELECT `Nombre`, `Semestre`, `Celular`,"
                    + " `Medico`, `Alergias`, `Medicamentos` "
                    + "FROM `alumnos` WHERE `Matricula` = ?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, Matricula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);//NOMBRE
                datos[1] = rs.getString(2);//SEMESTRE
                datos[2] = rs.getString(3);//CELULAR
                datos[3] = rs.getString(4);//MEDICO
                datos[4] = rs.getString(5);//ALERGIAS
                datos[5] = rs.getString(6);//MEDICAMENTOS
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar en DatosDeMySQL: " + e);
        }

        return datos;
    }

    public String ConsultarNombrePorMatricula(int Matricula) {
        String nombre = null;
        Object[] datos = new Object[1];
        try {
            String sql;
            sql = "SELECT `Nombre` "
                    + "FROM `alumnos` WHERE `Matricula` = ?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, Matricula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);//NOMBRE
            }
            nombre = (String) datos[0];
        } catch (SQLException e) {
            System.out.println("Error al consultar Nombre por Matricula en DatosDeMySQL: " + e);
        }
        return nombre;
    }

    public void EliminarUsuario(Usuario u) {
        try {
            String sql;
            sql = "DELETE FROM `alumnos` WHERE `Matricula`=?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, u.getMatricula());
            boolean ejecucion = con.EjecutarSQL(ps);
        } catch (Exception e) {
            System.out.println("Error al Borrar Usuario NOT FUND 404 XD: " + e);
        }
    }

    //DESDE ESTE PUNTO CREAN TABLAS,ASITENCIA Y ALUMNO (SE TOMA ASISTENCIA)
    public boolean ConsultarTabla(String Mes, String Anual, String Deporte) {
        boolean Encontrado = false;
        try {
            String sql;
            sql = "SELECT `Nombre` FROM `" + Deporte + "-" + Mes + "-" + Anual + "` WHERE 1;";
            PreparedStatement ps = con.con.prepareStatement(sql);
            Encontrado = con.EjecutarSQL(ps);
        } catch (SQLException e) {
            System.out.println("Error al Consultar Existencia de Tabla: " + e);
        }
        return Encontrado;
    }

    public boolean CrearTabla(String Dia, String Mes, String Anual, String Deporte) {
        boolean Funciona = false;
        try {
            String sql;
            sql = "CREATE TABLE `dam`.`" + Deporte + "-" + Mes + "-" + Anual + "` (`Matricula` INT(7) NOT NULL ,"
                    + " `Nombre` VARCHAR(80) NULL DEFAULT 'No Registrado') ENGINE = InnoDB;";
            PreparedStatement ps = con.con.prepareStatement(sql);
            Funciona = con.EjecutarSQL(ps);
        } catch (Exception e) {
            System.out.println("Error al Crear Base de datos tabla: " + e);
        }
        return Funciona;
    }

    public boolean AgregarColumnaDia(String Dia, String Mes, String Anual, String Deporte) {
        boolean Agregado = false;
        try {
            String sql;
            sql = "ALTER TABLE `" + Deporte + "-" + Mes + "-" + Anual + "` ADD `" + Dia + "` VARCHAR(2) NULL DEFAULT 'NA' AFTER `Nombre`;";
            PreparedStatement ps = con.con.prepareStatement(sql);
            Agregado = con.EjecutarSQL(ps);
        } catch (Exception e) {
            System.out.println("Error al agregar columna");
        }
        return Agregado;
    }

    public boolean ConsultarColumna(String Dia, String Mes, String Anual, String Deporte) {
        boolean Encontrado = false;
        try {
            String sql;
            sql = "SELECT `Nombre` FROM `" + Deporte + "-" + Mes + "-" + Anual + "` WHERE `" + Dia + "`;";
            PreparedStatement ps = con.con.prepareStatement(sql);
            Encontrado = con.EjecutarSQL(ps);
        } catch (SQLException e) {
            System.out.println("Error al Consultar Existencia de Columna: " + e);
        }
        return Encontrado;
    }

    public void InsertarAsistencia(String Dia, String Mes, String Anual, String Deporte, String Nombre, String Matricula) {
        try {
            String sql;
            sql = "INSERT INTO `" + Deporte + "-" + Mes + "-" + Anual + "`(`Matricula`, `Nombre`, `" + Dia + "`) VALUES (?,?,?);";

            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, Matricula);
            ps.setString(2, Nombre);
            ps.setString(3, "A");
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "Asistencia correctamente registrada");
            } else if (ejecucion = false) {
                JOptionPane.showMessageDialog(null, "Error al insertar Asistencia");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al insertar IMC en DatosDeMySQL:" + e);
        }
    }

    public void ActualizarAsistencia(String Dia, String Mes, String Anual, String Deporte, String Nombre, String Matricula) {
        try {
            String sql;
            sql = "UPDATE `" + Deporte + "-" + Mes + "-" + Anual + "` SET `Matricula`=?,`Nombre`=?,`" + Dia + "`=? WHERE `Matricula`=?;";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setString(1, Matricula);
            ps.setString(2, Nombre);
            ps.setString(3, "A");
            ps.setString(4, Matricula);
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "Asistencia correctamente registrada");
            } else if (ejecucion = false) {
                JOptionPane.showMessageDialog(null, "Error al insertar Asistencia");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al insertar IMC en DatosDeMySQL:" + e);
        }
    }

    public boolean BuscarAlumnoAsistencia(String Dia, String Mes, String Anual, String Deporte, int Matricula) {
        boolean encontrado = false;
        String MatriculaS = String.valueOf(Matricula);
        Object[] datos = new Object[1];
        String sql;
        String nombre = null;
        try {
            sql = "SELECT `Nombre` FROM `" + Deporte + "-" + Mes + "-" + Anual + "` WHERE `Matricula`=?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, Matricula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);//NOMBRE
            }
            nombre = (String) datos[0];
            if (nombre != null) {
                encontrado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar alumno: " + e);
        }
        return encontrado;
    }

    //MYSQL DE REGISTRO DE PRESTAMOS
    public void MostrarListaPrestamo(DefaultTableModel model, JTable TablaDatos) {
        try {
            String sql;
            sql = "SELECT `Matricula`, `Nombre`, `HOMBRERAS`, `CASCO`, "
                    + "`ROPA/PRACTICA`, `ROPA/JUEGO`, `GUARDAS`, "
                    + "`RODILLERAS`, `RIFIONERAS`, `COSTILLERAS`, "
                    + "`CUELLERA`, `CODERA`, `COLERA`, `ANTEBRAZERO`, "
                    + "`BARBIQUEJO`, `OREJERAS` "
                    + "FROM `prestamos-americano` WHERE 1";
            ResultSet rs = con.EjecutarSQLSelect(sql);
            c.CargarTabla(16, rs, model, TablaDatos);
        } catch (Exception e) {
            System.out.println("Error al Cargar Tabla Prestamo Dal: " + e);
        }
    }

    /*
    CREATE TABLE `dam`.`Prestamos` ( `Matricula` INT(7) NOT NULL , `Nombre` VARCHAR(20) NOT NULL , `Hombreras` VARCHAR(8) NULL DEFAULT 'NP' , `Casco` VARCHAR(8) NULL DEFAULT 'NP' , `Ropa/Practica` VARCHAR(8) NULL DEFAULT 'NP' , `Ropa/Juego` VARCHAR(8) NULL DEFAULT 'NP' , `Guardas` VARCHAR(8) NULL DEFAULT 'NP' , `Rodilleras` VARCHAR(8) NULL DEFAULT 'NP' , `Rifioneras` VARCHAR(8) NULL DEFAULT 'NP' , `Costilleras` VARCHAR(8) NULL DEFAULT 'NP' , `Cuellera` VARCHAR(8) NULL DEFAULT 'NP' , `Codera` VARCHAR(8) NULL DEFAULT 'NP' , `Colera` VARCHAR(8) NULL DEFAULT 'NP' , `Antebrazo` VARCHAR(8) NULL DEFAULT 'NP' , `Barbiquejo` VARCHAR(8) NULL DEFAULT 'NP' , `Orejeras` VARCHAR(8) NULL DEFAULT 'NP' ) ENGINE = InnoDB;

    \n
    CREATE TABLE `dam`.`PrestamosG` ( `Matricula` INT(7) NOT NULL , `Nombre` VARCHAR(80) NOT NULL , `Deporte` VARCHAR(80) NOT NULL , `FechaPrestamo` VARCHAR(12) NULL DEFAULT 'NO PRESTO' , `FechaEntrega` VARCHAR(12) NULL DEFAULT 'NO ENTREGADO' ) ENGINE = InnoDB;
     */
    
    public boolean BuscarAlumnoPrestamo(int Matricula) {
        boolean encontrado = false;
        Object[] datos = new Object[1];
        String sql;
        String nombre = null;
        try {
            sql = "SELECT `Nombre` FROM `prestamos-americano` WHERE `Matricula`=?";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, Matricula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString(1);//NOMBRE
            }
            nombre = (String) datos[0];
            if (nombre != null) {
                encontrado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar alumno: " + e);
        }
        return encontrado;
    }
    
    public void InsertarFechaPrestamo(Usuario u,String Fecha,String FechaPE, String Deporte) {
        try {
            String sql;
            sql = "INSERT INTO `prestamosg`(`Matricula`, `Nombre`, `Deportes`, `"+FechaPE+"`)"
                    + " VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.con.prepareStatement(sql);
            ps.setInt(1, u.getMatricula());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getDeporte());
            ps.setString(4, Fecha);
            boolean ejecucion = con.EjecutarSQL(ps);
            
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al insertar en DatosDeMySQL:" + e);
        }
    }
    
    public void InsertarPrestamo(Usuario u, String Deporte) {
        try {
            String sql;
            sql = "INSERT INTO `prestamos-"+Deporte+"`"
                    + "(`Matricula`, `Nombre`, `"+u.getMaterial()+"`) "
                    + "VALUES (?,?,?)";
            PreparedStatement ps = con.con.prepareStatement(sql);
            String Registro = u.getMarca()+"/"+u.getSize();
            ps.setInt(1, u.getMatricula());
            ps.setString(2, u.getNombre());
            ps.setString(3, Registro);
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "Prestamo correctamente INSERTADO");
            } else {
                JOptionPane.showMessageDialog(null, "Error al resgistrar prestamo");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al insertar en DatosDeMySQL:" + e);
        }
    }
    
    public void ActualizarPrestamo(Usuario u, String Deporte) {
        try {
            String sql;
            sql = "UPDATE `prestamos-"+Deporte+"` SET `"+u.getMaterial()+"`=? WHERE `Matricula`=?;";
            PreparedStatement ps = con.con.prepareStatement(sql);
            String Registro = u.getMarca()+"/"+u.getSize();
            ps.setString(1, Registro);
            ps.setInt(2, u.getMatricula());
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "Prestamo correctamente ACTULIZADO");
            } else if (ejecucion = false) {
                JOptionPane.showMessageDialog(null, "Error al actualizar prestamo");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al actualizar prestamo en DatosDeMySQL:" + e);
        }
    }
    
    public void EntregarEquipo(Usuario u, String Deporte) {
        try {
            String sql;
            sql = "UPDATE `prestamos-"+Deporte+"` SET `"+u.getMaterial()+"`=? WHERE `Matricula`=?;";
            System.out.println(sql);
            PreparedStatement ps = con.con.prepareStatement(sql);
            String Registro = "ENTREGADO";
            ps.setString(1, Registro);
            ps.setInt(2, u.getMatricula());
            boolean ejecucion = con.EjecutarSQL(ps);
            if (ejecucion = true) {
                JOptionPane.showMessageDialog(null, "Prestamo correctamente ACTULIZADO");
            } else if (ejecucion = false) {
                JOptionPane.showMessageDialog(null, "Error al insertar actualizar entrega");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Error al actualizar prestamo en DatosDeMySQL:" + e);
        }
    }
    
    public void BuscarListaPrestamo(DefaultTableModel model, JTable TablaDatos, String dato) {

        try {
            String sql;
            sql= "SELECT `Matricula`, `Nombre`, `HOMBRERAS`, `CASCO`, "
                    + "`ROPA/PRACTICA`, `ROPA/JUEGO`, `GUARDAS`, "
                    + "`RODILLERAS`, `RIFIONERAS`, `COSTILLERAS`, "
                    + "`CUELLERA`, `CODERA`, `COLERA`, `ANTEBRAZERO`, "
                    + "`BARBIQUEJO`, `OREJERAS` "
                    + "FROM `prestamos-americano` WHERE 1 "
                    + "AND `Nombre` LIKE \"%" + dato + "%\" OR `Matricula` LIKE\"%" + dato + "%\"";
           
            ResultSet rs = con.EjecutarSQLSelect(sql);
            c.CargarTabla(16, rs, model, TablaDatos);
        } catch (Exception e) {
            System.out.println("Error al Cargar Tabla Dal: " + e);
        }
    }
}
