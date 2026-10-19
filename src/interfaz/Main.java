package interfaz;

import mundo.Concesionario;
import mundo.Vehiculo;
import mundo.Carro;
import mundo.Moto;
import mundo.Camion;

public class Main {

    public static void main(String[] args) {
        Concesionario concesionario = new Concesionario();

        // Instanciación de vehículos
        Vehiculo v1 = new Carro("Mazda", "Mazda 3", 45000.0, 4);
        Vehiculo v2 = new Moto("Yamaha", "MT-07", 12000.0, 700, "Urbano");
        Vehiculo v3 = new Camion("Chevrolet", "NPR", 85000.0, 5000.0, 2);

        // Registro e inventario
        concesionario.agregarVehiculoInventario(v3);
        concesionario.agregarVehiculoInventario(v1);
        concesionario.agregarVehiculoInventario(v2);

        // Ordenamiento y búsqueda
        concesionario.ordenarInventarioPorPrecioBurbuja();
        Vehiculo encontrado = concesionario.buscarVehiculoBinarioPorPrecio(45000.0);
        System.out.println("Vehículo encontrado: " + (encontrado != null ? encontrado.getModelo() : "Ninguno"));

        // Operaciones de taller (FIFO) y ventas (LIFO)
        concesionario.registrarVehiculoEnTaller(v1);
        System.out.println("Atendido en taller: " + concesionario.atenderSiguienteVehiculoTaller().getModelo());

        concesionario.registrarVenta(v2);
        System.out.println("Venta cancelada: " + concesionario.cancelarUltimaVenta().getModelo());
    }
}