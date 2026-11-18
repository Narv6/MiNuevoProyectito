
package mundo;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;

public class Bicicleta extends Vehiculo
{
    // ATRIBUTO

    private int numeroCambios;

    // CONSTRUCTOR

    public Bicicleta(String marca, String modelo, double precio, int numeroCambios)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException
    {
        super(marca, modelo, precio);
        setNumeroCambios(numeroCambios);
    }

    // METODO ABSTRACTO DE LA BICICLETA

    @Override
    public String obtenerTipoDeVehiculo()
    {
        return "Bicicleta";
    }

    // GETTERS AND SETTERS

    public int getNumeroCambios()
    {
        return numeroCambios;
    }

    public void setNumeroCambios(int numeroCambios) throws DatosVehiculoInvalidosException
    {
        if (numeroCambios < 0)
        {
            throw new DatosVehiculoInvalidosException("El numero de cambios no puede ser negativo.");
        }

        this.numeroCambios = numeroCambios;
    }

    // METODO TO STRING

    @Override
    public String toString()
    {
        return super.toString() + ", numeroCambios=" + numeroCambios;
    }
}
