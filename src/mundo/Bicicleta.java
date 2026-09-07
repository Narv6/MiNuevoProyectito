package mundo;

public class Bicicleta extends Vehiculo {

    // ATRIBUTO

    private int numeroCambios;

    // CONSTRUCTOR

    public Bicicleta(String marca, String modelo, double precio, int numeroCambios)

    {

        super(marca, modelo, precio);

        this.numeroCambios = numeroCambios;

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

    public void setNumeroCambios(int numeroCambios)

    {

        this.numeroCambios = numeroCambios;

    }

    // METODO TO STRING

    @Override

    public String toString()

    {

        return super.toString() + ", numeroCambios=" + numeroCambios;

    }

}