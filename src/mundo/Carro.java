
package mundo;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;

public class Carro extends Vehiculo
{
    // ATRIBUTO

    private int numeroPuertas;

    // CONSTRUCTOR

    public Carro(String marca, String modelo, double precio, int numeroPuertas)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException
    {
        super(marca, modelo, precio);
        setNumeroPuertas(numeroPuertas);
    }

    // METODO ABSTRACTO DEL CARRO

    @Override
    public String obtenerTipoDeVehiculo()
    {
        return "Carro";
    }

    // GETTERS AND SETTERS

    public int getNumeroPuertas()
    {
        return numeroPuertas;
    }

    public void setNumeroPuertas(int numeroPuertas) throws DatosVehiculoInvalidosException
    {
        if (numeroPuertas <= 0)
        {
            throw new DatosVehiculoInvalidosException("El numero de puertas debe ser positivo.");
        }

        this.numeroPuertas = numeroPuertas;
    }

    // METODO TO STRING

    @Override
    public String toString()
    {
        return super.toString() + ", numeroPuertas=" + numeroPuertas;
    }
}
