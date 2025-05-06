/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan
 */
public class ControladorVenta {

    public void BuscarProducto(JTextField nombreProducto, JTable tablaproductos) {
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelo.ModeloProducto objetoProducto = new Modelo.ModeloProducto();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("NombreP");
        modelo.addColumn("precioProducto");
        modelo.addColumn("stock");

        tablaproductos.setModel(modelo);

        try {
            String consulta = "select * from producto where producto.nombre like concat('%',?,'%');";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setString(1, nombreProducto.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                objetoProducto.setIdProducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));
                modelo.addRow(new Object[]{objetoProducto.getIdProducto(), objetoProducto.getNombreProducto(), objetoProducto.getPrecioProducto(), objetoProducto.getStockProducto()});

            }
            tablaproductos.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostar productos," + e.toString());

        } finally {
            objetoConexion.cerrarConexion();

        }
        for (int column = 0; column > tablaproductos.getColumnCount(); column++) {
            Class<?> columClass = tablaproductos.getColumnClass(column);
            tablaproductos.setDefaultEditor(columClass, null);
        }

    }

    public void SeleccionarProoductosVenta(JTable tablaProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock, JTextField precioFinal) {

        int fila = tablaProducto.getSelectedRow();
        try {
            if (fila >= 0) {
                id.setText(tablaProducto.getValueAt(fila, 0).toString());
                nombres.setText(tablaProducto.getValueAt(fila, 1).toString());
                precioProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
                stock.setText(tablaProducto.getValueAt(fila, 3).toString());
                precioFinal.setText(tablaProducto.getValueAt(fila, 2).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion," + e.toString());
        }

    }
    public void BuscarCliente(JTextField nombreCliente, JTable tablacliente) {
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelo.ModeloCliente objetoCliente = new Modelo.ModeloCliente();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("ApPaterno");
        modelo.addColumn("ApMaterno");

        tablacliente.setModel(modelo);

        try {
            String consulta = "select * from cliente where cliente.nombres like concat('%',?,'%');";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setString(1, nombreCliente.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                objetoCliente.setIdCliente(rs.getInt("idcliente"));
                objetoCliente.setNombres(rs.getString("nombres"));
                objetoCliente.setApPaterno(rs.getString("appaterno"));
                objetoCliente.setApMaterno(rs.getString("apmaterno"));
                modelo.addRow(new Object[]{objetoCliente.getIdCliente(), objetoCliente.getNombres(), objetoCliente.getApPaterno(), objetoCliente.getApMaterno()});

            }
            tablacliente.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostar productos," + e.toString());

        } finally {
            objetoConexion.cerrarConexion();

        }
        for (int column = 0; column > tablacliente.getColumnCount(); column++) {
            Class<?> columClass = tablacliente.getColumnClass(column);
            tablacliente.setDefaultEditor(columClass, null);
        }

    }
    public void SeleccionarClienteVenta(JTable tablacliente, JTextField id, JTextField nombres, JTextField appterno, JTextField apmaterno) {

        int fila = tablacliente.getSelectedRow();
        try {
            if (fila >= 0) {
                id.setText(tablacliente.getValueAt(fila, 0).toString());
                nombres.setText(tablacliente.getValueAt(fila, 1).toString());
                appterno.setText(tablacliente.getValueAt(fila, 2).toString());
                apmaterno.setText(tablacliente.getValueAt(fila, 3).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion," + e.toString());
        }

    }
    
    
}
