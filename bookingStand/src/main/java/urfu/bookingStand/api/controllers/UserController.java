package urfu.bookingStand.api.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import urfu.bookingStand.api.dto.user.RegisterUserDto;
import urfu.bookingStand.domain.abstractions.UserService;
import urfu.bookingStand.domain.exceptions.ObjectRecreationException;
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
    public void RegisterUser(@Valid RegisterUserDto body) throws ObjectRecreationException {
        var request = modelMapper.map(body, RegisterUserRequest.class);
        userService.RegisterUser(request);
    }

    @GetMapping("registration")
    public String RegisterView() {
        return "registration";
    }
}
