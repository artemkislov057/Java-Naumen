package urfu.bookingStand.api.mapperConfigs;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import urfu.bookingStand.api.dto.user.RegisterUserDto;
import urfu.bookingStand.database.entities.Booking;
import urfu.bookingStand.domain.requests.RegisterUserRequest;
import urfu.bookingStand.domain.responses.StandEmploymentResponse;

@Configuration
public class RegisterUserDtoConfig {
    @Autowired
    public void ConfigureMap(ModelMapper mapper) {
        var map = mapper.createTypeMap(RegisterUserDto.class, RegisterUserRequest.class);
        map.addMapping(RegisterUserDto::getLogin, RegisterUserRequest::setShortName);

        var standTypeResponse = mapper.addMappings(new PropertyMap<Booking, StandEmploymentResponse>() {
            protected void configure() {
                map().getUser().setUserId(source.getUser().getId());
            }});
    }
}
