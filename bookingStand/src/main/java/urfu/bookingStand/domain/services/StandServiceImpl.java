package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.database.entities.Booking;
import urfu.bookingStand.database.entities.Stand;
import urfu.bookingStand.database.repositories.*;
import urfu.bookingStand.domain.abstractions.StandService;
import urfu.bookingStand.domain.exceptions.NoAccessException;
import urfu.bookingStand.domain.exceptions.NoUserException;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;

import java.text.MessageFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class StandServiceImpl implements StandService {
    private final StandRepository standRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final UserTeamAccessRepository userTeamAccessRepository;
    private final TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public StandServiceImpl(StandRepository standRepository, BookingRepository bookingRepository, UserRepository userRepository, UserTeamAccessRepository userTeamAccessRepository, TeamRepository teamRepository) {
        this.standRepository = standRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.userTeamAccessRepository = userTeamAccessRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void AddStand(AddStandRequest request, UUID teamId, UUID userId) throws NoAccessException {
        var team = teamRepository.findById(teamId);
        if (!userTeamAccessRepository.existsByUserIdAndTeamId(userId, teamId) || team.isEmpty()) {
            throw new NoAccessException(MessageFormat.format("User with id {0} has no access to team with id {1}", userId, teamId));
        }

        var stand = modelMapper.map(request, Stand.class);
        stand.setTeam(team.get());
        standRepository.save(stand);
    }

    @Override
    public void BookStand(BookStandRequest request, UUID standId, UUID userId) throws NoAccessException, NoUserException {
        var stand = standRepository.findById(standId);
        var team = userTeamAccessRepository.findByUserId(userId);
        var user = userRepository.findById(userId);

        if (user.isEmpty())
            throw new NoUserException(MessageFormat.format("User with id {0} doesn't exist.", userId));


        if (team.isEmpty() || stand.isEmpty()) {
            throw new NoAccessException(MessageFormat.format("User with id {0} has no access to booking stand{1}," +
                    " because he isn't included on the team having access.", userId, standId));
        }

        var booking = modelMapper.map(request, Booking.class);

        booking.setUserId(userId);
        booking.setStandId(standId);
        booking.setUser(user.get());
        booking.setStartTime(new Date());
        booking.setEndTime(new Date());

        bookingRepository.save(booking);
    }
}
