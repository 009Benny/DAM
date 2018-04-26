package FRAME;

import CONTROL.ConvertirAMayusculas;
import CONTROL.FechaYHora;
import CONTROL.PasoMetodosUsuario;
import CONTROL.Validar;
import EXCEL.GenerarExcel;
import MySQL.Usuario;
import static com.mysql.jdbc.StringUtils.indexOf;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import jxl.write.WriteException;

public class RegistroPrestamo extends javax.swing.JFrame {

    Validar v = new Validar();
    PasoMetodosUsuario bll = new PasoMetodosUsuario();
    FechaYHora t = new FechaYHora();
    Usuario u = new Usuario();
    DefaultTableModel modelo_tabla;
    JScrollPane crl = new JScrollPane();
    boolean ot = false;
    boolean nuevo = false;
    boolean consultar = false;
    boolean z = false;

    public RegistroPrestamo() {
        initComponents();
        lblFecha.setText(t.fecha());
        txtOtro.setEnabled(true);
        modelo_tabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        //TablaPrestamos.setAutoscrolls(true);
        TablaPrestamos.setModel(modelo_tabla);
        modelo_tabla.addColumn("Matricula");
        modelo_tabla.addColumn("Nombre");
        modelo_tabla.addColumn("HOMBRERAS");
        modelo_tabla.addColumn("CASCO");
        modelo_tabla.addColumn("ROPA/PRACTICA");
        modelo_tabla.addColumn("ROPA/JUEGO");
        modelo_tabla.addColumn("GUARDAS");
        modelo_tabla.addColumn("RODILLERAS");
        modelo_tabla.addColumn("RIFIONERAS");
        modelo_tabla.addColumn("COSTILLERAS");
        modelo_tabla.addColumn("CUELLERA");
        modelo_tabla.addColumn("CODERA");
        modelo_tabla.addColumn("COLERA");
        modelo_tabla.addColumn("ANTEBRAZO");
        modelo_tabla.addColumn("BARBIQUEJO");
        modelo_tabla.addColumn("OREJERAS");
        TableColumnModel columnModel = TablaPrestamos.getColumnModel();
        //Tamaño preferente de las columnas
        columnModel.getColumn(0).setPreferredWidth(60);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(60);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(70);
        columnModel.getColumn(6).setPreferredWidth(60);
        columnModel.getColumn(7).setPreferredWidth(60);
        columnModel.getColumn(8).setPreferredWidth(60);
        columnModel.getColumn(9).setPreferredWidth(70);
        columnModel.getColumn(10).setPreferredWidth(60);
        columnModel.getColumn(11).setPreferredWidth(50);
        columnModel.getColumn(12).setPreferredWidth(50);
        columnModel.getColumn(13).setPreferredWidth(60);
        columnModel.getColumn(14).setPreferredWidth(70);
        columnModel.getColumn(15).setPreferredWidth(50);
        bll.MostrarListaPrestamo(modelo_tabla, TablaPrestamos);
        //para que no se muevan las columnas
        TablaPrestamos.getTableHeader().setReorderingAllowed(false);

    }

    public void ActualizarTabla() {
        //Limpiar
        while (modelo_tabla.getRowCount() > 0) {
            modelo_tabla.removeRow(0);

        }
        //cargar
        bll.MostrarListaPrestamo(modelo_tabla, TablaPrestamos);
    }

    public void Buscar() {
        txtBuscar.setDocument(new ConvertirAMayusculas());
        String dato = txtBuscar.getText();

        if (dato.isEmpty()) {
            ActualizarTabla();
        } else if (!dato.isEmpty()) {
            while (modelo_tabla.getRowCount() > 0) {
                modelo_tabla.removeRow(0);
            }
            bll.BuscarListaPrestamo(modelo_tabla, TablaPrestamos, dato);
        }
    }

