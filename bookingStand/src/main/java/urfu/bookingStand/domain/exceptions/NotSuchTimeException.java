package urfu.bookingStand.domain.exceptions;

public class NotSuchTimeException extends Exception {
    public NotSuchTimeException(String message) {
        super(message);
    }
}
