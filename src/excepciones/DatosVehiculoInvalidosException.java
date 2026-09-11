package excepciones;

public class DatosVehiculoInvalidosException extends Exception {

	private static final long serialVersionUID = 1L;

	public DatosVehiculoInvalidosException(String mensaje) {
        super(mensaje);
    }
}