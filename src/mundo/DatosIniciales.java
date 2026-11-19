package mundo;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;
import excepciones.CapacidadCargaInvalidaException;

/**
 * Crea los vehiculos de ejemplo cuando no existe un archivo guardado.
 */
public class DatosIniciales
{
    public static Concesionario crearConcesionario()
            throws DatosVehiculoInvalidosException, PrecioInvalidoException, CapacidadCargaInvalidaException
    {
        Concesionario concesionario = new Concesionario();

        // Mismos vehiculos que tenia el Main original.

        Vehiculo v1 = new Carro("Mazda", "Mazda 3", 45000.0, 4);
        Vehiculo v2 = new Moto("Yamaha", "MT-07", 12000.0, 700, "Urbano");
        Vehiculo v3 = new Camion("Chevrolet", "NPR", 85000.0, 5000.0, 2);

        
        v1.setRutaImagen("imagenes/mazda.png");
        v2.setRutaImagen("imagenes/yamaha.jpg");
        v3.setRutaImagen("imagenes/chevrolet.jpg");

        concesionario.agregarVehiculoInventario(v3);
        concesionario.agregarVehiculoInventario(v1);
        concesionario.agregarVehiculoInventario(v2);

        return concesionario;
    }
}
