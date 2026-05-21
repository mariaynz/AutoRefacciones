package com.mycompany;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar la persistencia de datos en SQLite.
 * Maneja todas las operaciones CRUD para sucursales y refacciones de forma local.
 */
public class BaseDatos {
    private Connection conexion;
    private String url;
    
    /**
     * Constructor de BaseDatos para SQLite.
     * @param url URL de conexión JDBC (Ej: "jdbc:sqlite:bd_refacciones.db")
     */
    public BaseDatos(String url, String usuario, String password) {
        this.url = url;
        this.conexion = null;
    }
    
    /**
     * Conecta a la base de datos SQLite y crea las tablas automáticamente si no existen.
     * @return true si la conexión fue exitosa
     */
    public boolean conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection(url);
            System.out.println("✓ Conexión a base de datos SQLite exitosa.");
            
            crearTablasSiNoExisten();
            
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Driver SQLite no encontrado: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar a la base de datos SQLite: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Crea automáticamente el archivo físico y las tablas si es la primera ejecución.
     */
    private void crearTablasSiNoExisten() {
        String tablaSucursales = "CREATE TABLE IF NOT EXISTS sucursales (" +
                                "id_sucursal TEXT PRIMARY KEY, " +
                                "nombre TEXT NOT NULL UNIQUE, " +
                                "direccion TEXT NOT NULL);";

        String tablaRefacciones = "CREATE TABLE IF NOT EXISTS refacciones (" +
                                 "codigo TEXT PRIMARY KEY, " +
                                 "nombre TEXT NOT NULL, " +
                                 "precio REAL NOT NULL CHECK (precio > 0), " +
                                 "tipo_refaccion TEXT NOT NULL, " +
                                 "id_sucursal TEXT, " +
                                 "FOREIGN KEY (id_sucursal) REFERENCES sucursales(id_sucursal) ON DELETE CASCADE);";
        
        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(tablaSucursales);
            stmt.execute(tablaRefacciones);
            
            // Inyectar datos semilla para que la interfaz Swing no abra vacía
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM sucursales");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO sucursales VALUES ('SUC001', 'Sucursal Centro', 'Calle Principal 123, Mérida')");
                stmt.execute("INSERT INTO sucursales VALUES ('SUC002', 'Sucursal Oriente', 'Avenida Paseo de Montejo 456, Mérida')");
                stmt.execute("INSERT INTO refacciones VALUES ('REF001', 'Engrane de Acero', 150.50, 'Mecánica', 'SUC001')");
                stmt.execute("INSERT INTO refacciones VALUES ('REF005', 'Motor Trifásico 2HP', 1200.00, 'Eléctrica', 'SUC001')");
                System.out.println("✓ Datos de prueba inicializados con éxito.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al inicializar tablas en SQLite: " + e.getMessage());
        }
    }
    
    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Desconexión de base de datos completada.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar o desconectar: " + e.getMessage());
        }
    }
    
    public List<Sucursal> obtenerSucursales() {
        List<Sucursal> sucursales = new ArrayList<>();
        String query = "SELECT * FROM sucursales";
        
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String idSucursal = rs.getString("id_sucursal");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                
                Sucursal sucursal = new Sucursal(idSucursal, nombre, direccion, "192.168.1." + Math.abs(idSucursal.hashCode() % 255));
                
                List<Refaccion> refacciones = obtenerRefacciones(idSucursal);
                for (Refaccion refaccion : refacciones) {
                    sucursal.agregarRefaccion(refaccion);
                }
                
                sucursales.add(sucursal);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener sucursales: " + e.getMessage());
        }
        return sucursales;
    }
    
    public boolean guardarSucursal(Sucursal sucursal) {
        String query = "INSERT OR REPLACE INTO sucursales (id_sucursal, nombre, direccion) VALUES (?, ?, ?)";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, sucursal.getIdSucursal());
            pstmt.setString(2, sucursal.getNombre());
            pstmt.setString(3, sucursal.getDireccion());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Error al guardar sucursal: " + e.getMessage());
            return false;
        }
    }
    
    public List<Refaccion> obtenerRefacciones(String idSucursal) {
        List<Refaccion> refacciones = new ArrayList<>();
        String query = "SELECT * FROM refacciones WHERE id_sucursal = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, idSucursal);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String codigo = rs.getString("codigo");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    String tipoRefaccion = rs.getString("tipo_refaccion");
                    
                    Refaccion refaccion;
                    if ("Eléctrica".equalsIgnoreCase(tipoRefaccion) || "Electrica".equalsIgnoreCase(tipoRefaccion)) {
                        refaccion = new RefaccionElectrica(codigo, nombre, precio, 220.0);
                    } else {
                        refaccion = new RefaccionMecanica(codigo, nombre, precio, "Acero Aleado");
                    }
                    refacciones.add(refaccion);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al obtener refacciones: " + e.getMessage());
        }
        return refacciones;
    }
    
    public boolean guardarRefaccion(Refaccion refaccion, String idSucursal) {
        String query = "INSERT OR REPLACE INTO refacciones (codigo, nombre, precio, tipo_refaccion, id_sucursal) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, refaccion.getCodigo());
            pstmt.setString(2, refaccion.getNombre());
            pstmt.setDouble(3, refaccion.getPrecio());
            pstmt.setString(4, refaccion.getTipo());
            pstmt.setString(5, idSucursal);
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Error al guardar refacción: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarRefaccion(Refaccion refaccion) {
        String query = "UPDATE refacciones SET nombre = ?, precio = ?, tipo_refaccion = ? WHERE codigo = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, refaccion.getNombre());
            pstmt.setDouble(2, refaccion.getPrecio());
            pstmt.setString(3, refaccion.getTipo());
            pstmt.setString(4, refaccion.getCodigo());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar refacción: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarRefaccion(String codigo) {
        String query = "DELETE FROM refacciones WHERE codigo = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, codigo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar refacción: " + e.getMessage());
            return false;
        }
    }
    
    public boolean estaConectada() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}