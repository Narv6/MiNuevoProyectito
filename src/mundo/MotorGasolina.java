package mundo;

public class MotorGasolina extends Motor 
{

    // ATRIBUTO

    private double capacidadTanque;

    // CONSTRUCTOR

    public MotorGasolina(int potencia, double capacidadTanque) 
    {

        super(potencia);

        if (capacidadTanque <= 0) 
        {
            throw new IllegalArgumentException("La capacidad del tanque debe ser mayor que 0.");
        }

        this.capacidadTanque = capacidadTanque;
    }

    // MÉTODO ABSTRACTO

    @Override
    public String obtenerTipoMotor() 
    {
        return "Motor de gasolina";
    }

    // GETTER

    public double getCapacidadTanque() 
    {
        return capacidadTanque;
    }

    // SETTER

    public void setCapacidadTanque(double capacidadTanque) 
    {

        if (capacidadTanque <= 0) 
        {
            throw new IllegalArgumentException("La capacidad del tanque debe ser mayor que 0.");
        }

        this.capacidadTanque = capacidadTanque;
    }

    // TO STRING

    @Override
    public String toString() {

        return super.toString()
                + ", capacidadTanque=" + capacidadTanque + " L";
    }
}