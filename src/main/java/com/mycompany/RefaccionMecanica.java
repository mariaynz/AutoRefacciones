/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author mariayanezojeda
 */
public class RefaccionMecanica extends Refaccion {
    private String tipoAleacion;
    
    /**
     * Constructor de RefaccionMecanica.
     * @param codigo Código de barras
     * @param nombre Nombre comercial
     * @param precio Precio de la refacción
     * @param tipoAleacion Tipo de aleación del metal
     */
    public RefaccionMecanica(String codigo, String nombre, double precio, String tipoAleacion) {
        super(codigo, nombre, precio);
        this.tipoAleacion = tipoAleacion;
    }
    
    /**
     * Obtiene el tipo de aleación.
     * @return Tipo de aleación
     */
    public String getTipoAleacion() {
        return tipoAleacion;
    }
    
    /**
     * Establece el tipo de aleación.
     * @param tipoAleacion Nuevo tipo de aleación
     */
    public void setTipoAleacion(String tipoAleacion) {
        this.tipoAleacion = tipoAleacion;
    }
    
    @Override
    public String getTipo() {
        return "Mecánica";
    }
    
    @Override
    public String toString() {
        return super.toString() + " [Aleación: " + tipoAleacion + "]";
    }
}

