/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public void pasarProductosVenta(JTable tablaResumen, JTextField idProducto, JTextField nombreProducto, JTextField precioProducto, JTextField cantidadVenta, JTextField stock) {
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
        int stockDisponible = Integer.parseInt(stock.getText());
        String idNuevo = idProducto.getText();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String idExistente = modelo.getValueAt(i, 0).toString();
            if (idExistente.equals(idNuevo)) {
                JOptionPane.showMessageDialog(null, "El producto ya está registrado.");
                return;
            }
        }

        String nProducto = nombreProducto.getText();
        double precioUnitario = Double.parseDouble(precioProducto.getText());
        int cantidad = Integer.parseInt(cantidadVenta.getText());

        if (cantidad > stockDisponible) {
            JOptionPane.showMessageDialog(null, "La cantidad de venta no puede ser mayor al stock disponible.");
            return;
        }

        double subtotal = precioUnitario * cantidad;

        modelo.addRow(new Object[]{idNuevo, nProducto, precioUnitario, cantidad, subtotal});
    }

    public void eliminarProductosSeleccionadosResumenVenta(JTable tablaResumen) {
        try {

            DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
            int indiceSeleccionado = tablaResumen.getSelectedRow();

            if (indiceSeleccionado != -1) {
                modelo.removeRow(indiceSeleccionado);

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar" + e.toString());
        }

    }

    public void calcularTotalPagar(JTable tablaResumen, JLabel IVA, JLabel totalPagar) {
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();

        double totalSubtotal = 0;
        double iva = 0.18;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            totalSubtotal += (double) modelo.getValueAt(i, 4);
        }

        double totaliva = totalSubtotal * iva;
        double totalConIVA = totalSubtotal + totaliva;

        DecimalFormat formato = new DecimalFormat("#.##");
        totalPagar.setText(formato.format(totalConIVA));
        IVA.setText(formato.format(totaliva));
    }

    public void crearFactura(JTextField codCliente) {
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelo.ModeloCliente objetoCliente = new Modelo.ModeloCliente();

        String consulta = "insert into factura (fechaFactura,fkcliente) values(curdate(),?);";

        try {
            objetoCliente.setIdCliente(Integer.parseInt(codCliente.getText()));

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, objetoCliente.getIdCliente());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Factura Creada");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear Factura" + e.toString());

        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void realizarVenta(JTable tablaResumenVenta) {

        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();

        String consultaDetalle = "insert into detalle (fkfactura,fkproducto,cantidad,precioVenta) values((select max(idfactura)from factura),?,?,?)";
        String consultaStock = "UPDATE producto set producto.stock = stock - ? where idproducto =?;";

        try {
            PreparedStatement psDetalle = objetoConexion.estableceConexion().prepareCall(consultaDetalle);
            PreparedStatement psStock = objetoConexion.estableceConexion().prepareCall(consultaStock);

            int filas = tablaResumenVenta.getRowCount();
            for (int i = 0; i < filas; i++) {
                int idProducto = Integer.parseInt(tablaResumenVenta.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tablaResumenVenta.getValueAt(i, 3).toString());
                double precioVenta = Double.parseDouble(tablaResumenVenta.getValueAt(i, 2).toString());

                psDetalle.setInt(1, idProducto);
                psDetalle.setInt(2, cantidad);
                psDetalle.setDouble(3, precioVenta);
                psDetalle.executeUpdate();

                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                psStock.executeUpdate();

            }
            JOptionPane.showMessageDialog(null, "Venta realizada");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al vender" + e.toString());

        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void limpiarCamposLuegoVenta(JTextField buscarCliente, JTable tablaCliente, JTextField buscarProducto, JTable tablaproducto,
            JTextField selectIdCliente, JTextField selectNombreCliente, JTextField selectAppaterno,
            JTextField selectApmaterno, JTextField selectIdproducto, JTextField selectNombreProducto,
            JTextField selectPreciProducto, JTextField selectStockProducto, JTextField precioVenta,
            JTextField cantidadVenta, JTable tablaresumen, JLabel IVA, JLabel total, JTable tablaProducto) {

        buscarCliente.setText("");
        buscarCliente.requestFocus();
        DefaultTableModel modeloCliente = (DefaultTableModel) tablaCliente.getModel();

        buscarCliente.setText("");
        DefaultTableModel modeloProducto = (DefaultTableModel) tablaProducto.getModel();
        modeloCliente.setRowCount(0);

        selectIdCliente.setText("");
        selectNombreCliente.setText("");
        selectAppaterno.setText("");
        selectApmaterno.setText("");

        selectIdproducto.setText("");
        selectNombreProducto.setText("");
        selectPreciProducto.setText("");
        selectStockProducto.setText("");

        selectIdCliente.setText("");

        DefaultTableModel modeloResumen = (DefaultTableModel) tablaresumen.getModel();
        modeloResumen.setRowCount(0);

        IVA.setText("----");
        total.setText("----");

    }

    public void MostrarUltimaFactura(JLabel ultimafactura) {
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();

        try {
            String consulta = "SELECT MAX(idfactura) AS UltimaFactura FROM factura";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ultimafactura.setText(String.valueOf(rs.getInt("UltimaFactura")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar la ultima factura: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    }
