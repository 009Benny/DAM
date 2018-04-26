package CONTROL;

import MySQL.DatosDeMySQL;
import MySQL.Usuario;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Benny
 */
public class PasoMetodosUsuario {
    
    DatosDeMySQL dal = new DatosDeMySQL();
    
    public void MostrarLista(DefaultTableModel model, JTable TablaDatos) {
        dal.MostrarLista(model, TablaDatos);
    }
    
    public void BuscarLista(DefaultTableModel model, JTable TablaDatos, String dato) {
        dal.BuscarLista(model, TablaDatos, dato);
    }
    
    public void InsertarDatos(Usuario u) {
        dal.InsertarDatos(u);
    }
    
    public void EliminarUsuario(Usuario u) {
        dal.EliminarUsuario(u);
    }
    
    public void MostrarListaIMC(DefaultTableModel model, JTable TablaDatos) {
        dal.MostrarListaIMC(model, TablaDatos);
    }
    
    public Object[] ConsultarPorMatricula(int Matricula) {
        return dal.ConsultarPorMatricula(Matricula);
    }
    
    public void InsertarIMC(Usuario u) {
        dal.InsertarIMC(u);
    }
    
    public boolean ConsultarTabla(String Mes, String Anual, String Deporte) {
        return dal.ConsultarTabla(Mes, Anual, Deporte);
    }
    
    public boolean CrearTabla(String Dia, String Mes, String Anual, String Deporte) {
        return dal.CrearTabla(Dia, Mes, Anual, Deporte);
    }
    
    public boolean AgregarColumnaDia(String Dia, String Mes, String Anual, String Deporte) {
        return dal.AgregarColumnaDia(Dia, Mes, Anual, Deporte);
    }
    
    public boolean ConsultarColumna(String Dia, String Mes, String Anual, String Deporte) {
        return dal.ConsultarColumna(Dia, Mes, Anual, Deporte);
    }
    
    public String ConsultarNombrePorMatricula(int Matricula) {
        return dal.ConsultarNombrePorMatricula(Matricula);
    }
    
    public void InsertarAsistencia(String Dia, String Mes, String Anual, String Deporte, String Nombre, String Matricula) {
        dal.InsertarAsistencia(Dia, Mes, Anual, Deporte, Nombre, Matricula);
    }
    
    public void ActualizarAsistencia(String Dia, String Mes, String Anual, String Deporte, String Nombre, String Matricula) {
        dal.ActualizarAsistencia(Dia, Mes, Anual, Deporte, Nombre, Matricula);
    }
    
    public boolean BuscarAlumnoAsistencia(String Dia, String Mes, String Anual, String Deporte, int Matricula) {
        return dal.BuscarAlumnoAsistencia(Dia, Mes, Anual, Deporte, Matricula);
    }
    
    public void MostrarListaPrestamo(DefaultTableModel model, JTable TablaDatos) {
        dal.MostrarListaPrestamo(model, TablaDatos);
    }
    
    public void InsertarFechaPrestamo(Usuario u, String Fecha, String FechaPE,String Deporte) {
        dal.InsertarFechaPrestamo(u, Fecha, FechaPE, Deporte);
    }
        
    public boolean BuscarAlumnoPrestamo(int Matricula) {
        return dal.BuscarAlumnoPrestamo(Matricula);
    }
    
    public void InsertarPrestamo(Usuario u, String Deporte) {
        dal.InsertarPrestamo(u, Deporte);
    }
    
    public void ActualizarPrestamo(Usuario u, String Deporte) {
        dal.ActualizarPrestamo(u, Deporte);
    }
    
    public void EntregarEquipo(Usuario u, String Deporte) {
        dal.EntregarEquipo(u, Deporte);
    }

    public void BuscarListaPrestamo(DefaultTableModel model, JTable TablaDatos, String dato){
        dal.BuscarListaPrestamo(model, TablaDatos, dato);
    }
    
}
