

package FRAME;

import CONTROL.ConvertirAMayusculas;
import CONTROL.Validar;
import EXCEL.GenerarExcel;
import MySQL.DatosDeMySQL;
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
 * AUTOR: BENNY REYES SOSA CORREO: 009bennyreyes@gmail.com CELULAR: 8119193915
 */
public class RegistroUsuarios extends javax.swing.JFrame {

    Validar v = new Validar();
    DatosDeMySQL bll = new DatosDeMySQL();
    Usuario u = new Usuario();
    DefaultTableModel modelo_tabla;
    int Matricula = 0;
    boolean consultar = false;

    public RegistroUsuarios() {

        initComponents();
        Validaciones();
        modelo_tabla = new DefaultTableModel() {
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        TablaDatos.setModel(modelo_tabla);
        modelo_tabla.addColumn("Matricula");
        modelo_tabla.addColumn("Nombre");
        modelo_tabla.addColumn("Semestre");
        modelo_tabla.addColumn("Celular");
        modelo_tabla.addColumn("Problema Medico");
        bll.MostrarLista(modelo_tabla, TablaDatos);
        //para que no se muevan las columnas
        TablaDatos.getTableHeader().setReorderingAllowed(false);
    }

    protected final void Validaciones() {
        v.ValidarSoloLetras(txtNombre);
        v.LimitarCaracteres(txtNombre, 80);
        txtNombre.setDocument(new ConvertirAMayusculas());
        v.ValidarSoloNumeros(txtMatricula);
        v.LimitarCaracteres(txtMatricula, 7);
        v.ValidarSoloNumeros(txtCelular);
        v.LimitarCaracteres(txtCelular, 12);
        v.ValidarSoloLetras(txtMedico);
        v.LimitarCaracteres(txtMedico, 30);
        txtMedico.setDocument(new ConvertirAMayusculas());
        v.ValidarSoloLetras(txtAlergias);
        v.LimitarCaracteres(txtAlergias, 30);
        txtAlergias.setDocument(new ConvertirAMayusculas());
        v.ValidarSoloLetras(txtMedicamentos);
        v.LimitarCaracteres(txtMedicamentos, 30);
        txtMedicamentos.setDocument(new ConvertirAMayusculas());
    }

    public void ValidarIngreso() {
        String Nombre = txtNombre.getText().trim();
        String Matriculav = txtMatricula.getText().trim();
        String Semestre = (String) boxSemestre.getSelectedItem();
        String Celular = txtCelular.getText().trim();
        String Medico = (String) boxMedico.getSelectedItem();
        if (Medico.equals("OTRO")) {
            txtMedico.setEnabled(true);
        } else {
            txtMedico.setEnabled(false);
        }
        String Alergias = (String) boxAlergias.getSelectedItem();
        if (Alergias.equals("OTRO")) {
            txtAlergias.setEnabled(true);
        } else {
            txtAlergias.setEnabled(false);
        }
        String Medicamentos = (String) boxMedicamentos.getSelectedItem();
        if (Medicamentos.equals("OTRO")) {
            txtMedicamentos.setEnabled(true);
        } else {
            txtMedicamentos.setEnabled(false);
        }

        if (consultar == false) {
            if (Nombre.isEmpty() || Matriculav.isEmpty() || Semestre.isEmpty() || Celular.isEmpty()
                    || Medico.isEmpty() || Alergias.isEmpty() || Medicamentos.isEmpty()) {
                btnGuardar.setEnabled(false);
            } else {
                btnGuardar.setEnabled(true);
            }
        }

    }

    public void BotonGuardar() {
        String Nombre = txtNombre.getText();
        String MatriculaS = txtMatricula.getText();
        int MatriculaG = Integer.parseInt(MatriculaS);
        String Semestre = (String) boxSemestre.getSelectedItem();
        String Celular = txtCelular.getText();
        String Medico = (String) boxMedico.getSelectedItem();
        if ("OTRO".equals(Medico)) {
            Medico = txtMedico.getText();
        }
        String Alergias = (String) boxAlergias.getSelectedItem();
        if ("OTRO".equals(Alergias)) {
            Alergias = txtAlergias.getText();
        }
        String Medicamentos = (String) boxMedicamentos.getSelectedItem();
        if ("OTRO".equals(Medicamentos)) {
            Medicamentos = txtMedicamentos.getText();
        }

        u.setNombre(Nombre);
        u.setMatricula(MatriculaG);
        u.setSemestre(Semestre);
        u.setCelular(Celular);
        u.setMedico(Medico);
        u.setAlergias(Alergias);
        u.setMedicamentos(Medicamentos);

        if (consultar == false) {
            bll.InsertarDatos(u);
        } else if (consultar == true) {
            bll.ModificarDatos(u);
        }
        LimpiarTodo();
    }

    public void ActualizarTabla() {
        //Limpiar
        while (modelo_tabla.getRowCount() > 0) {
            modelo_tabla.removeRow(0);

        }
        //cargar
        bll.MostrarLista(modelo_tabla, TablaDatos);
    }

    public void LimpiarTodo() {
        txtMatricula.setText(null);
        txtNombre.setText(null);
        txtCelular.setText(null);
        boxSemestre.setSelectedIndex(0);
        boxMedico.setSelectedIndex(0);
        boxAlergias.setSelectedIndex(0);
        boxMedicamentos.setSelectedIndex(0);
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
        consultar = false;
        ActualizarTabla();
    }

    public void Buscar() {
        String dato = txtBuscar.getText();
        if (dato.isEmpty()) {
            ActualizarTabla();
        } else if (!dato.isEmpty()) {
            while (modelo_tabla.getRowCount() > 0) {
                modelo_tabla.removeRow(0);
            }
            bll.BuscarLista(modelo_tabla, TablaDatos, dato);
        }
    }

    protected String[][] TablaDeUsuarios(JTable TablaD) {

        TableModel ModeloTabla = TablaD.getModel();
        int col = ModeloTabla.getColumnCount();
        int fil = ModeloTabla.getRowCount();
        String[][] TablaU = new String[fil+1][col];
        TablaU[0][0]="Matricula";
        TablaU[0][1]="Nombre";
        TablaU[0][2]="Semestre";
        TablaU[0][3]="Celular";
        TablaU[0][4]="Problema Medico";
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                if (j == 0) {
                    int matp = (int) ModeloTabla.getValueAt(i, j);
                    TablaU[i+1][j] = Integer.toString(matp);
                } else {
                    TablaU[i+1][j] = (String) ModeloTabla.getValueAt(i, j);
                }
            }
        }
        return TablaU;
    }

    public void GuardarArchivo() {
        GenerarExcel GE = new GenerarExcel();
        String nombre = "";
        JFileChooser file = new JFileChooser();
        file.showSaveDialog(this);
        File guarda = file.getSelectedFile();
        if (guarda != null) {
            String[][] TablaLLena = TablaDeUsuarios(TablaDatos);
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

        panel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtMedicamentos = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        boxMedicamentos = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtMedico = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        boxMedico = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        boxAlergias = new javax.swing.JComboBox<>();
        txtAlergias = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        boxSemestre = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dolphin Manager Asistent");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setLayout(null);

        txtMedicamentos.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 12)); // NOI18N
        txtMedicamentos.setEnabled(false);
        txtMedicamentos.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtMedicamentosCaretUpdate(evt);
            }
        });
        txtMedicamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMedicamentosActionPerformed(evt);
            }
        });
        jPanel1.add(txtMedicamentos);
        txtMedicamentos.setBounds(410, 270, 170, 20);

        txtCelular.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCelularCaretUpdate(evt);
            }
        });
        txtCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularActionPerformed(evt);
            }
        });
        jPanel1.add(txtCelular);
        txtCelular.setBounds(130, 120, 170, 20);

        jLabel2.setFont(new java.awt.Font("Lucida Fax", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Registro de Alumnos");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(80, 10, 448, 33);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar);
        btnEliminar.setBounds(210, 310, 170, 30);

        boxMedicamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NIGUNO", "VITAMINAS", "ASPIRINA", "INSULINA", "OTRO" }));
        boxMedicamentos.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                boxMedicamentosCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jPanel1.add(boxMedicamentos);
        boxMedicamentos.setBounds(410, 220, 170, 20);

        jLabel6.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Celular:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 120, 120, 20);

        txtMedico.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 12)); // NOI18N
        txtMedico.setEnabled(false);
        txtMedico.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtMedicoCaretUpdate(evt);
            }
        });
        txtMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMedicoActionPerformed(evt);
            }
        });
        jPanel1.add(txtMedico);
        txtMedico.setBounds(10, 270, 170, 20);

        jLabel5.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ESPECIFICAR OTRO");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 250, 170, 20);

        jLabel4.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nombre:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 90, 120, 20);

        jLabel9.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("MEDICAMENTOS");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(410, 190, 170, 30);

        jLabel10.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("ALERGIAS ");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(210, 190, 170, 30);

        jLabel7.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("*EN CASO DE 2 O MAS ESPECIFICAR COMO OTRO");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(240, 350, 350, 20);

        boxMedico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NINGUNA", "AMNESIA", "ANEMIA", "ARTRITIS", "CIRROSIS", "DIABETES", "DIABETES TIPO 2", "FRACTURA", "HIPERTENSION", "INFLUENZA", "INSOMNIO", "MIGRAÑA", "TIROIDES", "OTRO" }));
        boxMedico.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                boxMedicoCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        boxMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxMedicoActionPerformed(evt);
            }
        });
        jPanel1.add(boxMedico);
        boxMedico.setBounds(20, 220, 150, 20);

        jLabel14.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("PROBLEMA MEDICO");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(10, 190, 170, 30);

        boxAlergias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NIGUNA", "ACAROS(POLVO)", "POLEN", "PELO DE ANIMALES", "ALIMENTOS(ESPECIFICAR)", "ABEJAS", "MOHO", "LATEX", "INYECCIONES", "OTRO" }));
        boxAlergias.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                boxAlergiasCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jPanel1.add(boxAlergias);
        boxAlergias.setBounds(210, 220, 170, 20);

        txtAlergias.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 12)); // NOI18N
        txtAlergias.setEnabled(false);
        txtAlergias.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtAlergiasCaretUpdate(evt);
            }
        });
        txtAlergias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlergiasActionPerformed(evt);
            }
        });
        jPanel1.add(txtAlergias);
        txtAlergias.setBounds(210, 270, 170, 20);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar);
        btnGuardar.setBounds(410, 310, 170, 30);

        jLabel11.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("ESPECIFICAR OTRO");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(210, 250, 170, 20);

        jLabel3.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Semestre:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(370, 60, 100, 20);

        txtMatricula.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtMatriculaCaretUpdate(evt);
            }
        });
        txtMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatriculaActionPerformed(evt);
            }
        });
        jPanel1.add(txtMatricula);
        txtMatricula.setBounds(130, 60, 170, 20);

        jLabel13.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("ESPECIFICAR OTRO");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(410, 250, 170, 20);

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/if_package-purge_24217.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar);
        btnLimpiar.setBounds(10, 310, 170, 30);

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Matricula:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 60, 110, 20);

        jLabel8.setFont(new java.awt.Font("Lucida Fax", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ENFERMEDAD O ");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 180, 170, 20);

        txtNombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNombreCaretUpdate(evt);
            }
        });
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre);
        txtNombre.setBounds(130, 90, 450, 20);

        boxSemestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        boxSemestre.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxSemestreItemStateChanged(evt);
            }
        });
        jPanel1.add(boxSemestre);
        boxSemestre.setBounds(480, 60, 80, 20);

        panel.addTab("REGISTRO ALUMNOS", jPanel1);

        jPanel2.setLayout(null);

        jLabel15.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("BUSCAR:");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(20, 20, 110, 20);

        txtBuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBuscarCaretUpdate(evt);
            }
        });
        jPanel2.add(txtBuscar);
        txtBuscar.setBounds(140, 20, 290, 20);

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaDatos);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(30, 60, 530, 260);

        jButton1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/Excel_2013_23480.png"))); // NOI18N
        jButton1.setText("EXPORTAR  A EXCEL");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(260, 330, 300, 60);

        panel.addTab("TABLA DE ALUMNOS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatriculaActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularActionPerformed

    private void boxMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxMedicoActionPerformed

    private void txtMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedicoActionPerformed

    private void txtAlergiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlergiasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlergiasActionPerformed

    private void txtMedicamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMedicamentosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedicamentosActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        LimpiarTodo();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        BotonGuardar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "Realmente desea eliminar este usuario: ", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);      // TODO add your handling code here:
        if (respuesta == 0) {
            String mat = txtMatricula.getText().trim();
            Matricula = Integer.parseInt(mat);
            u.setMatricula(Matricula);
            bll.EliminarUsuario(u);
            LimpiarTodo();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtMatriculaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMatriculaCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatriculaCaretUpdate

    private void txtNombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNombreCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCaretUpdate

    private void txtCelularCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCelularCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularCaretUpdate

    private void boxSemestreItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxSemestreItemStateChanged
        ValidarIngreso();       // TODO add your handling code here:
    }//GEN-LAST:event_boxSemestreItemStateChanged

    private void boxMedicoCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_boxMedicoCaretPositionChanged
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_boxMedicoCaretPositionChanged

    private void boxAlergiasCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_boxAlergiasCaretPositionChanged
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_boxAlergiasCaretPositionChanged

    private void boxMedicamentosCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_boxMedicamentosCaretPositionChanged
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_boxMedicamentosCaretPositionChanged

    private void txtMedicoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMedicoCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedicoCaretUpdate

    private void txtAlergiasCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtAlergiasCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlergiasCaretUpdate

    private void txtMedicamentosCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMedicamentosCaretUpdate
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedicamentosCaretUpdate

    private void txtBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscarCaretUpdate
        Buscar();        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarCaretUpdate

    private void TablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosMouseClicked
        if (evt.getClickCount() == 1) {
            consultar = true;
            panel.setSelectedIndex(0);
            int fila = TablaDatos.getSelectedRow();
            Matricula = (int) TablaDatos.getValueAt(fila, 0);
            Object[] datos = bll.ConsultarPorMatricula(Matricula);
            String MatriculaS;
            MatriculaS = Integer.toString(Matricula);
            txtNombre.setText(datos[0].toString());
            txtMatricula.setText(MatriculaS);
            boxSemestre.setSelectedItem(datos[1].toString());
            txtCelular.setText(datos[2].toString());
            boxMedico.setSelectedItem("OTRO");
            boxAlergias.setSelectedItem("OTRO");
            boxMedicamentos.setSelectedItem("OTRO");
            boxMedico.setEnabled(false);
            boxAlergias.setEnabled(false);
            boxMedicamentos.setEnabled(false);
            txtMedico.setText(datos[3].toString());
            txtAlergias.setText(datos[4].toString());
            txtMedicamentos.setText(datos[5].toString());
            btnEliminar.setEnabled(true);
            btnGuardar.setEnabled(true);
        }


    }//GEN-LAST:event_TablaDatosMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        GuardarArchivo();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
    
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistroUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroUsuarios().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaDatos;
    private javax.swing.JComboBox<String> boxAlergias;
    private javax.swing.JComboBox<String> boxMedicamentos;
    private javax.swing.JComboBox<String> boxMedico;
    private javax.swing.JComboBox<String> boxSemestre;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTabbedPane panel;
    private javax.swing.JTextField txtAlergias;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtMedicamentos;
    private javax.swing.JTextField txtMedico;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
