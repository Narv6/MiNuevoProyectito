
package mundo;

import excepciones.DatosVehiculoInvalidosException;
import excepciones.PrecioInvalidoException;
import interfaz.Mantenible;

/**
 * Clase padre de los vehiculos.
 */
public abstract class Vehiculo implements Mantenible
{
    // ATRIBUTOS

    private String marca;
    private String modelo;
    private double precio;
    private String rutaImagen;
    private Motor motor;
    private boolean mantenimientoRealizado;
    private double costoManoObra;
    private double costoRepuestos;

    // CONSTRUCTOR

    public Vehiculo(String marca, String modelo, double precio)
            throws DatosVehiculoInvalidosException, PrecioInvalidoException
    {
        setMarca(marca);
        setModelo(modelo);
        setPrecio(precio);

        this.rutaImagen = "";
        this.motor = null;
        this.mantenimientoRealizado = false;
        this.costoManoObra = 0;
        this.costoRepuestos = 0;
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

    // IMAGEN DEL VEHICULO

    public String getRutaImagen()
    {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) throws DatosVehiculoInvalidosException
    {
        if (rutaImagen != null && rutaImagen.contains(";"))
        {
            throw new DatosVehiculoInvalidosException("La ruta no puede contener punto y coma.");
        }

        if (rutaImagen == null)
        {
            this.rutaImagen = "";
        }
        else
        {
            this.rutaImagen = rutaImagen;
        }
    }

    // MOTOR DEL VEHICULO

    public Motor getMotor()
    {
        return motor;
    }

    public void setMotor(Motor motor)
    {
        this.motor = motor;
    }

    // MANTENIMIENTO

    @Override
    public void realizarMantenimiento()
    {
        mantenimientoRealizado = true;
    }

    @Override
    public double calcularCostoMantenimiento()
    {
        return costoManoObra + costoRepuestos;
    }

    public boolean isMantenimientoRealizado()
    {
        return mantenimientoRealizado;
    }

    public void setMantenimientoRealizado(boolean mantenimientoRealizado)
    {
        this.mantenimientoRealizado = mantenimientoRealizado;
    }

    public double getCostoManoObra()
    {
        return costoManoObra;
    }

    public void setCostoManoObra(double costoManoObra) throws DatosVehiculoInvalidosException
    {
        if (costoManoObra < 0)
        {
            throw new DatosVehiculoInvalidosException("El costo de mano de obra no puede ser negativo.");
        }

        this.costoManoObra = costoManoObra;
    }

    public double getCostoRepuestos()
    {
        return costoRepuestos;
    }

    public void setCostoRepuestos(double costoRepuestos) throws DatosVehiculoInvalidosException
    {
        if (costoRepuestos < 0)
        {
            throw new DatosVehiculoInvalidosException("El costo de repuestos no puede ser negativo.");
        }

        this.costoRepuestos = costoRepuestos;
    }

    // METODO TO STRING

    @Override
    public String toString()
    {
        return "Vehiculo [marca=" + marca + ", modelo=" + modelo + ", precio=" + precio + "]";
    }
}
