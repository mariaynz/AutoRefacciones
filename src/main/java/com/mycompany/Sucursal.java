/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mariayanezojeda
 */
/**
 * Clase que representa una sucursal de la empresa.
 * Cada sucursal tiene un servidor local, vendedores asignados e inventario.
 * Gestiona las refacciones y realiza auditorías técnicas.
 */
public class Sucursal {
    private String idSucursal;
    private String nombre;
    private String direccion;
    private ServidorLocal servidorLocal;
    private List<Vendedor> vendedores;
    private List<Refaccion> refacciones;
    
    /**
     * Constructor de Sucursal.
     * @param idSucursal Identificador único de la sucursal
     * @param nombre Nombre de la sucursal
     * @param direccion Dirección de la sucursal
     * @param ipServidor IP del servidor local
     */
    public Sucursal(String idSucursal, String nombre, String direccion, String ipServidor) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.servidorLocal = new ServidorLocal(ipServidor);
        this.vendedores = new ArrayList<>();
        this.refacciones = new ArrayList<>();
    }
    
    /**
     * Obtiene el identificador de la sucursal.
     * @return ID de sucursal
     */
    public String getIdSucursal() {
        return idSucursal;
    }
    
    /**
     * Obtiene el nombre de la sucursal.
     * @return Nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene la dirección de la sucursal.
     * @return Dirección
     */
    public String getDireccion() {
        return direccion;
    }
    
    /**
     * Obtiene el servidor local de la sucursal.
     * @return Servidor local
     */
    public ServidorLocal getServidorLocal() {
        return servidorLocal;
    }
    
    /**
     * Obtiene la lista de vendedores.
     * @return Lista de vendedores
     */
    public List<Vendedor> getVendedores() {
        return new ArrayList<>(vendedores);
    }
    
    /**
     * Obtiene la lista de refacciones.
     * @return Lista de refacciones
     */
    public List<Refaccion> getRefacciones() {
        return new ArrayList<>(refacciones);
    }
    
    /**
     * Agrega un vendedor a la sucursal.
     * @param vendedor Vendedor a agregar
     */
    public void agregarVendedor(Vendedor vendedor) {
        if (vendedor != null && !vendedores.contains(vendedor)) {
            vendedores.add(vendedor);
        }
    }
    
    /**
     * Agrega una refacción al inventario.
     * @param refaccion Refacción a agregar
     */
    public void agregarRefaccion(Refaccion refaccion) {
        if (refaccion != null) {
            // Elimina si ya existe
            refacciones.removeIf(r -> r.getCodigo().equals(refaccion.getCodigo()));
            refacciones.add(refaccion);
        }
    }
    
    /**
     * Elimina una refacción del inventario.
     * @param codigo Código de la refacción a eliminar
     * @return true si se eliminó, false si no existe
     */
    public boolean eliminarRefaccion(String codigo) {
        return refacciones.removeIf(r -> r.getCodigo().equals(codigo));
    }
    
    /**
     * Actualiza el precio de una refacción.
     * @param codigo Código de la refacción
     * @param nuevoPrecio Nuevo precio
     * @return true si se actualizó, false si no existe
     */
    public boolean actualizarRefaccion(String codigo, double nuevoPrecio) {
        for (Refaccion r : refacciones) {
            if (r.getCodigo().equals(codigo)) {
                r.setPrecio(nuevoPrecio); // Usa validación interna
                return true;
            }
        }
        return false;
    }
    
    /**
     * Realiza auditoría técnica de la sucursal.
     * Instruye a todos los equipos ISO a generar reportes y calibrarse.
     * @return String con los reportes consolidados
     */
    public String realizarAuditoriaTecnica() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n\n╔════════════════════════════════════════════════════════════════════════════════╗\n");
        reporte.append("║                    AUDITORÍA TÉCNICA SUCURSAL - ").append(nombre).append("\n");
        reporte.append("╚════════════════════════════════════════════════════════════════════════════════╝\n\n");
        
        // Auditoría del Servidor Local
        reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        reporte.append(servidorLocal.generarReporte());
        servidorLocal.calibrar();
        
        // Auditoría de Refacciones Eléctricas
        boolean hayRefaccionesElectricas = false;
        for (Refaccion r : refacciones) {
            if (r instanceof ISO) {
                hayRefaccionesElectricas = true;
                reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                ISO equipo = (ISO) r;
                reporte.append(equipo.generarReporte());
                equipo.calibrar();
            }
        }
        
        if (!hayRefaccionesElectricas) {
            reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            reporte.append("ℹ No hay refacciones eléctricas en este inventario.\n");
        }
        
        reporte.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        reporte.append("\n✓ AUDITORÍA TÉCNICA COMPLETADA EXITOSAMENTE.\n");
        
        return reporte.toString();
    }
    
    @Override
    public String toString() {
        return "Sucursal{" +
                "id='" + idSucursal + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dirección='" + direccion + '\'' +
                ", vendedores=" + vendedores.size() +
                ", refacciones=" + refacciones.size() +
                '}';
    }
}