    public void ValidarIngreso() {
        String Material;
        v.ValidarSoloNumeros(txtMatricula);
        v.LimitarCaracteres(txtMatricula, 7);
        v.LimitarCaracteres(txtOtro, 40);
        txtOtro.setDocument(new ConvertirAMayusculas());
        String Matricula = txtMatricula.getText();
        String Deporte = (String) boxDeporte.getSelectedItem();
        if (ot != true) {
            Material = (String) boxMaterial.getSelectedItem();
        } else {
            Material = txtOtro.getText();
        }
        String Marca = (String) boxMarca.getSelectedItem();
        String Size = (String) boxSize.getSelectedItem();
        if (!("SELECCIONAR".equals(Deporte) || "SELECCIONAR".equals(Marca) || "SELECCIONAR".equals(Size))) {
            if (!(Matricula.isEmpty())) {
                if (z == false) {
                    btnNuevoRegistro.setEnabled(true);
                    btnOtroRegistro.setEnabled(true);
                }
            } else {
                btnNuevoRegistro.setEnabled(false);
                btnOtroRegistro.setEnabled(false);
            }
        } else {
            btnNuevoRegistro.setEnabled(false);
            btnOtroRegistro.setEnabled(false);
        }
    }

    public void ValidarMaterial() {
        String h = (String) boxMaterial.getSelectedItem();
        if ("OTRO".equals(h)) {
            txtOtro.setEnabled(true);
            ot = true;
        } else {
            txtOtro.setEnabled(false);
        }

    }

    public void LLenarAmericano() {
        String Deporte = (String) boxDeporte.getSelectedItem();
        if (Deporte.equals("AMERICANO") || Deporte.equals("TOCHITO")) {
            boxMaterial.removeAllItems();
            boxMaterial.addItem("SELECCIONAR");
            boxMaterial.addItem("HOMBRERAS");
            boxMaterial.addItem("ROPA/PRACTICA");
            boxMaterial.addItem("CASCO");
            boxMaterial.addItem("ROPA/JUEGO");
            boxMaterial.addItem("GUARDAS");
            boxMaterial.addItem("RODILLERAS");
            boxMaterial.addItem("RIFIONERAS");
            boxMaterial.addItem("COSTILLERAS");
            boxMaterial.addItem("CUELLERA");
            boxMaterial.addItem("CODERA");
            boxMaterial.addItem("COLERA");
            boxMaterial.addItem("ANTEBRAZERO");
            boxMaterial.addItem("BARBIQUEJO");
            boxMaterial.addItem("OREJERAS");
            boxMaterial.addItem("OTRO");
            boxMaterial.setEnabled(true);
            boxMarca.setEnabled(true);
            boxSize.setEnabled(true);
        }
    }

    public void LimpiarTodo() {
        txtMatricula.setText(null);
        txtMatricula.setEnabled(true);
        lblNombre.setText(null);
        boxDeporte.setSelectedIndex(0);
        boxDeporte.setEnabled(true);
        boxMaterial.removeAll();
        boxMaterial.setEnabled(false);
        txtOtro.setText(null);
        txtOtro.setEnabled(false);
        boxMarca.setEnabled(false);
        boxSize.setEnabled(false);
        btnNuevoRegistro.setEnabled(false);
        btnOtroRegistro.setEnabled(false);
        btnEntrega.setEnabled(false);
        ot = false;
        z = false;
        ActualizarTabla();
    }

    public void LimpiarOtro() {
        txtMatricula.setEnabled(false);
        lblNombre.setText(null);
        boxDeporte.setSelectedIndex(0);
        boxMaterial.removeAll();
        boxMaterial.setEnabled(false);
        txtOtro.setText(null);
        txtOtro.setEnabled(false);
        boxMarca.setEnabled(false);
        boxSize.setEnabled(false);
        btnNuevoRegistro.setEnabled(false);
        btnOtroRegistro.setEnabled(false);
        btnEntrega.setEnabled(false);
        ot = false;
        nuevo = false;
        ActualizarTabla();
    }

    public void NuevoRegistro() {
        boolean x = false;

        String Matricula = txtMatricula.getText().trim();
        int Mat = Integer.parseInt(Matricula);
        String Nombre = lblNombre.getText().trim();
        String Deporte = (String) boxDeporte.getSelectedItem();
        String Material;
        if (ot == true) {
            Material = txtOtro.getText().trim();
        } else {
            Material = (String) boxMaterial.getSelectedItem();
        }
        String Marca = (String) boxMarca.getSelectedItem();
        String Size = (String) boxSize.getSelectedItem();
        String Fecha = lblFecha.getText();
        String FechaPE = "FechaPrestamo";

        u.setMatricula(Mat);
        u.setNombre(Nombre);
        u.setDeporte(Deporte);
        u.setMaterial(Material);
        u.setMarca(Marca);
        u.setSize(Size);
        bll.InsertarFechaPrestamo(u, Fecha, FechaPE, Deporte);
        x = bll.BuscarAlumnoPrestamo(Mat);
        if (x == false) {
            bll.InsertarPrestamo(u, Deporte);
        } else {
            bll.ActualizarPrestamo(u, Deporte);
        }

        if (nuevo = false) {
            LimpiarTodo();
        } else {
            LimpiarOtro();
        }

    }

