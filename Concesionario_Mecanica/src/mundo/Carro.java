package mundo;

public class Carro extends Vehiculo {
    
    // ATRIBUTO
	
    private int numeroPuertas;

    // CONSTRUCTOR
    public Carro(String marca, String modelo, double precio, int numeroPuertas) 
    {
        super(marca, modelo, precio);
        
        this.numeroPuertas = numeroPuertas;
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

    public void setNumeroPuertas(int numeroPuertas) 
    {
        this.numeroPuertas = numeroPuertas;
    }

    // METODO TO STRING
    
    @Override
    public String toString() 
    {
        return super.toString() + ", numeroPuertas=" + numeroPuertas;
    }
}