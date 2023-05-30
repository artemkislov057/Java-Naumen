package urfu.bookingStand.domain.exceptions;

public class NotSuchTimeException extends DomainExceptionBase {
    public NotSuchTimeException(String message) {
        super(message);
    }
}
