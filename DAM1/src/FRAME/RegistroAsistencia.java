package FRAME;

import CONTROL.FechaYHora;
import CONTROL.PasoMetodosUsuario;
import CONTROL.Validar;

/**
 * AUTOR: BENNY REYES SOSA CORREO: 009bennyreyes@gmail.com CELULAR: 8119193915
 */
public class RegistroAsistencia extends javax.swing.JFrame {

    Validar v = new Validar();
    FechaYHora t = new FechaYHora();
    PasoMetodosUsuario bll = new PasoMetodosUsuario();

    public RegistroAsistencia() {
        initComponents();
        lblFecha.setText(t.fecha());
    }

    public void ValidarMatricula() {
        v.ValidarSoloNumeros(txtMatricula);
        v.LimitarCaracteres(txtMatricula, 7);
    }
    
    public void Validar(){
        String Matricula=txtMatricula.getText().trim();
        if(Matricula.isEmpty()){
            btnGuardar.setEnabled(false);
        }else{
            btnGuardar.setEnabled(true);
        }
    }

    public void RegistrarAsistencia() {
        boolean encontrado = false;
        boolean Crear = false;
        boolean columna = false;
        boolean BColumna = false;
        boolean BuscarA=false;
        String MatS = txtMatricula.getText().trim();
        int Matricula = Integer.parseInt(MatS);
        String Deporte = (String) boxDeporte.getSelectedItem();
        String Diap = t.Dia();
        String Mesp = String.valueOf(t.Mes());
        Mesp = MesS(Mesp);
        String Anualp = t.Anual();
        encontrado = bll.ConsultarTabla(Mesp, Anualp, Deporte);
        if (encontrado == true) {
            System.out.println("Encontro tabla");
            BColumna = bll.ConsultarColumna(Diap, Mesp, Anualp, Deporte);
            if (BColumna == false){
                System.out.println("Agrego columna");
                columna = bll.AgregarColumnaDia(Diap, Mesp, Anualp, Deporte);
            }
        } else {
            System.out.println("No econtrado tabla");
            Crear = bll.CrearTabla(Diap, Mesp, Anualp, Deporte);
            System.out.println("Creo tabla");
            columna = bll.AgregarColumnaDia(Diap, Mesp, Anualp, Deporte);
            System.out.println("Agrego columna");
        }
        String Nombre=null;
        Nombre = bll.ConsultarNombrePorMatricula(Matricula);
        System.out.println(Nombre);
        BuscarA = bll.BuscarAlumnoAsistencia(Diap, Mesp, Anualp, Deporte, Matricula);
        System.out.println(BuscarA);
        if(BuscarA == true){
            bll.ActualizarAsistencia(Diap, Mesp, Anualp, Deporte, Nombre, MatS);
            System.out.println("Actualizo asistencia");
        }else{
            bll.InsertarAsistencia(Diap, Mesp, Anualp, Deporte, Nombre, MatS);
            System.out.println("Inserto asistencia");
        }
        LimpiarTodo();
    }

    public String MesS(String MesS) {
        switch (MesS) {
            case "01":
                MesS = "ENERO";
                break;
            case "02":
                MesS = "FEBRERO";
                break;
            case "03":
                MesS = "MARZO";
                break;
            case "04":
                MesS = "ABRIL";
                break;
            case "05":
                MesS = "MAYO";
                break;
            case "06":
                MesS = "JUNIO";
                break;
            case "07":
                MesS = "JULIO";
                break;
            case "08":
                MesS = "AGOSTO";
                break;
            case "09":
                MesS = "SEMPTIEMBRE";
                break;
            case "10":
                MesS = "OCTUBRE";
                break;
            case "11":
                MesS = "NOVIEMBRE";
                break;
            case "12":
                MesS = "DICIEMBRE";
                break;
            default:
                break;
        }
        return MesS;
    }
    
    protected void LimpiarTodo(){
        txtMatricula.setText(null);
        btnGuardar.setEnabled(false);
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        boxDeporte = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaAsistencia = new javax.swing.JTable();
        boxBDeporte = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        boxMes = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        boxAño = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REGISTRO DE ASISTENCIA");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(190, 10, 320, 40);

        jLabel1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Matricula:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(190, 160, 120, 30);

        txtMatricula.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtMatricula.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtMatriculaCaretUpdate(evt);
            }
        });
        jPanel1.add(txtMatricula);
        txtMatricula.setBounds(340, 110, 180, 30);

        boxDeporte.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        boxDeporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AMERICANO", "TOCHITO", " " }));
        boxDeporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxDeporteActionPerformed(evt);
            }
        });
        jPanel1.add(boxDeporte);
        boxDeporte.setBounds(340, 160, 180, 30);

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
        btnGuardar.setBounds(260, 240, 220, 50);

        jLabel4.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        jLabel4.setText("Fecha:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(230, 60, 80, 30);

        lblFecha.setFont(new java.awt.Font("Lucida Sans Unicode", 3, 18)); // NOI18N
        lblFecha.setText("dd/MM/YYYY");
        jPanel1.add(lblFecha);
        lblFecha.setBounds(330, 60, 130, 30);

        jLabel9.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Matricula:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(190, 110, 120, 30);

        jTabbedPane1.addTab("REGISTRO DE ASISTENCIA", jPanel1);

        jPanel2.setLayout(null);

        TablaAsistencia.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TablaAsistencia);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(18, 72, 671, 188);

        boxBDeporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AMERICANO", " " }));
        jPanel2.add(boxBDeporte);
        boxBDeporte.setBounds(540, 20, 140, 30);

        jLabel6.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("AÑO:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(220, 20, 50, 30);

        jLabel7.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("DEPORTE:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(420, 20, 110, 30);

        boxMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" }));
        jPanel2.add(boxMes);
        boxMes.setBounds(80, 20, 120, 30);

        jLabel8.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("MES:");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 20, 50, 30);

        boxAño.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018", "2019", " " }));
        boxAño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxAñoActionPerformed(evt);
            }
        });
        jPanel2.add(boxAño);
        boxAño.setBounds(280, 20, 100, 30);

        jButton1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/Excel_2013_23480.png"))); // NOI18N
        jButton1.setText("EXPORTAR TABLA ACTUAL A EXCEL");
        jPanel2.add(jButton1);
        jButton1.setBounds(260, 270, 430, 50);

        jTabbedPane1.addTab("TABLA DE ASISTENCIA", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(749, Short.MAX_VALUE)
                .addComponent(jLabel3))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxDeporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxDeporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxDeporteActionPerformed

    private void txtMatriculaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMatriculaCaretUpdate
        ValidarMatricula();
        Validar();// TODO add your handling code here:
    }//GEN-LAST:event_txtMatriculaCaretUpdate

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        RegistrarAsistencia();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void boxAñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxAñoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxAñoActionPerformed

    /*public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroAsistencia().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaAsistencia;
    private javax.swing.JComboBox<String> boxAño;
    private javax.swing.JComboBox<String> boxBDeporte;
    private javax.swing.JComboBox<String> boxDeporte;
    private javax.swing.JComboBox<String> boxMes;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JTextField txtMatricula;
    // End of variables declaration//GEN-END:variables
}
