/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author mariayanezojeda
 */
public abstract class Refaccion {
    protected String codigo;
    protected String nombre;
    protected double precio;
    
    /**
     * Constructor de la clase Refaccion.
     * @param codigo Código de barras de la refacción
     * @param nombre Nombre comercial de la refacción
     * @param precio Precio de la refacción
     */
    public Refaccion(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    /**
     * Obtiene el código de barras de la refacción.
     * @return Código de barras
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * Obtiene el nombre comercial de la refacción.
     * @return Nombre comercial
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el precio de la refacción.
     * @return Precio
     */
    public double getPrecio() {
        return precio;
    }
    
    /**
     * Establece el precio de la refacción con validación de seguridad.
     * No permite precios negativos ni cero.
     * @param nuevoPrecio Nuevo precio a establecer
     * @throws IllegalArgumentException si el precio es inválido
     */
    public void setPrecio(double nuevoPrecio) {
        if (!validarPrecio(nuevoPrecio)) {
            throw new IllegalArgumentException(
                "El precio debe ser mayor a cero. Precio ingresado: " + nuevoPrecio
            );
        }
        this.precio = nuevoPrecio;
    }
    
    /**
     * Valida que un precio sea válido (mayor a cero).
     * @param precio Precio a validar
     * @return true si el precio es válido, false en caso contrario
     */
    public boolean validarPrecio(double precio) {
        return precio > 0;
    }
    
    /**
     * Obtiene el tipo de refacción.
     * @return Tipo de refacción
     */
    public abstract String getTipo();
    
    @Override
    public String toString() {
        return "Refacción{" +
                "código='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", tipo=" + getTipo() +
                '}';
    }
}

