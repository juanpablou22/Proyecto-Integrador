/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.*;
import Controlador.ControladorUsuario;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

public class FromLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public FromLogin() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblClave = new JLabel("Clave:");
        txtUsuario = new JTextField();
        txtClave = new JPasswordField();
        btnIngresar = new JButton("Ingresar");

        lblUsuario.setBounds(30, 30, 60, 25);
        txtUsuario.setBounds(100, 30, 150, 25);
        lblClave.setBounds(30, 70, 60, 25);
        txtClave.setBounds(100, 70, 150, 25);
        btnIngresar.setBounds(100, 110, 100, 30);

        add(lblUsuario);
        add(txtUsuario);
        add(lblClave);
        add(txtClave);
        add(btnIngresar);

        btnIngresar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String clave = new String(txtClave.getPassword());

            ControladorUsuario ctrl = new ControladorUsuario();
            boolean valido = ctrl.validarUsuario(usuario, clave);

            if (valido) {
                JOptionPane.showMessageDialog(null, "Bienvenido");
                new MenuPrincipal().setVisible(true);
                dispose();  // Cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o clave incorrectos");
            }
        });
    }
}
