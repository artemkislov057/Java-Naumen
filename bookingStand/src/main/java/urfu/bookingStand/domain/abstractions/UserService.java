package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.requests.RegisterUserRequest;

public interface UserService {
    void RegisterUser(RegisterUserRequest request) throws ObjectRecreationException;
}
