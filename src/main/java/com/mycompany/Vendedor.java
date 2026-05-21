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
 * Clase que representa un vendedor de la empresa.
 * Los vendedores están asignados a sucursales pero pueden ser reasignados.
 * Si una sucursal cierra, el vendedor se reasigna, no se despide.
 */
public class Vendedor {
    private String numeroEmpleado;
    private String nombre;
    private String idSucursal;
    
    /**
     * Constructor de Vendedor.
     * @param numeroEmpleado Número único de empleado
     * @param nombre Nombre del vendedor
     * @param idSucursal ID de la sucursal asignada
     */
    public Vendedor(String numeroEmpleado, String nombre, String idSucursal) {
        this.numeroEmpleado = numeroEmpleado;
        this.nombre = nombre;
        this.idSucursal = idSucursal;
    }
    
    /**
     * Obtiene el número de empleado.
     * @return Número de empleado
     */
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }
    
    /**
     * Obtiene el nombre del vendedor.
     * @return Nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el ID de la sucursal actual.
     * @return ID de sucursal
     */
    public String getIdSucursal() {
        return idSucursal;
    }
    
    /**
     * Reasigna el vendedor a una nueva sucursal.
     * @param nuevoIdSucursal ID de la nueva sucursal
     */
    public void setIdSucursal(String nuevoIdSucursal) {
        this.idSucursal = nuevoIdSucursal;
    }
    
    @Override
    public String toString() {
        return "Vendedor{" +
                "número=" + numeroEmpleado +
                ", nombre='" + nombre + '\'' +
                ", sucursal='" + idSucursal + '\'' +
                '}';
    }
}

