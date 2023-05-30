package urfu.bookingStand.api.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import urfu.bookingStand.api.dto.user.RegisterUserDto;
import urfu.bookingStand.api.dto.user.UserDto;
import urfu.bookingStand.domain.abstractions.UserService;
import urfu.bookingStand.domain.exceptions.DomainExceptionBase;
import urfu.bookingStand.domain.requests.RegisterUserRequest;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/users")
    @ResponseBody
    public void registerUser(@Valid RegisterUserDto body) throws DomainExceptionBase {
        var request = modelMapper.map(body, RegisterUserRequest.class);
        userService.registerUser(request);
    }

    @GetMapping("api/users/{login}")
    @ResponseBody
    public UserDto getUserByLogin(@PathVariable String login, HttpServletResponse response) {
        var user = userService.findByLogin(login);
        if (user.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        return modelMapper.map(user, UserDto.class);
    }

    @GetMapping("registration")
    public String registerView() {
        return "registration";
    }
}
