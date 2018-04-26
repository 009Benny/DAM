
package CONTROL;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *
 * @author Benny
 */
public class Validar {
        
    public void ValidarSoloLetras(JTextField campo){
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if(Character.isDigit(c)){
                    e.consume();
                }
            }
    });
    }
    
    public void ValidarSoloNumeros(JTextField campo){
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if(!Character.isDigit(c)){
                    e.consume();
                }
            }
    });
    }
    
    public void ValidarFloat(JTextField campo){
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                Character c = e.getKeyChar();
                if(!Character.isDigit(c)){
                    if(c.equals(".")){
                        e.consume();
                    }
                }
            }
    });
    }
    
    public void LimitarCaracteres(JTextField campo,int cantidad){
        campo.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                int tam=campo.getText().length();
                if(tam>=cantidad){
                    e.consume();
                }
            }
    });
    }
    
    public boolean ValidarCorreo(String correo){
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        
        if(mat.find()){
            return true;
        }else{
            return false;
        }
    }
}
