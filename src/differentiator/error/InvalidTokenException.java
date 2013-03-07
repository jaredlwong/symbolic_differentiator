package differentiator.error;

public class InvalidTokenException extends IllegalArgumentException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InvalidTokenException() {}

    public InvalidTokenException(String message)
    {
       super(message);
    }
}
