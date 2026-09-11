package mundo;


public class MotorElectrico extends Motor 
{

    // ATRIBUTO

    private double capacidadBateria;

    // CONSTRUCTOR

    public MotorElectrico(int potencia, double capacidadBateria) 
    {

        super(potencia);

        if (capacidadBateria <= 0) 
        {
            throw new IllegalArgumentException("La capacidad de la batería debe ser mayor que 0.");
        }

        this.capacidadBateria = capacidadBateria;
    }

    // MÉTODO ABSTRACTO

    @Override
    public String obtenerTipoMotor() 
    {
        return "Motor eléctrico";
    }

    // GETTER

    public double getCapacidadBateria() 
    
    {
        return capacidadBateria;
    }

    // SETTER

    public void setCapacidadBateria(double capacidadBateria) 
    {

        if (capacidadBateria <= 0) 
        {
            throw new IllegalArgumentException("La capacidad de la batería debe ser mayor que 0.");
        }

        this.capacidadBateria = capacidadBateria;
    }

    // MÉTODO

    public void cargarBateria() 
    {

        System.out.println("La batería se está cargando...");
    }

    // TO STRING

    @Override
    public String toString() {

        return super.toString()
                + ", capacidadBateria=" + capacidadBateria + " kWh";
    }
}