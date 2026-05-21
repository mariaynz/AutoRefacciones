package com.mycompany;

import javax.swing.*;

/**
 * Punto de entrada principal para el proyecto AutoRefacciones2 configurado con SQLite.
 * @author mariayanezojeda
 */
public class AutoRefacciones2 {
    
    public static void main(String[] args) {
        // Configurar el estilo visual nativo del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Cadena de conexión corregida para el motor local SQLite
        String url = "jdbc:sqlite:bd_refacciones.db";
        String usuario = "";  
        String password = ""; 
        
        // Crear instancia de la base de datos local
        BaseDatos baseDatos = new BaseDatos(url, usuario, password);
        
        // Intentar conectar e inicializar tablas automáticas
        if (!baseDatos.conectar()) {
            JOptionPane.showMessageDialog(null,
                "No se pudo inicializar la base de datos SQLite local.\n" +
                "Verifica que las dependencias de Maven se hayan descargado correctamente.",
                "Error de Conexión",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Desplegar la interfaz gráfica de usuario en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaApp = new VentanaPrincipal(baseDatos);
            ventanaApp.mostrar();
        });
    }
}