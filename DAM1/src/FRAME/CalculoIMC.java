package FRAME;

import CONTROL.PasoMetodosUsuario;
import CONTROL.Validar;
import EXCEL.GenerarExcel;
import MySQL.Usuario;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import jxl.write.WriteException;

/**
 * AUTOR: BENNY REYES SOSA
 * CORREO: 009bennyreyes@gmail.com 
 * CELULAR: 8119193915
 */
public class CalculoIMC extends javax.swing.JFrame {

    Validar v = new Validar();
    PasoMetodosUsuario bll = new PasoMetodosUsuario();
    Usuario u = new Usuario();
    DefaultTableModel modelo_tablaIMC;
    boolean consultar;

    public CalculoIMC() {
        initComponents();
        Validaciones();
        modelo_tablaIMC = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        TablaIMC.setModel(modelo_tablaIMC);
        modelo_tablaIMC.addColumn("Matricula");
        modelo_tablaIMC.addColumn("Nombre");
        modelo_tablaIMC.addColumn("IMC");
        modelo_tablaIMC.addColumn("SITUACION");
        bll.MostrarListaIMC(modelo_tablaIMC, TablaIMC);
        //para que no se muevan las columnas
        TablaIMC.getTableHeader().setReorderingAllowed(false);
    }

    protected void Validaciones() {
        v.ValidarSoloNumeros(txtMatricula);
        v.LimitarCaracteres(txtMatricula, 7);
        v.ValidarFloat(txtPeso);
        v.LimitarCaracteres(txtPeso, 6);
        v.ValidarFloat(txtAltura);
        v.LimitarCaracteres(txtAltura, 6);

    }

    public void CalcularIMC() {
        String pesoS = txtPeso.getText();
        String alturaS = txtAltura.getText();
        String Mat = txtMatricula.getText();
        btnGuardar.setEnabled(false);
        if (!(alturaS.isEmpty() || pesoS.isEmpty())) {
            float peso = Float.parseFloat(pesoS);
            float altura = Float.parseFloat(alturaS);
            float IMC = (peso / (altura * altura));
            String IMCS = String.valueOf(IMC);
            lblIMC.setText(IMCS);
            if (!(Mat.isEmpty())) {
                btnGuardar.setEnabled(true);
            }
        }
    }

    public void ActualizarTabla() {
        //Limpiar
        while (modelo_tablaIMC.getRowCount() > 0) {
            modelo_tablaIMC.removeRow(0);

        }
        //cargar
        bll.MostrarListaIMC(modelo_tablaIMC, TablaIMC);
    }

    public void Buscar() {
        String dato = txtBuscar.getText();
        if (dato.isEmpty()) {
            ActualizarTabla();
        } else if (!dato.isEmpty()) {
            while (modelo_tablaIMC.getRowCount() > 0) {
                modelo_tablaIMC.removeRow(0);
            }
            bll.BuscarLista(modelo_tablaIMC, TablaIMC, dato);
        }
    }

    public void LimpiarTodo() {
        txtMatricula.setText(null);
        txtPeso.setText(null);
        txtAltura.setText(null);
        lblIMC.setText(null);
        btnGuardar.setEnabled(false);
    }

    public void GuardarIMC() {
        String Situacion = null;
        String MatriculaS = txtMatricula.getText();
        int Matricula = Integer.parseInt(MatriculaS);
        String IMC = lblIMC.getText();
        float IMCF = Float.parseFloat(IMC);
        if (IMCF < 18) {
            Situacion = "DESNUTRICION";
            System.out.println(Situacion);
        } else if (IMCF < 27) {
            Situacion = "NORMAL";
            System.out.println(Situacion);
        } else if (IMCF < 30) {
            Situacion = "OBESIDAD 1";
            System.out.println(Situacion);
        } else if (IMCF < 40) {
            Situacion = "OBESIDAD 2";
            System.out.println(Situacion);
        } else if (IMCF > 40) {
            Situacion = "OBESIDAD 3";
            System.out.println(Situacion);
        }
        
        

        Object[] datos = bll.ConsultarPorMatricula(Matricula);
        String semestre = datos[1].toString();
        if (semestre.equals("1") || semestre.equals("2") || semestre.equals("3")
                || semestre.equals("4") || semestre.equals("5") || semestre.equals("6")
                || semestre.equals("7") || semestre.equals("8") || semestre.equals("9")) {
            consultar = true;
        }
        if (consultar == true) {
            u.setMatricula(Matricula);
            u.setIMC(IMC);
            u.setSituacion(Situacion);
            bll.InsertarIMC(u);
        } else {
            JOptionPane.showMessageDialog(null, "La matricula no esta registrada");
        }
        LimpiarTodo();
        ActualizarTabla();
    }
    
