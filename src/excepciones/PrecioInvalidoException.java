package excepciones;

public class PrecioInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public PrecioInvalidoException(String mensaje) {
        super(mensaje);
    }
}