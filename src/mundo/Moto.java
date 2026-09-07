package mundo;

public class Moto extends Vehiculo 
{
    
    // ATRIBUTOS
    private int cilindraje;
    private String tipoDeUso;

    // CONSTRUCTOR
    public Moto(String marca, String modelo, double precio, int cilindraje, String tipoDeUso) 
    {
        super(marca, modelo, precio);
        
        this.cilindraje = cilindraje;
        this.tipoDeUso = tipoDeUso;
    }

    // METODO ABSTRACTO DE MOTO
    @Override
    public String obtenerTipoDeVehiculo() {
        return "Motocicleta";
    }

    // GETTERS AND SETTERS
    public int getCilindraje() 
    {
        return cilindraje;
    }

    public void setCilindraje(int cilindraje) 
    {
        this.cilindraje = cilindraje;
    }

    public String getTipoDeUso() 
    {
        return tipoDeUso;
    }

    public void setTipoDeUso(String tipoDeUso) 
    {
        this.tipoDeUso = tipoDeUso;
    }

    // METODO TO STRING
    @Override
    public String toString() 
    {
        return super.toString() + ", cilindraje=" + cilindraje + "cc, tipoDeUso=" + tipoDeUso;
    }
}