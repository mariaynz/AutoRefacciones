```mermaid
classDiagram
    direction text-to-bottom

    %% Interfaz de Calidad
    class ISO {
        <<interface>>
        +generarReporteEstado() void
        +calibrar() void
    }

    %% Clases Principales de la Lógica
    class Sucursal {
        -String idSucursal
        -String nombre
        -String direccion
        -ServidorLocal servidor
        -List~Vendedor~ vendedores
        -List~Refaccion~ inventario
        +auditoriaTecnica() void
        +agregarRefaccion(Refaccion r) void
        +agregarVendedor(Vendedor v) void
    }

    class ServidorLocal {
        -String idServidor
        -String ipLocal
        +generarReporteEstado() void
        +calibrar() void
    }

    class Vendedor {
        -String numEmpleado
        -String nombre
    }

    %% Jerarquía de Herencia (Refacciones)
    class Refaccion {
        <<abstract>>
        -String codigo
        -String nombre
        -double precio
        +getCodigo() String
        +getNombre() String
        +getPrecio() double
        +getTipo() String*
    }

    class RefaccionMecanica {
        -String aleacionMetal
        +getAleacionMetal() String
    }

    class RefaccionElectrica {
        -double voltaje
        +generarReporteEstado() void
        +calibrar() void
    }

    %% Conexión de Persistencia
    class BaseDatos {
        -Connection conexion
        -String url
        +conectar() boolean
        +desconectar() void
        +obtenerSucursales() List~Sucursal~
        +guardarSucursal(Sucursal s) boolean
        +obtenerRefacciones(String idSucursal) List~Refaccion~
        +guardarRefaccion(Refaccion r, String idSucursal) boolean
    }

    %% Relaciones de Dependencia y Estructura
    ISO <|.. ServidorLocal : Implementa
    ISO <|.. RefaccionElectrica : Implementa

    Sucursal "1" *-- "1" ServidorLocal : Composición (Estructural)
    Sucursal "1" o-- "0..*" Vendedor : Agregación (Reubicables)
    Sucursal "1" o-- "0..*" Refaccion : Agregación (Inventario)

    Refaccion <|-- RefaccionMecanica : Herencia
    Refaccion <|-- RefaccionElectrica : Herencia

    VentanaPrincipal ..> BaseDatos : Usa
    VentanaSucursal ..> BaseDatos : Usa
    VentanaSucursal ..> Sucursal : Gestiona
