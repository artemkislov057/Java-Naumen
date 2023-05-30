package urfu.bookingStand.api.mapperConfigs;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import urfu.bookingStand.database.entities.Booking;
import urfu.bookingStand.domain.responses.StandEmploymentResponse;

@Configuration
public class StandEmploymentDtoConfig {
    @Autowired
    public void configureMap(ModelMapper mapper) {
        mapper.addMappings(new PropertyMap<Booking, StandEmploymentResponse>() {
            protected void configure() {
                map().getUser().setUserId(source.getUser().getId());
            }});
    }
}