        protected String[][] TablaDeIMC(JTable TablaD) {

        TableModel ModeloTabla = TablaD.getModel();
        int col = ModeloTabla.getColumnCount();
        int fil = ModeloTabla.getRowCount();
        String[][] TablaU = new String[fil+1][col];
        TablaU[0][0]="Matricula";
        TablaU[0][1]="Nombre";
        TablaU[0][2]="IMC";
        TablaU[0][3]="SITUACION";
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                if (j == 0) {
                    int matp = (int) ModeloTabla.getValueAt(i, j);
                    TablaU[i+1][j] = Integer.toString(matp);
                } else if(j==2){
                    float imcp = (float) ModeloTabla.getValueAt(i, j);
                    TablaU[i+1][j] = Float.toString(imcp);
                }else {
                    TablaU[i+1][j] = (String) ModeloTabla.getValueAt(i, j);
                }
            }
        }
        return TablaU;
    }

    public void GuardarArchivo() {
        GenerarExcel GE = new GenerarExcel();
        JFileChooser file = new JFileChooser();
        file.showSaveDialog(this);
        File guarda = file.getSelectedFile();
        if (guarda != null) {
            String[][] TablaLLena = TablaDeIMC(TablaIMC);
            String ruta = guarda.toString();
            try {
                ruta = ruta + ".xls";
                try {
                    GE.GenerarExcel(TablaLLena, ruta);
                } catch (WriteException ex) {
                    Logger.getLogger(RegistroUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null,
                        "El archivo se a guardado Exitosamente",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(RegistroUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAltura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblIMC = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaIMC = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CALCULO DE IMC");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 10, 680, 40);

        jLabel2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("MATRICULA: ");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 90, 170, 30);

        txtMatricula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatriculaActionPerformed(evt);
            }
        });
        jPanel1.add(txtMatricula);
        txtMatricula.setBounds(190, 90, 180, 30);

        jLabel3.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("PESO(KG):");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 140, 160, 30);

        txtPeso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtPeso.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPesoCaretUpdate(evt);
            }
        });
        txtPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesoActionPerformed(evt);
            }
        });
        jPanel1.add(txtPeso);
        txtPeso.setBounds(190, 140, 180, 30);

        jLabel4.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("IMC: ");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(120, 240, 70, 30);

        txtAltura.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtAltura.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtAlturaCaretUpdate(evt);
            }
        });
        txtAltura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlturaActionPerformed(evt);
            }
        });
        jPanel1.add(txtAltura);
        txtAltura.setBounds(190, 190, 180, 30);

        jLabel5.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel5.setText("ALTURA(MTS):");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 190, 167, 30);

        lblIMC.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        lblIMC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblIMC);
        lblIMC.setBounds(190, 240, 110, 30);

        jLabel7.setBackground(new java.awt.Color(255, 0, 0));
        jLabel7.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("DESNUTRICION");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel7);
        jLabel7.setBounds(500, 60, 120, 20);

        jLabel8.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Menor a 18:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(400, 60, 90, 20);

        jLabel9.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("18 A 24.9:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(400, 90, 90, 20);

        jLabel10.setBackground(new java.awt.Color(255, 0, 0));
        jLabel10.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 255, 51));
        jLabel10.setText("NORMAL");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel10);
        jLabel10.setBounds(500, 90, 120, 20);

        jLabel11.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("25 A 26.9:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(410, 120, 80, 20);

        jLabel12.setBackground(new java.awt.Color(255, 0, 0));
        jLabel12.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setText("SOBREPESO");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel12);
        jLabel12.setBounds(500, 120, 120, 20);

        jLabel13.setBackground(new java.awt.Color(255, 0, 0));
        jLabel13.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel13.setText("OBESIDAD");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel13);
        jLabel13.setBounds(500, 150, 120, 20);

        jLabel14.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("MAYOR A 27");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(400, 150, 90, 20);

        jLabel15.setBackground(new java.awt.Color(255, 0, 0));
        jLabel15.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 102, 0));
        jLabel15.setText("OBESIDAD l");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel15);
        jLabel15.setBounds(500, 180, 120, 20);

        jLabel16.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("27 A 29.9:");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(410, 180, 80, 20);

        jLabel17.setBackground(new java.awt.Color(255, 0, 0));
        jLabel17.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("OBESIDAD ll");
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel17);
        jLabel17.setBounds(500, 210, 120, 20);

        jLabel18.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("30 A 39.9:");
        jPanel1.add(jLabel18);
        jLabel18.setBounds(410, 210, 80, 20);

        jLabel19.setBackground(new java.awt.Color(255, 0, 0));
        jLabel19.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setText("OBESIDAD EXTREMA");
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLabel19);
        jLabel19.setBounds(500, 240, 140, 20);

        jLabel20.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("MAYOR A 40:");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(390, 240, 100, 20);

        btnGuardar.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar);
        btnGuardar.setBounds(380, 310, 140, 50);

        btnLimpiar.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/if_package-purge_24217.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar);
        btnLimpiar.setBounds(190, 310, 140, 50);

        jTabbedPane2.addTab("CALCULO IMC", jPanel1);

        jPanel2.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("BUSCAR:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(20, 10, 100, 20);

        TablaIMC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaIMC);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(50, 60, 590, 220);

        txtBuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBuscarCaretUpdate(evt);
            }
        });
        jPanel2.add(txtBuscar);
        txtBuscar.setBounds(130, 10, 290, 20);

        btnExcel.setFont(new java.awt.Font("Lucida Sans Unicode", 2, 18)); // NOI18N
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/Excel_2013_23480.png"))); // NOI18N
        btnExcel.setText("GENERAR EXCEL");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel2.add(btnExcel);
        btnExcel.setBounds(370, 300, 280, 60);

        jTabbedPane2.addTab("TABLA DE IMC ALUMNOS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatriculaActionPerformed

    private void txtPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesoActionPerformed

    private void txtAlturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlturaActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        LimpiarTodo();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        GuardarIMC();        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtPesoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPesoCaretUpdate
        Validaciones();
        CalcularIMC(); // TODO add your handling code here:
    }//GEN-LAST:event_txtPesoCaretUpdate

    private void txtAlturaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtAlturaCaretUpdate
        Validaciones();
        CalcularIMC(); // TODO add your handling code here:
    }//GEN-LAST:event_txtAlturaCaretUpdate

    private void txtBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscarCaretUpdate
        Buscar();        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCaretUpdate

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
GuardarArchivo();        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcelActionPerformed

    /*public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalculoIMC().setVisible(true);
            }
        });
    }
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaIMC;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblIMC;
    private javax.swing.JTextField txtAltura;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtPeso;
    // End of variables declaration//GEN-END:variables
}
