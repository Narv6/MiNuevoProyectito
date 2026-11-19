
package mundo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;

/**
 * Pruebas del inventario, la cola del taller, las ventas y los motores.
 */
public class ConcesionarioTest
{
    private Concesionario concesionario;
    private Vehiculo v1;
    private Vehiculo v2;
    private Vehiculo v3;

    @BeforeEach
    public void setUp() throws Exception
    {
        concesionario = new Concesionario();
        v1 = new Carro("Mazda", "Mazda 3", 45000.0, 4);
        v2 = new Moto("Yamaha", "MT-07", 12000.0, 700, "Urbano");
        v3 = new Camion("Chevrolet", "NPR", 85000.0, 5000.0, 2);
    }

    // ORDENAMIENTO POR BURBUJA

    @Test
    public void testOrdenamientoBurbuja() throws Exception
    {
        concesionario.agregarVehiculoInventario(v3);
        concesionario.agregarVehiculoInventario(v1);
        concesionario.agregarVehiculoInventario(v2);

        concesionario.ordenarInventarioPorPrecio();

        NodoDobleVehiculo cabeza = concesionario.getInventarioDoble().getCabeza();

        assertEquals(12000.0, cabeza.getDato().getPrecio(), 0.01);
        assertEquals(45000.0, cabeza.getSiguiente().getDato().getPrecio(), 0.01);
        assertEquals(85000.0, cabeza.getSiguiente().getSiguiente().getDato().getPrecio(), 0.01);
    }

    @Test
    public void testOrdenarInventarioVacio()
    {
        concesionario.ordenarInventarioPorPrecio();
        assertEquals(0, concesionario.getInventarioDoble().getTamanio());
    }

    @Test
    public void testOrdenarUnSoloVehiculo() throws Exception
    {
        concesionario.agregarVehiculoInventario(v1);
        concesionario.ordenarInventarioPorPrecio();
        assertEquals(v1, concesionario.getInventarioDoble().getCabeza().getDato());
    }

    // BUSQUEDA BINARIA MULTIPLE

    @Test
    public void testBusquedaBinaria() throws Exception
    {
        concesionario.agregarVehiculoInventario(v3);
        concesionario.agregarVehiculoInventario(v2);
        concesionario.agregarVehiculoInventario(v1);

        ArrayList<Vehiculo> encontrados = concesionario.buscarVehiculoPorPrecio(45000.0);
        assertEquals(1, encontrados.size());
        assertEquals("Mazda 3", encontrados.get(0).getModelo());

        ArrayList<Vehiculo> noEncontrados = concesionario.buscarVehiculoPorPrecio(99999.0);
        assertEquals(0, noEncontrados.size());
    }

    @Test
    public void testBusquedaInventarioVacio()
    {
        ArrayList<Vehiculo> encontrados = concesionario.buscarVehiculoPorPrecio(45000.0);
        assertEquals(0, encontrados.size());
    }

    @Test
    public void testBuscarVariosVehiculosConMismoPrecio() throws Exception
    {
        Vehiculo carro1 = new Carro("Mazda", "Mazda 3", 45000.0, 4);
        Vehiculo carro2 = new Carro("Toyota", "Corolla", 45000.0, 4);
        Vehiculo moto = new Moto("Yamaha", "MT-07", 12000.0, 700, "Urbano");

        concesionario.agregarVehiculoInventario(carro1);
        concesionario.agregarVehiculoInventario(carro2);
        concesionario.agregarVehiculoInventario(moto);

        assertEquals(2, concesionario.buscarVehiculoPorPrecio(45000.0).size());
    }

    // COLA FIFO

    @Test
    public void testColaTallerFIFO() throws Exception
    {
        concesionario.agregarVehiculoInventario(v1);
        concesionario.agregarVehiculoInventario(v2);

        concesionario.registrarVehiculoEnTaller(v1);
        concesionario.registrarVehiculoEnTaller(v2);

        assertEquals(v1, concesionario.atenderSiguienteVehiculoTaller());
        assertEquals(v2, concesionario.atenderSiguienteVehiculoTaller());
        assertNull(concesionario.atenderSiguienteVehiculoTaller());
    }

    @Test
    public void testTallerVacio()
    {
        assertNull(concesionario.atenderSiguienteVehiculoTaller());
    }

    // PILA LIFO

    @Test
    public void testPilaVentasLIFO() throws Exception
    {
        concesionario.agregarVehiculoInventario(v1);
        concesionario.agregarVehiculoInventario(v2);

        concesionario.registrarVenta(v1);
        concesionario.registrarVenta(v2);

        assertEquals(0, concesionario.getInventarioDoble().getTamanio());
        assertEquals(v2, concesionario.cancelarUltimaVenta());
        assertEquals(v1, concesionario.cancelarUltimaVenta());
        assertEquals(2, concesionario.getInventarioDoble().getTamanio());
        assertNull(concesionario.cancelarUltimaVenta());
    }

    @Test
    public void testPilaVentasVacia()
    {
        assertNull(concesionario.cancelarUltimaVenta());
    }

    // FILTROS POR TIPO

    @Test
    public void testFiltrosPorTipo() throws Exception
    {
        concesionario.agregarVehiculoInventario(v1);
        concesionario.agregarVehiculoInventario(v2);
        concesionario.agregarVehiculoInventario(v3);
        concesionario.agregarVehiculoInventario(new Bicicleta("Trek", "Marlin", 900.0, 18));

        assertEquals(1, concesionario.obtenerCarros().size());
        assertEquals(1, concesionario.obtenerMotos().size());
        assertEquals(1, concesionario.obtenerCamiones().size());
        assertEquals(1, concesionario.obtenerBicicletas().size());
    }

    // EXCEPCIONES PERSONALIZADAS

    @Test
    public void testPrecioInvalido() throws Exception
    {
        boolean lanzoExcepcion = false;

        try
        {
            new Carro("Mazda", "3", -50, 4);
        }
        catch (PrecioInvalidoException e)
        {
            lanzoExcepcion = true;
        }

        assertEquals(true, lanzoExcepcion);
    }

    @Test
    public void testPuertasInvalidas() throws Exception
    {
        boolean lanzoExcepcion = false;

        try
        {
            new Carro("Mazda", "3", 50000, 0);
        }
        catch (DatosVehiculoInvalidosException e)
        {
            lanzoExcepcion = true;
        }

        assertEquals(true, lanzoExcepcion);
    }

    // MOTOR ELECTRICO

    @Test
    public void testAutonomiaMotorElectrico()
    {
        MotorElectrico motor = new MotorElectrico(150, 60);
        motor.setConsumoCada100Km(15);
        assertEquals(400.0, motor.calcularAutonomia(), 0.01);
    }

    // MANTENIMIENTO

    @Test
    public void testMantenimientoVehiculo() throws Exception
    {
        Vehiculo carro = new Carro("Mazda", "Mazda 3", 45000, 4);

        concesionario.agregarVehiculoInventario(carro);
        concesionario.registrarVehiculoEnTaller(carro);

        carro.setCostoManoObra(100);
        carro.setCostoRepuestos(80);

        Vehiculo atendido = concesionario.atenderSiguienteVehiculoTaller();

        assertEquals(carro, atendido);
        assertTrue(carro.isMantenimientoRealizado());
        assertEquals(180.0, carro.calcularCostoMantenimiento(), 0.01);
    }
}
