package urfu.bookingStand.domain.abstractions;

import org.springframework.security.core.userdetails.UserDetailsService;
import urfu.bookingStand.domain.exceptions.DomainExceptionBase;
import urfu.bookingStand.domain.requests.RegisterUserRequest;
import urfu.bookingStand.domain.responses.UserResponse;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void registerUser(RegisterUserRequest request) throws DomainExceptionBase;

    Optional<UserResponse> findByLogin(String login);
}
