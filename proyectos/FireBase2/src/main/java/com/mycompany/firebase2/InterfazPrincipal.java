/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.firebase2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileOutputStream;

/**
 *
 * @author Lidia Fernández Martínez
 */
public class InterfazPrincipal extends javax.swing.JFrame {

    DefaultTableModel modelo;
    String mensaje;
    String datos[] = new String[4];
    Usuarios user, userSave, userUpdate;
    String email, password, ids, rols, emailUSer, passwordUSer, idUSerUpdate;
    int filas = 0;
    FireBaseService fbs = null;
    int rolCambiadoInt, idUserInt;
    boolean sw = true;
    FirebaseAuth auth;
    PdfPTable tablaPDF;

    Image icon = new ImageIcon(getClass().getResource("/images/icono.png")).getImage();

    /**
     * Crea el formulario InterfazPrincipal
     */
    public InterfazPrincipal() {
        initComponents();
        spinnerRol.setVisible(false);
        texto1.setVisible(false);
        rol.setVisible(false);
        btnConfirmar.setVisible(false);
        btnModificar.setVisible(false);
        btnEliminar.setVisible(false);
        setIconImage(icon);
        setTitle("Gestión de usuarios");

        //Creación de la tabla
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("ID");
        modelo.addColumn("CORREO");
        modelo.addColumn("CONTRASEÑA");
        modelo.addColumn("ROL");
        this.tabla.setModel(modelo);
        
        //Conexión a la base de datos
        try {
            fbs = new FireBaseService();
        } catch (IOException ex) {
            Logger.getLogger(InterfazPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnConsultar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        texto1 = new javax.swing.JLabel();
        rol = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        spinnerRol = new javax.swing.JSpinner();
        menu = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        salir = new javax.swing.JMenuItem();
        ayuda = new javax.swing.JMenu();
        usoAplicacion = new javax.swing.JMenuItem();
        acercaDe = new javax.swing.JMenuItem();
        imprimir = new javax.swing.JMenu();
        generarPDF = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titulo.setFont(new java.awt.Font("Traditional Arabic", 2, 18)); // NOI18N
        titulo.setText("GESTIÓN DE USUARIOS");

        tabla.setAutoCreateRowSorter(true);
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla.setToolTipText("");
        jScrollPane2.setViewportView(tabla);
        tabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        btnConsultar.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(0, 204, 0));
        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/buscar.png"))); // NOI18N
        btnConsultar.setText("Consultar usuarios");
        btnConsultar.setToolTipText("Consulta la lista de usuarios");
        btnConsultar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnConsultar.setBorderPainted(false);
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(204, 204, 0));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnModificar.setText("Modificar rol");
        btnModificar.setToolTipText("Cambia el rol del usuario");
        btnModificar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnModificar.setBorderPainted(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 0, 0));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar usuario");
        btnEliminar.setToolTipText("Elimina un usuario");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.setBorderPainted(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        texto1.setFont(new java.awt.Font("Trebuchet MS", 3, 14)); // NOI18N
        texto1.setText("Modifca el rol del usuario");

        rol.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        rol.setText("Rol");

        btnConfirmar.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnConfirmar.setContentAreaFilled(false);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        spinnerRol.setModel(new javax.swing.SpinnerNumberModel(-1, -1, 1, 1));

        archivo.setText("Archivo");

        salir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salida.png"))); // NOI18N
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });
        archivo.add(salir);

        menu.add(archivo);

        ayuda.setText("Ayuda");

        usoAplicacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        usoAplicacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/signo-de-interrogacion.png"))); // NOI18N
        usoAplicacion.setText("Uso de la aplicación");
        usoAplicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usoAplicacionActionPerformed(evt);
            }
        });
        ayuda.add(usoAplicacion);

        acercaDe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        acercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/informacion.png"))); // NOI18N
        acercaDe.setText("Acerca de...");
        acercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaDeActionPerformed(evt);
            }
        });
        ayuda.add(acercaDe);

        menu.add(ayuda);

        imprimir.setText("Imprimir");

        generarPDF.setText("Generar PDF");
        generarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarPDFActionPerformed(evt);
            }
        });
        imprimir.add(generarPDF);

        menu.add(imprimir);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titulo)
                .addGap(295, 295, 295))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(109, 109, 109))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(texto1)
                        .addGap(100, 100, 100))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(spinnerRol, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rol)
                        .addGap(175, 175, 175))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(texto1)
                        .addGap(24, 24, 24)
                        .addComponent(rol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnConfirmar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *Método del menú que permite salir de la aplicación
     */
    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

    /**
     * Método que muestra un JOptionPane con información
     */
    private void acercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaDeActionPerformed
        mensaje = "Aplicación de escritorio multiplataforma para la gestión de roles de usuarios."
                + "\nDesarrollada en Netbeans 13 empleando el lenguaje JAVA."
                + "\nDesarrollada por Lidia Fernández Martínez, para el proyecto final del grado en desarrollo de aplicaciones multiplataforma curso 2021/2022.";
        JOptionPane.showMessageDialog(null, mensaje, "ACERCA DE", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_acercaDeActionPerformed

     /**
     * Método que muestra un JOptionPane con información
     */
    private void usoAplicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usoAplicacionActionPerformed
        mensaje = "Interfaz gráfica para gestión de roles de usuario de la aplicación AppBar."
                + "\nEn esta aplicación de escritorio se consulta y actualizan automáticamente los diferentes usuarios que se registran en la aplicación principal."
                + "\nEstos usuarios se muestran en una tabla con cuatro columnas (ID, CORREO, CONTRASEÑA, ROL)."
                + "\nEl administrador tendrá dos opciones adiccionales más al seleccionar un usuario de la tabla como son modificar el rol del usuario y eliminar un usuario.";
       JOptionPane.showMessageDialog(null, mensaje, "USO DE LA APLICACIÓN", JOptionPane.INFORMATION_MESSAGE);    }//GEN-LAST:event_usoAplicacionActionPerformed

     /**
     * Método que realiza la conexión con la base de datos y muestra en la tabla los usuarios registrados
     * en la base de datos.
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed

        if (fbs == null) {
            btnConsultar.setVisible(true);
        } else {
            btnConsultar.setVisible(false);
            btnModificar.setVisible(true);
            btnEliminar.setVisible(true);
        }

        //Se realiza la conexión con la tabla
        DatabaseReference ref = fbs.getDb()
                .getReference("usuarios");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    //Se obtienen los datos de la tabla de Firebase
                    modelo.setRowCount(0);
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String idUser = String.valueOf(ds.child("id").getValue());
                        int idUserInt = Integer.parseInt(idUser);
                        String emailUser = String.valueOf(ds.child("email").getValue());
                        String passwordUser = String.valueOf(ds.child("password").getValue());
                        String rolUser = String.valueOf(ds.child("rol").getValue());
                        int rolUserInt = Integer.parseInt(rolUser);

                        //Se añaden estos daros a la tabla
                        datos[0] = idUser;
                        datos[1] = emailUser;
                        datos[2] = passwordUser;
                        datos[3] = rolUser;
                        modelo.addRow(datos);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.print("Error: " + error.getMessage());
            }

        });

    }//GEN-LAST:event_btnConsultarActionPerformed

     /**
     * Método que coge el rol del usuario de la fila seleccionada de la tabla
     */
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        //Se selecciona la fila de la tabla
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada >= 0) {
            //Se ponen visibles los elementos necesarios
            texto1.setVisible(true);
            rol.setVisible(true);
            btnConfirmar.setVisible(true);
            spinnerRol.setVisible(true);
            
            // Se establece el rol del usuario seleccionado en el spinner
            String posicionSpinner = tabla.getValueAt(filaSeleccionada, 3).toString();
            int posicionSpinnerInt = Integer.parseInt(posicionSpinner);
            switch (posicionSpinnerInt) {
                case 0:
                    spinnerRol.setValue(0);
                    break;
                case -1:
                    spinnerRol.setValue(-1);
                    break;
                case 1:
                    spinnerRol.setValue(1);
                    break;
                default:
                    break;
            }
            filas = filaSeleccionada;

            idUSerUpdate = tabla.getValueAt(filaSeleccionada, 0).toString();
            idUserInt = Integer.parseInt(idUSerUpdate);
            emailUSer = tabla.getValueAt(filaSeleccionada, 1).toString();
            passwordUSer = tabla.getValueAt(filaSeleccionada, 2).toString();
            sw = true;

        } else {
            //Se indica un error si no se ha seleccionado una fila
            mensaje = "Debe seleccionar una fila o la tabla está vacía";
            JOptionPane.showMessageDialog(null, mensaje, "ERROR AL MODIFICAR", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_btnModificarActionPerformed

     /**
     * Método que permite eliminar un usuario 
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        //Seleccionamos la fila
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            //Se muestra un aviso al usuario
            mensaje = "Se eliminará el usuario de forma permanente,¿Quiere eliminarlo?";
            int opcion = JOptionPane.showConfirmDialog(null, mensaje, "ATENCION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opcion == JOptionPane.YES_OPTION) {

                DatabaseReference ref = fbs.getDb()
                        .getReference("usuarios");

                //Obtenemos el id para poder eliminar el usuario de la tabla
                String idSeleccionado = tabla.getValueAt(filaSeleccionada, 0).toString();
                int idSeleccionadoInt = Integer.parseInt(idSeleccionado);

                ref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                //Obtenemos el id y el email de la base de datos
                                String idVariable = ds.child("id").getValue().toString();
                                int idVariableComparar = Integer.parseInt(idVariable);
                                String emailVariable = ds.child("email").getValue().toString();
                                //eliminamos si el id seleccionado es el de la base de datos
                                if (idVariableComparar == idSeleccionadoInt) {
                                    ref.child(idSeleccionado).removeValueAsync();
                                    //Eliminamos el usuario de la Authentication de Firebase
                                    auth = FirebaseAuth.getInstance();
                                    try {
                                        UserRecord userRegistro = auth.getUserByEmail(emailVariable);
                                        String idUserRegistro = userRegistro.getUid();
                                        auth.deleteUserAsync(idUserRegistro);
                                    } catch (FirebaseAuthException ex) {
                                        Logger.getLogger(InterfazPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    modelo.removeRow(filaSeleccionada);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.print("Error: " + error.getMessage());
                    }

                });
            }
        } else {
             //Se indica un error si no se ha seleccionado una fila 
            mensaje = "Debe seleccionar una fila o la tabla está vacía";
            JOptionPane.showMessageDialog(null, mensaje, "ERROR AL ELIMINAR", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    /**
     * Método que modifica el rol del usuario seleccionado
     */
    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        //Ocultamos los elementos
        spinnerRol.setVisible(false);
        texto1.setVisible(false);
        rol.setVisible(false);
        btnConfirmar.setVisible(false);

        //Establecemos el nuevo valor del spinner en su fila y columna
        String valorSpinner = "" + spinnerRol.getValue();
        rolCambiadoInt = Integer.parseInt(valorSpinner);
        modelo.setValueAt(valorSpinner, filas, 3);

        //Actualizamos el usuario
        userUpdate = new Usuarios(idUserInt, emailUSer, passwordUSer, rolCambiadoInt);
        System.err.println(userUpdate);

        DatabaseReference ref = fbs.getDb()
                .getReference("usuarios");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (sw == true) {
                    //Actualizamos el usuario en la base de datos
                    ref.child(idUSerUpdate).setValueAsync(userUpdate);
                    sw = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }//GEN-LAST:event_btnConfirmarActionPerformed

     /**
     * Método que permite generar un PDF con los usuarios existentes en la base de datos
     */
    private void generarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarPDFActionPerformed
        
        //Creamos el documento
        Document documento = new Document();
        try{
            //Indicamos la ruta
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Usuarios.pdf"));
            documento.open();
            
            //Añadimos las columnas al PDF
            tablaPDF = new PdfPTable(4);
            tablaPDF.addCell("ID");
            tablaPDF.addCell("CORREO");
            tablaPDF.addCell("CONTRASEÑA");
            tablaPDF.addCell("ROL");
            
            //Cogemos los datos
            try {
                DatabaseReference ref = fbs.getDb()
                .getReference("usuarios");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    //Se obtienen los datos de la tabla de Firebase
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String idUser = String.valueOf(ds.child("id").getValue());
                        String emailUser = String.valueOf(ds.child("email").getValue());
                        String passwordUser = String.valueOf(ds.child("password").getValue());
                        String rolUser = String.valueOf(ds.child("rol").getValue());
        
                        tablaPDF.addCell(idUser);
                        tablaPDF.addCell(emailUser);
                        tablaPDF.addCell(passwordUser);
                        tablaPDF.addCell(rolUser);
                        
                        
                    }
                    try {
                        //Añadimos la tabla al PDF
                        documento.add(tablaPDF);
                    } catch (DocumentException ex) {
                        Logger.getLogger(InterfazPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.print("Error: " + error.getMessage());
            }

        });
            } catch (Exception e) {
                System.out.println("Error: "+ e);
            }
            
            JOptionPane.showMessageDialog(null, "PDF Generado con éxito");
            documento.close();
        }catch(Exception e){
            System.out.println("Error: "+ e);
        }
    }//GEN-LAST:event_generarPDFActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Crea y lanza el formulario */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem acercaDe;
    private javax.swing.JMenu archivo;
    private javax.swing.JMenu ayuda;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JMenuItem generarPDF;
    private javax.swing.JMenu imprimir;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuBar menu;
    private javax.swing.JLabel rol;
    private javax.swing.JMenuItem salir;
    private javax.swing.JSpinner spinnerRol;
    private javax.swing.JTable tabla;
    private javax.swing.JLabel texto1;
    private javax.swing.JLabel titulo;
    private javax.swing.JMenuItem usoAplicacion;
    // End of variables declaration//GEN-END:variables
}
