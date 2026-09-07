package mundo;

public abstract class Vehiculo 
{
    
	// ATRIBUTOS
    protected String marca;
    protected String modelo;
    protected double precio;
    
    // CONSTRUCTOR
    public Vehiculo(String marca, String modelo, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
    }

    // METODOS ABSTRACTOS
    public abstract String obtenerTipoDeVehiculo();

    // GETTERS AND SETTERS
    
    public String getMarca() 
    { return marca; }
    
    public void setMarca(String marca) 
    { this.marca = marca; }
    
    public String getModelo() 
    { return modelo; }
    
    public void setModelo(String modelo) 
    { this.modelo = modelo; }
    
    public double getPrecio() 
    { return precio; }
    
    public void setPrecio(double precio) 
    { this.precio = precio; }
    
    //METODO TO STRING

    @Override
    public String toString() 
    {
        return "Vehiculo [marca=" + marca + ", modelo=" + modelo + ", precio=" + precio + "]";
    }
}