    public void EntregaEquipo() {
        boolean x = false;
        String Matricula = txtMatricula.getText().trim();
        int Mat = Integer.parseInt(Matricula);
        String Nombre = lblNombre.getText().trim();
        String Deporte = (String) boxDeporte.getSelectedItem();
        String Material;
        Material = txtOtro.getText().trim();
        String Marca = (String) boxMarca.getSelectedItem();
        String Size = (String) boxSize.getSelectedItem();
        String Fecha = lblFecha.getText();
        String FechaPE = "FechaEntrega";
        u.setMatricula(Mat);
        u.setNombre(Nombre);
        u.setDeporte(Deporte);
        u.setMaterial(Material);
        u.setMarca(Marca);
        u.setSize(Size);
        bll.InsertarFechaPrestamo(u, Fecha, FechaPE, Deporte);
        bll.EntregarEquipo(u, Deporte);
        if (nuevo = false) {
            LimpiarTodo();
        } else {
            LimpiarOtro();
        }
    }

    protected String[][] TablaDePrestamos(JTable TablaD) {

        TableModel ModeloTabla = TablaD.getModel();
        int col = ModeloTabla.getColumnCount();
        int fil = ModeloTabla.getRowCount();
        String[][] TablaU = new String[fil+1][col];
        TablaU[0][0]="Matricula";
        TablaU[0][1]="Nombre";
        TablaU[0][2]="HOMBRERAS";
        TablaU[0][3]="CASCO";
        TablaU[0][4]="ROPA/JUEGO";
        TablaU[0][5]="ROPA/PRACTICA";
        TablaU[0][6]="GUARDAS";
        TablaU[0][7]="RODILLERAS";
        TablaU[0][8]="RIFIONERAS";
        TablaU[0][9]="COSTILLERAS";
        TablaU[0][10]="CUELLERA";
        TablaU[0][11]="CODERA";
        TablaU[0][12]="COLERA";
        TablaU[0][13]="ANTEBRAZO";
        TablaU[0][14]="BARBIQUEJO";
        TablaU[0][15]="OREJERAS";
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
        JFileChooser file = new JFileChooser();
        file.showSaveDialog(this);
        File guarda = file.getSelectedFile();
        if (guarda != null) {
            String[][] TablaLLena = TablaDePrestamos(TablaPrestamos);
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

        Panel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        boxDeporte = new javax.swing.JComboBox<>();
        btnNuevoRegistro = new javax.swing.JButton();
        btnLimpiarTodo = new javax.swing.JButton();
        btnEntrega = new javax.swing.JButton();
        boxMaterial = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        boxMarca = new javax.swing.JComboBox<>();
        boxSize = new javax.swing.JComboBox<>();
        btnOtroRegistro = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtOtro = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPrestamos = new CONTROL.DTable();
        jLabel4 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        boxBuscar = new javax.swing.JComboBox<>();
        btnExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Lucida Fax", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE PRESTAMOS");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 11, 831, 43);

        jLabel2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DEPORTE:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(40, 150, 100, 30);

