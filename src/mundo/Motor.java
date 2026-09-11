package mundo;

public abstract class Motor {

    // ATRIBUTO

    private int potencia;

    // CONSTRUCTOR

    public Motor(int potencia) 
    {

        if (potencia <= 0) {
            throw new IllegalArgumentException(
                    "La potencia debe ser mayor que 0."
            );
        }

        this.potencia = potencia;
    }

    // MÉTODO ABSTRACTO

    public abstract String obtenerTipoMotor();

    // GETTER

    public int getPotencia() 
    {
        return potencia;
    }

    // SETTER

    public void setPotencia(int potencia) 
    {

        if (potencia <= 0) {
            throw new IllegalArgumentException(
                    "La potencia debe ser mayor que 0."
            );
        }

        this.potencia = potencia;
    }

    @Override
    public String toString() 
    {

        return "Motor [potencia=" + potencia + " HP]";
    }
}