package urfu.bookingStand.domain.exceptions;

public class BookingNotFoundException extends DomainExceptionBase {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
