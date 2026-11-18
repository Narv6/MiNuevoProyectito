
package mundo;

import excepciones.CapacidadCargaInvalidaException;
import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;

public class Camion extends Vehiculo
{
    // ATRIBUTOS

    private double capacidadCarga;
    private int numeroEjes;

    // CONSTRUCTOR

    public Camion(String marca, String modelo, double precio, double capacidadCarga, int numeroEjes)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException, CapacidadCargaInvalidaException
    {
        super(marca, modelo, precio);
        setCapacidadCarga(capacidadCarga);
        setNumeroEjes(numeroEjes);
    }

    // METODO ABSTRACTO DEL CAMION

    @Override
    public String obtenerTipoDeVehiculo()
    {
        return "Camion";
    }

    // GETTERS AND SETTERS

    public double getCapacidadCarga()
    {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) throws CapacidadCargaInvalidaException
    {
        if (capacidadCarga <= 0)
        {
            throw new CapacidadCargaInvalidaException("La capacidad de carga debe ser positiva.");
        }

        this.capacidadCarga = capacidadCarga;
    }

    public int getNumeroEjes()
    {
        return numeroEjes;
    }

    public void setNumeroEjes(int numeroEjes) throws DatosVehiculoInvalidosException
    {
        if (numeroEjes <= 0)
        {
            throw new DatosVehiculoInvalidosException("El numero de ejes debe ser positivo.");
        }

        this.numeroEjes = numeroEjes;
    }

    // METODO TO STRING

    @Override
    public String toString()
    {
        return super.toString() + ", capacidadCarga=" + capacidadCarga + ", numeroEjes=" + numeroEjes;
    }
}
