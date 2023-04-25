package urfu.bookingStand.api.controllerAdvices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;

@ControllerAdvice
public class ExceptionsHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(ObjectRecreationException.class)
    public String handleConflict(ObjectRecreationException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(NoAccessException.class)
    public String handleNoAccess(NoAccessException exception) {
        return exception.getMessage();
    }
}
