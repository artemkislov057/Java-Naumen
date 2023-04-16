package urfu.bookingStand.configuration.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;
import urfu.bookingStand.domain.models.BookingUserDetails;

@Configuration
public class UserDetailsConfig {
    @Bean
    @RequestScope
    public BookingUserDetails getUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (BookingUserDetails) authentication.getPrincipal();
    }
}
