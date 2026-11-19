
package mundo;

import interfaz.Electrico;

/**
 * Motor electrico con calculo de autonomia estimada.
 */
public class MotorElectrico extends Motor implements Electrico
{
    // ATRIBUTOS

    private double capacidadBateria;
    private double consumoCada100Km;

    // CONSTRUCTOR

    public MotorElectrico(int potencia, double capacidadBateria)
    {
        super(potencia);

        if (capacidadBateria <= 0)
        {
            throw new IllegalArgumentException("La capacidad de la bateria debe ser mayor que 0.");
        }

        this.capacidadBateria = capacidadBateria;
        this.consumoCada100Km = 0;
    }

    // TIPO DE MOTOR

    @Override
    public String obtenerTipoMotor()
    {
        return "Motor electrico";
    }

    // GETTERS AND SETTERS

    public double getCapacidadBateria()
    {
        return capacidadBateria;
    }

    public void setCapacidadBateria(double capacidadBateria)
    {
        if (capacidadBateria <= 0)
        {
            throw new IllegalArgumentException("La capacidad de la bateria debe ser mayor que 0.");
        }

        this.capacidadBateria = capacidadBateria;
    }

    public double getConsumoCada100Km()
    {
        return consumoCada100Km;
    }

    public void setConsumoCada100Km(double consumoCada100Km)
    {
        if (consumoCada100Km <= 0)
        {
            throw new IllegalArgumentException("El consumo debe ser mayor que cero.");
        }

        this.consumoCada100Km = consumoCada100Km;
    }

    // INTERFAZ ELECTRICO

    @Override
    public void cargarBateria()
    {
        System.out.println("La bateria se esta cargando...");
    }

    @Override
    public double calcularAutonomia()
    {
        if (consumoCada100Km <= 0)
        {
            return 0;
        }

        return (capacidadBateria * 100) / consumoCada100Km;
    }

    // METODO TO STRING

    @Override
    public String toString()
    {
        return super.toString() + ", capacidadBateria=" + capacidadBateria + " kWh";
    }
}