        jLabel3.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("NOMBRE:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 110, 100, 30);

        lblFecha.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 24)); // NOI18N
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setText("dd/MM/YYYY");
        jPanel1.add(lblFecha);
        lblFecha.setBounds(630, 80, 160, 40);

        jLabel5.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TAMAÑO:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(620, 150, 120, 22);

        jLabel7.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("MATRICULA: ");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(20, 70, 120, 30);

        lblNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblNombre);
        lblNombre.setBounds(140, 110, 460, 30);

        jLabel8.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("FECHA: ");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(620, 60, 190, 22);

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
        txtMatricula.setBounds(140, 70, 200, 30);

        boxDeporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "AMERICANO", "TOCHITO" }));
        boxDeporte.setToolTipText("");
        boxDeporte.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        boxDeporte.setName(""); // NOI18N
        boxDeporte.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxDeporteItemStateChanged(evt);
            }
        });
        jPanel1.add(boxDeporte);
        boxDeporte.setBounds(140, 150, 180, 30);

        btnNuevoRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/guardar.png"))); // NOI18N
        btnNuevoRegistro.setText("REGISTRAR PRESTAMO");
        btnNuevoRegistro.setEnabled(false);
        btnNuevoRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoRegistroActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoRegistro);
        btnNuevoRegistro.setBounds(20, 280, 180, 60);

        btnLimpiarTodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/if_package-purge_24217.png"))); // NOI18N
        btnLimpiarTodo.setText("LIMPIAR TODO");
        btnLimpiarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTodoActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiarTodo);
        btnLimpiarTodo.setBounds(430, 280, 170, 60);

        btnEntrega.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/if_ok-sign_173063.png"))); // NOI18N
        btnEntrega.setText("ENTREGA EQUIPO");
        btnEntrega.setEnabled(false);
        btnEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntregaActionPerformed(evt);
            }
        });
        jPanel1.add(btnEntrega);
        btnEntrega.setBounds(630, 280, 170, 60);

        boxMaterial.setEnabled(false);
        boxMaterial.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxMaterialItemStateChanged(evt);
            }
        });
        jPanel1.add(boxMaterial);
        boxMaterial.setBounds(140, 190, 180, 30);

        jLabel9.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("MATERIAL:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(30, 190, 110, 30);

        jLabel10.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("MARCA:");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(410, 150, 120, 22);

        boxMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "NIKE", "UNDERGROUND", "ADIDAS", "NBA", " " }));
        boxMarca.setEnabled(false);
        boxMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxMarcaItemStateChanged(evt);
            }
        });
        jPanel1.add(boxMarca);
        boxMarca.setBounds(390, 180, 160, 30);

        boxSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "XS", "S", "M", "L", "XL" }));
        boxSize.setEnabled(false);
        boxSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxSizeItemStateChanged(evt);
            }
        });
        jPanel1.add(boxSize);
        boxSize.setBounds(590, 180, 170, 30);

        btnOtroRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/agregar.png"))); // NOI18N
        btnOtroRegistro.setText("REGISTRAR OTRO");
        btnOtroRegistro.setEnabled(false);
        btnOtroRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtroRegistroActionPerformed(evt);
            }
        });
        jPanel1.add(btnOtroRegistro);
        btnOtroRegistro.setBounds(230, 280, 170, 60);

        jLabel11.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("OTRO:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(70, 230, 70, 30);

        txtOtro.setEnabled(false);
        jPanel1.add(txtOtro);
        txtOtro.setBounds(140, 230, 200, 30);

        Panel.addTab("REGISTRO DE PRESTAMOS", jPanel1);

        TablaPrestamos.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaPrestamos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TablaPrestamos.setAutoscrolls(false);
        TablaPrestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPrestamosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaPrestamos);

        jLabel4.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("BUSCAR:");

        txtBuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBuscarCaretUpdate(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        boxBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AMERICANO", "TOCHITO", " " }));

        btnExcel.setFont(new java.awt.Font("Lucida Sans Unicode", 2, 18)); // NOI18N
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONOS/Excel_2013_23480.png"))); // NOI18N
        btnExcel.setText("GENERAR EXCEL");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(boxBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(239, 239, 239))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        Panel.addTab("TABLA DE PRESTAMOS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Panel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatriculaActionPerformed

    private void btnNuevoRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoRegistroActionPerformed
        NuevoRegistro();        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoRegistroActionPerformed

    private void txtMatriculaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtMatriculaCaretUpdate
        ValidarIngreso();
        String Matricula = txtMatricula.getText();
        if (Matricula.length() == 7) {
            int Mat = 0;
            Mat = Integer.parseInt(Matricula);
            String Nombre = null;
            if (Mat > 1000000) {
                Nombre = bll.ConsultarNombrePorMatricula(Mat);
                lblNombre.setText(Nombre);
            }
        }
    }//GEN-LAST:event_txtMatriculaCaretUpdate

    private void boxDeporteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxDeporteItemStateChanged
        LLenarAmericano();
    }//GEN-LAST:event_boxDeporteItemStateChanged

    private void boxMaterialItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxMaterialItemStateChanged
        ValidarIngreso();
        ValidarMaterial();        // TODO add your handling code here:
    }//GEN-LAST:event_boxMaterialItemStateChanged

    private void boxMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxMarcaItemStateChanged
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_boxMarcaItemStateChanged

    private void boxSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxSizeItemStateChanged
        ValidarIngreso();        // TODO add your handling code here:
    }//GEN-LAST:event_boxSizeItemStateChanged

    private void btnLimpiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTodoActionPerformed
        LimpiarTodo();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarTodoActionPerformed

    private void btnOtroRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtroRegistroActionPerformed
        nuevo = true;
        NuevoRegistro();       // TODO add your handling code here:
    }//GEN-LAST:event_btnOtroRegistroActionPerformed

    private void btnEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregaActionPerformed
        EntregaEquipo();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntregaActionPerformed

    private void TablaPrestamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPrestamosMouseClicked
        int fila = TablaPrestamos.getSelectedRow();
        int columna = TablaPrestamos.getSelectedColumn();
        System.out.println(columna);
        consultar = true;
        Panel.setSelectedIndex(0);
        int Mat = (int) TablaPrestamos.getValueAt(fila, 0);
        String Matricula = String.valueOf(Mat);
        String Nombre = (String) TablaPrestamos.getValueAt(fila, 1);
        String Material;
        String Marca = null;
        String Size = null;
        System.out.println("280 Nombre: " + Nombre);
        String Deporte = (String) boxBuscar.getSelectedItem();
        if (columna > 1) {
            Material = (String) TablaPrestamos.getValueAt(fila, columna);
            if (!(Material.equals("NP") || Material.equals("ENTREGADO"))) {
                z = true;
                btnEntrega.setEnabled(true);
                int ind = Material.indexOf("/");
                Marca = Material.substring(0, ind);
                Size = Material.substring(ind + 1, Material.length());
            }

        } else {
            Material = "SELECCIONAR";
            Marca = "SELECCIONAR";
            Size = "SELECCIONRAR";
        }
        JTableHeader th = TablaPrestamos.getTableHeader();
        TableColumnModel tc = th.getColumnModel();
        TableColumn tcm = tc.getColumn(columna);
        String CMaterial = (String) tcm.getHeaderValue();

        txtMatricula.setText(Matricula);
        lblNombre.setText(Nombre);
        boxDeporte.setSelectedItem(Deporte);
        boxMaterial.setSelectedItem("OTRO");
        boxMarca.setSelectedItem(Marca);
        boxSize.setSelectedItem(Size);
        txtMatricula.setEnabled(false);
        boxDeporte.setEnabled(false);
        boxMaterial.setEnabled(false);
        System.out.println("CMaterial: " + CMaterial);
        txtOtro.setEnabled(true);
        txtOtro.setText(CMaterial);
        txtOtro.setEnabled(false);
        boxMarca.setEnabled(false);
        boxSize.setEnabled(false);
        btnNuevoRegistro.setEnabled(false);
        btnOtroRegistro.setEnabled(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaPrestamosMouseClicked

    private void txtBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscarCaretUpdate

        Buscar();

    }//GEN-LAST:event_txtBuscarCaretUpdate

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed

    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
GuardarArchivo();        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcelActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(RegistroPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(RegistroPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(RegistroPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(RegistroPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new RegistroPrestamo().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Panel;
    private javax.swing.JTable TablaPrestamos;
    private javax.swing.JComboBox<String> boxBuscar;
    private javax.swing.JComboBox<String> boxDeporte;
    private javax.swing.JComboBox<String> boxMarca;
    private javax.swing.JComboBox<String> boxMaterial;
    private javax.swing.JComboBox<String> boxSize;
    private javax.swing.JButton btnEntrega;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnLimpiarTodo;
    private javax.swing.JButton btnNuevoRegistro;
    private javax.swing.JButton btnOtroRegistro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtOtro;
    // End of variables declaration//GEN-END:variables
}
