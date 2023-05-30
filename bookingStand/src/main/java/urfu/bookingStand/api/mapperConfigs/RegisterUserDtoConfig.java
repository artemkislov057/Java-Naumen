package urfu.bookingStand.api.mapperConfigs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import urfu.bookingStand.api.dto.user.RegisterUserDto;
import urfu.bookingStand.domain.requests.RegisterUserRequest;

@Configuration
public class RegisterUserDtoConfig {
    @Autowired
    public void configureMap(ModelMapper mapper) {
        var map = mapper.createTypeMap(RegisterUserDto.class, RegisterUserRequest.class);
        map.addMapping(RegisterUserDto::getLogin, RegisterUserRequest::setShortName);
    }
}
