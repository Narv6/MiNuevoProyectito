
package mundo;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;

public abstract class Vehiculo
{
    // ATRIBUTOS

    private String marca;
    private String modelo;
    private double precio;

    // CONSTRUCTOR

    public Vehiculo(String marca, String modelo, double precio)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException
    {
        setMarca(marca);
        setModelo(modelo);
        setPrecio(precio);
    }

    // METODO ABSTRACTO

    public abstract String obtenerTipoDeVehiculo();

    // GETTERS AND SETTERS

    public String getMarca()
    {
        return marca;
    }

    public void setMarca(String marca) throws DatosVehiculoInvalidosException
    {
        if (marca == null || marca.trim().isEmpty() || marca.contains(";"))
        {
            throw new DatosVehiculoInvalidosException("La marca no es valida.");
        }

        this.marca = marca;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo) throws DatosVehiculoInvalidosException
    {
        if (modelo == null || modelo.trim().isEmpty() || modelo.contains(";"))
        {
            throw new DatosVehiculoInvalidosException("El modelo no es valido.");
        }

        this.modelo = modelo;
    }

    public double getPrecio()
    {
        return precio;
    }

    public void setPrecio(double precio) throws PrecioInvalidoException
    {
        if (precio <= 0)
        {
            throw new PrecioInvalidoException("El precio debe ser mayor que cero.");
        }

        this.precio = precio;
    }

    // METODO TO STRING

    @Override
    public String toString()
    {
        return "Vehiculo [marca=" + marca + ", modelo=" + modelo + ", precio=" + precio + "]";
    }
}
