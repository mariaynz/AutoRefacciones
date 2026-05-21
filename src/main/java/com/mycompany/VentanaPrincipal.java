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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana principal de la aplicación.
 * Muestra una lista de sucursales desde la cual el usuario puede seleccionar una
 * para gestionar su inventario.
 */
public class VentanaPrincipal extends JFrame {
    private BaseDatos baseDatos;
    private JList<String> listaSucursales;
    private DefaultListModel<String> modeloLista;
    private List<Sucursal> sucursales;
    
    /**
     * Constructor de VentanaPrincipal.
     * @param baseDatos Instancia de base de datos
     */
    public VentanaPrincipal(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
        this.sucursales = new ArrayList<>();
        inicializar();
    }
    
    /**
     * Inicializa los componentes de la ventana principal.
     */
    private void inicializar() {
        setTitle("AutoRefacciones - Sistema de Gestión de Inventario");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(245, 245, 245));
        
        // Título
        JLabel labelTitulo = new JLabel("Seleccionar Sucursal");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(33, 33, 33));
        panelPrincipal.add(labelTitulo, BorderLayout.NORTH);
        
        // Modelo y lista de sucursales
        modeloLista = new DefaultListModel<>();
        listaSucursales = new JList<>(modeloLista);
        listaSucursales.setFont(new Font("Arial", Font.PLAIN, 14));
        listaSucursales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Scroll para la lista
        JScrollPane scrollPane = new JScrollPane(listaSucursales);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setOpaque(false);
        
        // Botón abrir
        JButton btnAbrir = new JButton("Abrir Sucursal");
        btnAbrir.setFont(new Font("Arial", Font.BOLD, 12));
        btnAbrir.setBackground(new Color(76, 175, 80));
        btnAbrir.setForeground(Color.WHITE);
        btnAbrir.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnAbrir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAbrir.addActionListener(e -> abrirSucursal());
        
        // Botón actualizar
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setBackground(new Color(2, 136, 209));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> cargarSucursales());
        
        panelBotones.add(btnAbrir);
        panelBotones.add(btnActualizar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        cargarSucursales();
    }
    
    /**
     * Carga las sucursales desde la base de datos.
     */
    private void cargarSucursales() {
        modeloLista.clear();
        sucursales = baseDatos.obtenerSucursales();
        
        if (sucursales.isEmpty()) {
            modeloLista.addElement("No hay sucursales registradas");
        } else {
            for (Sucursal sucursal : sucursales) {
                modeloLista.addElement(sucursal.getNombre() + " - " + sucursal.getDireccion());
            }
        }
    }
    
    /**
     * Abre la ventana de gestión de la sucursal seleccionada.
     */
    private void abrirSucursal() {
        int indiceSeleccionado = listaSucursales.getSelectedIndex();
        
        if (indiceSeleccionado < 0 || sucursales.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona una sucursal.", 
                "Selección Requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Sucursal sucursal = sucursales.get(indiceSeleccionado);
        VentanaSucursal ventanaSucursal = new VentanaSucursal(sucursal, baseDatos);
        ventanaSucursal.mostrar();
    }
    
    /**
     * Muestra la ventana principal.
     */
    public void mostrar() {
        setVisible(true);
    }
}

