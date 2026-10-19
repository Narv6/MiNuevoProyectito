package mundo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para validar la lógica del Concesionario, 
 * estructuras de datos y algoritmos.
 */
public class ConcesionarioTest {

    private Concesionario concesionario;
    private Vehiculo v1;
    private Vehiculo v2;
    private Vehiculo v3;

    @BeforeEach
    public void setUp() {
        concesionario = new Concesionario();
        v1 = new Carro("Mazda", "Mazda 3", 45000.0, 4);
        v2 = new Moto("Yamaha", "MT-07", 12000.0, 700, "Urbano");
        v3 = new Camion("Chevrolet", "NPR", 85000.0, 5000.0, 2);
    }

    @Test
    public void testOrdenamientoBurbuja() {
        // Agregamos en orden desordenado
        concesionario.agregarVehiculoInventario(v3); // 85000
        concesionario.agregarVehiculoInventario(v1); // 45000
        concesionario.agregarVehiculoInventario(v2); // 12000

        // Ejecutamos el ordenamiento por burbuja
        concesionario.ordenarInventarioPorPrecioBurbuja();

        // Verificamos el nuevo orden en la lista doble (de menor a mayor precio)
        NodoDobleVehiculo cabeza = concesionario.getInventarioDoble().getCabeza();
        
        assertEquals(12000.0, cabeza.getDato().getPrecio(), 0.01);
        assertEquals(45000.0, cabeza.getSiguiente().getDato().getPrecio(), 0.01);
        assertEquals(85000.0, cabeza.getSiguiente().getSiguiente().getDato().getPrecio(), 0.01);
    }

    @Test
    public void testBusquedaBinaria() {
        // Agregamos y ordenamos primero
        concesionario.agregarVehiculoInventario(v2); // 12000
        concesionario.agregarVehiculoInventario(v1); // 45000
        concesionario.agregarVehiculoInventario(v3); // 85000
        concesionario.ordenarInventarioPorPrecioBurbuja();

        // Buscamos un precio existente
        Vehiculo encontrado = concesionario.buscarVehiculoBinarioPorPrecio(45000.0);
        assertNotNull(encontrado);
        assertEquals("Mazda 3", encontrado.getModelo());

        // Buscamos un precio que no existe
        Vehiculo noEncontrado = concesionario.buscarVehiculoBinarioPorPrecio(99999.0);
        assertNull(noEncontrado);
    }

    @Test
    public void testColaTallerFIFO() {
        concesionario.registrarVehiculoEnTaller(v1); // Primero en llegar
        concesionario.registrarVehiculoEnTaller(v2); // Segundo en llegar

        // El primero en ser atendido debe ser v1 (FIFO)
        Vehiculo atendido1 = concesionario.atenderSiguienteVehiculoTaller();
        assertEquals(v1, atendido1);

        // El siguiente debe ser v2
        Vehiculo atendido2 = concesionario.atenderSiguienteVehiculoTaller();
        assertEquals(v2, atendido2);

        // Si la cola está vacía debe retornar null
        assertNull(concesionario.atenderSiguienteVehiculoTaller());
    }

    @Test
    public void testPilaVentasLIFO() {
        concesionario.registrarVenta(v1); // Primera venta
        concesionario.registrarVenta(v2); // Última venta

        // Al cancelar/revertir, la última venta registrada (v2) debe salir primero (LIFO)
        Vehiculo cancelada1 = concesionario.cancelarUltimaVenta();
        assertEquals(v2, cancelada1);

        // Luego debe salir v1
        Vehiculo cancelada2 = concesionario.cancelarUltimaVenta();
        assertEquals(v1, cancelada2);

        // Si la pila está vacía debe retornar null
        assertNull(concesionario.cancelarUltimaVenta());
    }
}