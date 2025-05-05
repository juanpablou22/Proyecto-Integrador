/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan
 */
public class ControladorProducto {
    public void MostrarProductos(JTable tablaTotalProductos) {
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelo.ModeloProducto objetoProducto = new Modelo.ModeloProducto();
        DefaultTableModel modelo = new DefaultTableModel();
        String sql = "";
        modelo.addColumn("id");
        modelo.addColumn("NombreProd");
        modelo.addColumn("Precio");
        modelo.addColumn("stock");
        tablaTotalProductos.setModel(modelo);

        sql = "select producto.idproducto, producto.nombre, producto.precioProducto, producto.stock from producto";
        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                objetoProducto.setIdProducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));
                modelo.addRow(new Object[]{objetoProducto.getIdProducto(), objetoProducto.getNombreProducto(), objetoProducto.getPrecioProducto(), objetoProducto.getStockProducto()});

            }
            tablaTotalProductos.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostar productos," + e.toString());

        } finally {
            objetoConexion.cerrarConexion();

        }

    }
}
