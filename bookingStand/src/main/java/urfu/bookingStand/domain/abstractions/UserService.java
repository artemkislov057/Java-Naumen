package urfu.bookingStand.domain.abstractions;

import org.springframework.security.core.userdetails.UserDetailsService;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.requests.RegisterUserRequest;

public interface UserService extends UserDetailsService {
    void registerUser(RegisterUserRequest request) throws ObjectRecreationException;
}
