package urfu.bookingStand.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import urfu.bookingStand.api.dto.stand.AddStandDto;
import urfu.bookingStand.api.dto.stand.BookStandDto;
import urfu.bookingStand.api.dto.stand.StandByTeamIdDto;
import urfu.bookingStand.domain.abstractions.StandService;
import urfu.bookingStand.domain.exceptions.*;
import urfu.bookingStand.domain.models.BookingUserDetails;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;

import java.util.ArrayList;
import java.util.List;
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

    @PostMapping("api/stands/{standId}/book")
    @ResponseBody
    public void BookStand(@RequestBody BookStandDto body, @PathVariable UUID standId, Authentication authentication)
            throws NoAccessException,
            UserNotFoundException,
            NotSuchTimeException,
            StandNotFoundException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        var request = modelMapper.map(body, BookStandRequest.class);
        standService.BookStand(request, standId, user.getId());
    }

    @DeleteMapping("api/stands/{standId}/bookings/{bookingId}")
    @ResponseBody
    public void DeleteBookStand(@PathVariable UUID standId, @PathVariable long bookingId, Authentication authentication)
            throws NoAccessException,
            UserNotFoundException,
            StandNotFoundException, BookingNotFoundException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        standService.DeleteBookStand(standId, bookingId, user.getId());
    }

    @GetMapping("api/teams/{teamId}/stands")
    @ResponseBody
    public List<StandByTeamIdDto> getStandByTeamId(@PathVariable UUID teamId) throws TeamNotFoundException {
        var standsByTeamIdResponse = standService.getStandByTeamId(teamId);

        var standsDto = new ArrayList<StandByTeamIdDto>();
        for (var standResponse : standsByTeamIdResponse) {
            var standByTeamIdDto = modelMapper.map(standResponse, StandByTeamIdDto.class);
            standsDto.add(standByTeamIdDto);
        }
        return standsDto;
    }
}
