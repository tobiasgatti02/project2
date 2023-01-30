package excepciones;

@SuppressWarnings("serial")
public class NivelInvalidoException extends RuntimeException {
	
	public NivelInvalidoException(String msg) {
		super(msg);
	}
}
