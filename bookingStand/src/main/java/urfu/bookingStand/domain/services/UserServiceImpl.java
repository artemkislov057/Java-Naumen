package urfu.bookingStand.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.User;
import urfu.bookingStand.database.enums.Role;
import urfu.bookingStand.database.repositories.UserRepository;
import urfu.bookingStand.domain.abstractions.UserService;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.requests.RegisterUserRequest;

import java.text.MessageFormat;
import java.util.Collections;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void RegisterUser(RegisterUserRequest request) throws ObjectRecreationException {
        if (userRepository.existsByShortname(request.getShortName())) {
            throw new ObjectRecreationException(MessageFormat.format("Пользователь {0} уже зарегистрирован!", request.getShortName()));
        }
        var user = new User();
        user.setName(request.getName());
        user.setShortname(request.getShortName());
        user.setPassword(request.getPassword());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
    }
}
