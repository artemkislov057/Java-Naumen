package urfu.bookingStand.domain.exceptions;

public class UserNotFoundException extends DomainExceptionBase {
    public UserNotFoundException(String message) {
        super(message);
    }
}
