package mundo;

public class Camion extends Vehiculo {

    // ATRIBUTOS

    private double capacidadCarga;

    private int numeroEjes;

    // CONSTRUCTOR

    public Camion(String marca, String modelo, double precio, double capacidadCarga, int numeroEjes)

    {

        super(marca, modelo, precio);

        this.capacidadCarga = capacidadCarga;

        this.numeroEjes = numeroEjes;

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

    public void setCapacidadCarga(double capacidadCarga)

    {

        this.capacidadCarga = capacidadCarga;

    }

    public int getNumeroEjes()

    {

        return numeroEjes;

    }

    public void setNumeroEjes(int numeroEjes)

    {

        this.numeroEjes = numeroEjes;

    }

    // METODO TO STRING

    @Override

    public String toString()

    {

        return super.toString() + ", capacidadCarga=" + capacidadCarga
                + ", numeroEjes=" + numeroEjes;

    }

}