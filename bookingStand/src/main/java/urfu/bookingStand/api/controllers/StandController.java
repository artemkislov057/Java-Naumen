package urfu.bookingStand.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import urfu.bookingStand.api.dto.stand.AddStandDto;
import urfu.bookingStand.domain.abstractions.StandService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.models.BookingUserDetails;
import urfu.bookingStand.domain.requests.AddStandRequest;

import java.util.UUID;

@Controller
public class StandController {
    private final StandService standService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public StandController(StandService standService) {
        this.standService = standService;
    }

    @PostMapping("api/teams/{teamId}/stands")
    @ResponseBody
    public void AddStand(@RequestBody AddStandDto body, @PathVariable UUID teamId, Authentication authentication) throws NoAccessException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        var request = modelMapper.map(body, AddStandRequest.class);
        standService.AddStand(request, teamId, user.getId());
    }
}
