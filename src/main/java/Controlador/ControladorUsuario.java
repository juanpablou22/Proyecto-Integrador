/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Configuracion.CConexion;

public class ControladorUsuario {

    public boolean validarUsuario(String usuario, String clave) {
        boolean valido = false;
        CConexion conexion = new CConexion();

        try {
            Connection con = conexion.estableceConexion();
            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                valido = true;
            }

            rs.close();
            ps.close();
            conexion.cerrarConexion();

        } catch (Exception e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }

        return valido;
    }
}
