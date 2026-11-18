
package mundo;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;

public class Moto extends Vehiculo
{
    // ATRIBUTOS

    private int cilindraje;
    private String tipoDeUso;

    // CONSTRUCTOR

    public Moto(String marca, String modelo, double precio, int cilindraje, String tipoDeUso)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException
    {
        super(marca, modelo, precio);
        setCilindraje(cilindraje);
        setTipoDeUso(tipoDeUso);
    }

    // METODO ABSTRACTO DE MOTO

    @Override
    public String obtenerTipoDeVehiculo()
    {
        return "Motocicleta";
    }

    // GETTERS AND SETTERS

    public int getCilindraje()
    {
        return cilindraje;
    }

    public void setCilindraje(int cilindraje) throws DatosVehiculoInvalidosException
    {
        if (cilindraje <= 0)
        {
            throw new DatosVehiculoInvalidosException("El cilindraje debe ser positivo.");
        }

        this.cilindraje = cilindraje;
    }

    public String getTipoDeUso()
    {
        return tipoDeUso;
    }

    public void setTipoDeUso(String tipoDeUso) throws DatosVehiculoInvalidosException
    {
        if (tipoDeUso == null || tipoDeUso.trim().isEmpty() || tipoDeUso.contains(";"))
        {
            throw new DatosVehiculoInvalidosException("El tipo de uso no es valido.");
        }

        this.tipoDeUso = tipoDeUso;
    }

    // METODO TO STRING

    @Override
    public String toString()
    {
        return super.toString() + ", cilindraje=" + cilindraje + "cc, tipoDeUso=" + tipoDeUso;
    }
}
