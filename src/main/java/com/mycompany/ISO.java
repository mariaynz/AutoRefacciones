/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author mariayanezojeda
 */
/**
 * Interfaz que define el contrato para equipos que cumplen con normas ISO.
 * Estos equipos deben ser capaces de generar reportes y calibrarse.
 */
public interface ISO {
    /**
     * Genera un reporte de estado del equipo.
     * @return String con el contenido del reporte
     */
    String generarReporte();
    
    /**
     * Calibra el equipo según normas ISO.
     */
    void calibrar();
}
