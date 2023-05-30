package urfu.bookingStand.api.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import urfu.bookingStand.api.dto.stand.AddStandDto;
import urfu.bookingStand.api.dto.stand.BookStandDto;
import urfu.bookingStand.api.dto.stand.StandByTeamIdDto;
import urfu.bookingStand.api.dto.stand.StandEmploymentDto;
import urfu.bookingStand.domain.abstractions.StandService;
import urfu.bookingStand.domain.exceptions.*;
import urfu.bookingStand.domain.models.BookingUserDetails;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;

import java.time.LocalDateTime;
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
    public void addStand(@RequestBody AddStandDto body, @PathVariable UUID teamId, Authentication authentication) throws NoAccessException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        var request = modelMapper.map(body, AddStandRequest.class);
        standService.addStand(request, teamId, user.getId());
    }

    @PostMapping("api/stands/{standId}/book")
    @ResponseBody
    public void bookStand(@RequestBody BookStandDto body, @PathVariable UUID standId, Authentication authentication)
            throws NoAccessException,
            UserNotFoundException,
            NotSuchTimeException,
            StandNotFoundException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        var request = modelMapper.map(body, BookStandRequest.class);
        standService.bookStand(request, standId, user.getId());
    }

    @DeleteMapping("api/stands/{standId}/bookings/{bookingId}")
    @ResponseBody
    public void deleteBookStand(@PathVariable UUID standId, @PathVariable long bookingId, Authentication authentication)
            throws NoAccessException,
            UserNotFoundException,
            StandNotFoundException, BookingNotFoundException {
        var user = (BookingUserDetails) authentication.getPrincipal();
        standService.deleteBookStand(standId, bookingId, user.getId());
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

    @GetMapping("/api/stands/{standId}/bookings")
    @ResponseBody
    public List<StandEmploymentDto> getStandEmploymentByTimePeriod(@PathVariable UUID standId,
                                                                   @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                                   @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) throws StandNotFoundException {
       var standEmploymentResponse = standService.getStandEmploymentByTimePeriod(standId, from, to);

       var standsEmploymentDto = new ArrayList<StandEmploymentDto>();
       for (var standEmployment : standEmploymentResponse) {
            var standEmploymentDto = modelMapper.map(standEmployment, StandEmploymentDto.class);
            standsEmploymentDto.add(standEmploymentDto);
       }
       return standsEmploymentDto;
    }

}
