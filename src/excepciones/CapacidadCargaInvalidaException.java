package excepciones;

public class CapacidadCargaInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;

	public CapacidadCargaInvalidaException(String mensaje) {
        super(mensaje);
    }
}