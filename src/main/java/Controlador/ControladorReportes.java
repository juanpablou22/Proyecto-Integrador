/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan
 */
public class ControladorReportes {

    public void BuscarFacturaMostrarDatosCliente(JTextField numeroFactura, JLabel numeroFacturaEncontrado, JLabel fechaFacturaEncontrado, JLabel nombrecliente, JLabel appaterno, JLabel apmaterno) {

        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();

        try {
            String consulta = "SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, cliente.appaterno, cliente.apmaterno FROM factura "
                    + "INNER JOIN cliente ON cliente.idcliente = factura.fkcliente WHERE factura.idfactura = ?";

            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                numeroFacturaEncontrado.setText(String.valueOf(rs.getInt("idfactura")));
                fechaFacturaEncontrado.setText(rs.getDate("fechaFactura").toString());
                nombrecliente.setText(rs.getString("nombres"));
                appaterno.setText(rs.getString("appaterno"));
                apmaterno.setText(rs.getString("apmaterno"));

            } else {
                numeroFacturaEncontrado.setText("");
                fechaFacturaEncontrado.setText("");
                nombrecliente.setText("");
                appaterno.setText("");
                apmaterno.setText("");
                JOptionPane.showMessageDialog(null, "No se encontro la factura,");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la factura: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void BuscarFacturaMostrarDatosProductos(JTextField numeroFactura, JTable tablaProductos, JLabel IVA, JLabel total) {

        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("N.Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("Subtotal");

        tablaProductos.setModel(modelo);
        try {
            String consulta = "SELECT producto.nombre, detalle.cantidad, detalle.precioVenta "
                    + "FROM detalle INNER JOIN factura ON factura.idfactura = detalle.fkfactura "
                    + "INNER JOIN producto ON producto.idproducto = detalle.fkproducto WHERE factura.idfactura = ?";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText()));
            ResultSet rs = ps.executeQuery();
            double totalFactura = 0.0;
            double valorIVA = 0.18;

            DecimalFormat formato = new DecimalFormat("#.##");
            while (rs.next()) {

                String nombreProducto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");
                double subtotal = cantidad * precioVenta;

                totalFactura = Double.parseDouble(formato.format(totalFactura + subtotal));

                modelo.addRow(new Object[]{nombreProducto, cantidad, precioVenta, subtotal});

            }

            double totalIVA = Double.parseDouble(formato.format(totalFactura * valorIVA));
            IVA.setText(String.valueOf(totalIVA));

            total.setText(String.valueOf(totalFactura));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los productos de la factura: " + e.toString());

        } finally {
            objetoConexion.cerrarConexion();
        }
    }

}
