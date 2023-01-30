package excepciones;

@SuppressWarnings("serial")
public class ArchivoInvalidoException extends RuntimeException {
	
	public ArchivoInvalidoException(String msg) {
		super(msg);
	}
}
