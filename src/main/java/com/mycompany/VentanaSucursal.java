/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author mariayanezojeda
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana de gestión de una sucursal específica.
 * Permite visualizar el inventario, agregar nuevas refacciones y realizar auditorías.
 */
public class VentanaSucursal extends JFrame {
    private Sucursal sucursal;
    private BaseDatos baseDatos;
    private JTable tablaRefacciones;
    private DefaultTableModel modeloTabla;
    private JTextField campoCodigo;
    private JTextField campoNombre;
    private JTextField campoPrecio;
    private JComboBox<String> campoTipo;
    
    /**
     * Constructor de VentanaSucursal.
     * @param sucursal Sucursal a gestionar
     * @param baseDatos Instancia de base de datos
     */
    public VentanaSucursal(Sucursal sucursal, BaseDatos baseDatos) {
        this.sucursal = sucursal;
        this.baseDatos = baseDatos;
        inicializar();
    }
    
    /**
     * Inicializa los componentes de la ventana.
     */
    private void inicializar() {
        setTitle("AutoRefacciones - " + sucursal.getNombre());
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        
        // Encabezado con información de la sucursal
        JPanel panelEncabezado = crearPanelEncabezado();
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);
        
        // Panel central con tabla de refacciones
        JPanel panelCentral = crearPanelTabla();
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior con entrada de datos
        JPanel panelInferior = crearPanelEntrada();
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Crea el panel del encabezado con información de la sucursal.
     */
    private JPanel crearPanelEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel labelTitulo = new JLabel("Sucursal: " + sucursal.getNombre());
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel labelDireccion = new JLabel("Dirección: " + sucursal.getDireccion());
        labelDireccion.setFont(new Font("Arial", Font.PLAIN, 12));
        labelDireccion.setForeground(new Color(100, 100, 100));
        
        JPanel panelIzq = new JPanel(new GridLayout(2, 1));
        panelIzq.setOpaque(false);
        panelIzq.add(labelTitulo);
        panelIzq.add(labelDireccion);
        
        JButton btnAuditoria = new JButton("Auditoría Técnica");
        btnAuditoria.setFont(new Font("Arial", Font.BOLD, 11));
        btnAuditoria.setBackground(new Color(255, 152, 0));
        btnAuditoria.setForeground(Color.WHITE);
        btnAuditoria.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAuditoria.addActionListener(e -> realizarAuditoriaTecnica());
        
        panel.add(panelIzq, BorderLayout.WEST);
        panel.add(btnAuditoria, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Crea el panel con la tabla de refacciones.
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        
        JLabel labelTabla = new JLabel("Inventario de Refacciones");
        labelTabla.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(labelTabla, BorderLayout.NORTH);
        
        // Crear tabla
        String[] columnas = {"Código", "Nombre", "Precio", "Tipo", "Detalles"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable directamente
            }
        };
        
        tablaRefacciones = new JTable(modeloTabla);
        tablaRefacciones.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaRefacciones.setRowHeight(25);
        tablaRefacciones.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaRefacciones.getTableHeader().setBackground(new Color(200, 200, 200));
        
        JScrollPane scrollTabla = new JScrollPane(tablaRefacciones);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panel.add(scrollTabla, BorderLayout.CENTER);
        
        cargarRefacciones();
        
