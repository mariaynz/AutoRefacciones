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

/**
 * Clase que representa un servidor local de una sucursal.
 * El servidor es parte estructural de la sucursal.
 * Si la sucursal cierra, el servidor se destruye.
 * Implementa la interfaz ISO para cumplir con normas de calidad.
 */
public class ServidorLocal implements ISO {
    private String ipServidor;
    private LocalDateTime ultimaCalibración;
    private boolean activo;
    
    /**
     * Constructor de ServidorLocal.
     * @param ipServidor Dirección IP del servidor
     */
    public ServidorLocal(String ipServidor) {
        this.ipServidor = ipServidor;
        this.ultimaCalibración = LocalDateTime.now();
        this.activo = true;
    }
    
    /**
     * Obtiene la dirección IP del servidor.
     * @return IP del servidor
     */
    public String getIpServidor() {
        return ipServidor;
    }
    
    /**
     * Establece la dirección IP del servidor.
     * @param ipServidor Nueva dirección IP
     */
    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }
    
    /**
     * Verifica si el servidor está activo.
     * @return true si está activo, false en caso contrario
     */
    public boolean isActivo() {
        return activo;
    }
    
    /**
     * Obtiene la fecha y hora de la última calibración.
     * @return Última calibración
     */
    public LocalDateTime getUltimaCalibración() {
        return ultimaCalibración;
    }
    
    @Override
    public String generarReporte() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "═══════════════════════════════════════════════════\n" +
               "REPORTE DE ESTADO - SERVIDOR LOCAL (ISO)\n" +
               "═══════════════════════════════════════════════════\n" +
               "Dirección IP: " + ipServidor + "\n" +
               "Estado: " + (activo ? "OPERATIVO" : "INACTIVO") + "\n" +
               "Última Calibración: " + ultimaCalibración.format(formatter) + "\n" +
               "Integridad de Datos: VERIFICADA\n" +
               "═══════════════════════════════════════════════════\n";
    }
    
    @Override
    public void calibrar() {
        this.ultimaCalibración = LocalDateTime.now();
        System.out.println("✓ Servidor Local [" + ipServidor + "] calibrado exitosamente.");
    }
    
    /**
     * Desactiva el servidor (cuando la sucursal cierra).
     */
    public void desactivar() {
        this.activo = false;
    }
    
    @Override
    public String toString() {
        return "ServidorLocal{" +
                "ip='" + ipServidor + '\'' +
                ", activo=" + activo +
                ", última calibración=" + ultimaCalibración +
                '}';
    }
}
