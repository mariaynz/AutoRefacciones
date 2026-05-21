/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author mariayanezojeda
 */
public class RefaccionElectrica extends Refaccion implements ISO {
    private double voltaje;
    private LocalDateTime ultimaCalibración;
    
    /**
     * Constructor de RefaccionElectrica.
     * @param codigo Código de barras
     * @param nombre Nombre comercial
     * @param precio Precio de la refacción
     * @param voltaje Voltaje específico de la refacción
     */
    public RefaccionElectrica(String codigo, String nombre, double precio, double voltaje) {
        super(codigo, nombre, precio);
        this.voltaje = voltaje;
        this.ultimaCalibración = LocalDateTime.now();
    }
    
    /**
     * Obtiene el voltaje de la refacción.
     * @return Voltaje
     */
    public double getVoltaje() {
        return voltaje;
    }
    
    /**
     * Establece el voltaje de la refacción.
     * @param voltaje Nuevo voltaje
     */
    public void setVoltaje(double voltaje) {
        this.voltaje = voltaje;
    }
    
    /**
     * Obtiene la fecha y hora de la última calibración.
     * @return Última calibración
     */
    public LocalDateTime getUltimaCalibración() {
        return ultimaCalibración;
    }
    
    @Override
    public String getTipo() {
        return "Eléctrica";
    }
    
    @Override
    public String generarReporte() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "═══════════════════════════════════════════════════\n" +
               "REPORTE DE ESTADO - REFACCIÓN ELÉCTRICA (ISO)\n" +
               "═══════════════════════════════════════════════════\n" +
               "Código: " + codigo + "\n" +
               "Nombre: " + nombre + "\n" +
               "Voltaje: " + voltaje + " V\n" +
               "Precio: $" + String.format("%.2f", precio) + "\n" +
               "Última Calibración: " + ultimaCalibración.format(formatter) + "\n" +
               "Estado: OPERATIVO\n" +
               "═══════════════════════════════════════════════════\n";
    }
    
    @Override
    public void calibrar() {
        this.ultimaCalibración = LocalDateTime.now();
        System.out.println("✓ Refacción Eléctrica [" + codigo + "] calibrada exitosamente.");
    }
    
    @Override
    public String toString() {
        return super.toString() + " [Voltaje: " + voltaje + "V]";
    }
}