        return panel;
    }
    
    /**
     * Crea el panel de entrada para nuevas refacciones.
     */
    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        // Separador
        JSeparator separador = new JSeparator();
        panel.add(separador);
        panel.add(Box.createVerticalStrut(10));
        
        JLabel labelNueva = new JLabel("Agregar Nueva Refacción");
        labelNueva.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(labelNueva);
        panel.add(Box.createVerticalStrut(8));
        
        // Campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(1, 5, 10, 0));
        panelCampos.setOpaque(false);
        
        campoCodigo = new JTextField(10);
        campoCodigo.setToolTipText("Código de barras");
        panelCampos.add(crearCampoConEtiqueta("Código:", campoCodigo));
        
        campoNombre = new JTextField(10);
        campoNombre.setToolTipText("Nombre comercial");
        panelCampos.add(crearCampoConEtiqueta("Nombre:", campoNombre));
        
        campoPrecio = new JTextField(10);
        campoPrecio.setToolTipText("Precio (debe ser mayor a 0)");
        panelCampos.add(crearCampoConEtiqueta("Precio:", campoPrecio));
        
        campoTipo = new JComboBox<>(new String[]{"Mecánica", "Eléctrica"});
        panelCampos.add(crearCampoConEtiqueta("Tipo:", campoTipo));
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 11));
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardarRefaccion());
        panelCampos.add(btnGuardar);
        
        panel.add(panelCampos);
        
        return panel;
    }
    
    /**
     * Crea un campo de entrada con su etiqueta.
     */
    private JPanel crearCampoConEtiqueta(String etiqueta, JComponent campo) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setOpaque(false);
        
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Arial", Font.BOLD, 10));
        label.setPreferredSize(new Dimension(60, 20));
        
        panel.add(label, BorderLayout.WEST);
        panel.add(campo, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Carga las refacciones en la tabla.
     */
    private void cargarRefacciones() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        List<Refaccion> refacciones = sucursal.getRefacciones();
        for (Refaccion refaccion : refacciones) {
            String detalles = "";
            if (refaccion instanceof RefaccionElectrica) {
                RefaccionElectrica re = (RefaccionElectrica) refaccion;
                detalles = "Voltaje: " + re.getVoltaje() + "V";
            } else if (refaccion instanceof RefaccionMecanica) {
                RefaccionMecanica rm = (RefaccionMecanica) refaccion;
                detalles = "Aleación: " + rm.getTipoAleacion();
            }
            
            Object[] fila = {
                refaccion.getCodigo(),
                refaccion.getNombre(),
                String.format("$%.2f", refaccion.getPrecio()),
                refaccion.getTipo(),
                detalles
            };
            modeloTabla.addRow(fila);
        }
    }
    
    /**
     * Guarda una nueva refacción.
     */
    private void guardarRefaccion() {
        try {
            String codigo = campoCodigo.getText().trim();
            String nombre = campoNombre.getText().trim();
            String precioStr = campoPrecio.getText().trim();
            String tipo = (String) campoTipo.getSelectedItem();
            
            // Validaciones
            if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios.", 
                    "Validación", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double precio = Double.parseDouble(precioStr);
            
            Refaccion refaccion;
            if ("Eléctrica".equals(tipo)) {
                refaccion = new RefaccionElectrica(codigo, nombre, precio, 110.0);
            } else {
                refaccion = new RefaccionMecanica(codigo, nombre, precio, "Acero");
            }
            
            // Agregar a sucursal y guardar en BD
            sucursal.agregarRefaccion(refaccion);
            baseDatos.guardarRefaccion(refaccion, sucursal.getIdSucursal());
            
            // Limpiar campos y actualizar tabla
            campoCodigo.setText("");
            campoNombre.setText("");
            campoPrecio.setText("");
            campoTipo.setSelectedIndex(0);
            cargarRefacciones();
            
            JOptionPane.showMessageDialog(this, 
                "Refacción guardada exitosamente.", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El precio debe ser un número válido.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Realiza auditoría técnica de la sucursal.
     */
    private void realizarAuditoriaTecnica() {
        String reporte = sucursal.realizarAuditoriaTecnica();
        
        // Crear diálogo para mostrar el reporte
        JDialog dialogoReporte = new JDialog(this, "Reporte de Auditoría Técnica", true);
        dialogoReporte.setSize(700, 500);
        dialogoReporte.setLocationRelativeTo(this);
        
        JTextArea areaTexto = new JTextArea(reporte);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 11));
        areaTexto.setEditable(false);
        areaTexto.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        dialogoReporte.add(scrollPane);
        
        dialogoReporte.setVisible(true);
    }
    
    /**
     * Muestra la ventana.
     */
    public void mostrar() {
        setVisible(true);
    }
}

