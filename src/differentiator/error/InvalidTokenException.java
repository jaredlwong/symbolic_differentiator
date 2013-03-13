package differentiator.error;

public class InvalidTokenException extends IllegalArgumentException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private InvalidTokenException() {}

    private InvalidTokenException(String message)
    {
       super(message);
    }
}
