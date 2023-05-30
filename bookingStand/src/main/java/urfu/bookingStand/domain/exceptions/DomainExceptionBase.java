package urfu.bookingStand.domain.exceptions;

public abstract class DomainExceptionBase extends Exception {
    public DomainExceptionBase(String message) {
        super(message);
    }
}
