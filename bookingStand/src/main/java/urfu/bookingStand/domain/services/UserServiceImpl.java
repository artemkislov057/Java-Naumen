package urfu.bookingStand.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.User;
import urfu.bookingStand.database.enums.Role;
import urfu.bookingStand.database.repositories.UserRepository;
import urfu.bookingStand.domain.abstractions.UserService;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
import urfu.bookingStand.domain.requests.RegisterUserRequest;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void RegisterUser(RegisterUserRequest request) throws ObjectRecreationException {
        if (userRepository.existsByShortname(request.getShortName())) {
            throw new ObjectRecreationException(MessageFormat.format("Пользователь {0} уже зарегистрирован!", request.getShortName()));
        }
        var user = new User();
        user.setName(request.getName());
        user.setShortname(request.getShortName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userFromDatabase = userRepository.findByShortname(username);
        if (userFromDatabase == null) {
            throw new UsernameNotFoundException(MessageFormat.format("Пользователь {0} не найден!", username));
        }

        return new org.springframework.security.core.userdetails.User(
                userFromDatabase.getName(),
                userFromDatabase.getPassword(),
                mapRolesToAuthorities(userFromDatabase.getRoles())
        );
    }

    private List<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.name())).collect(Collectors.toList());
    }
}
