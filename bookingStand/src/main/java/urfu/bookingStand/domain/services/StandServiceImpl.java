package urfu.bookingStand.domain.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import urfu.bookingStand.api.dto.stand.StandByTeamIdDto;
import urfu.bookingStand.database.entities.Booking;
import urfu.bookingStand.database.entities.Stand;
import urfu.bookingStand.database.repositories.*;
import urfu.bookingStand.domain.abstractions.StandService;
import urfu.bookingStand.domain.exceptions.*;
import urfu.bookingStand.domain.requests.AddStandRequest;
import urfu.bookingStand.domain.requests.BookStandRequest;
import urfu.bookingStand.domain.responses.StandEmploymentResponse;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public StandServiceImpl(StandRepository standRepository,
                            BookingRepository bookingRepository,
                            UserRepository userRepository,
                            UserTeamAccessRepository userTeamAccessRepository,
                            TeamRepository teamRepository) {
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
    public void BookStand(BookStandRequest request, UUID standId, UUID userId) throws NoAccessException,
            UserNotFoundException,
            NotSuchTimeException, StandNotFoundException {
        var stand = standRepository.findById(standId);
        var user = userRepository.findById(userId);

        var startTime = request.getStartTime();
        var endTime = request.getEndTime();

        if (user.isEmpty())
            throw new UserNotFoundException(MessageFormat.format("User with id {0} doesn't exist.", userId));

        if (stand.isEmpty())
            throw new StandNotFoundException(MessageFormat.format("Stand with ID: {0} not founded.",
                    standId));

        var teamID = stand.get().getTeam().getId();
        if (!userTeamAccessRepository.existsByUserIdAndTeamId(userId, teamID))
            throw new NoAccessException(MessageFormat.format("User {0} has no access to booking stand{1}," +
                    " because he isn't included on the team having access.", user.get().getName(), standId));

        if (startTime.isAfter(endTime))
            throw new NotSuchTimeException(MessageFormat.format("Дата начала брони: {0} идет после даты окончания {1}.",
                    startTime, endTime));

        if (bookingRepository.existsByStartTimeBetween(startTime, endTime) ||
                bookingRepository.existsByEndTimeBetween(startTime, endTime)){
            throw new NotSuchTimeException(MessageFormat.format("Пересечение дат: в промежутке между {0} и {1} есть бронь.",
                    startTime, endTime));
        }

        var booking = new Booking();

        booking.setUserId(userId);
        booking.setStandId(standId);
        booking.setUser(user.get());
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);

        bookingRepository.save(booking);
    }

    @Override
    public void DeleteBookStand(UUID standId, long bookingId, UUID userId)
            throws StandNotFoundException, NoAccessException, BookingNotFoundException {
        var stand = standRepository.findById(standId);

        var user = userRepository.findById(userId);

        if (stand.isEmpty())
            throw new StandNotFoundException(MessageFormat.format("Stand with ID: {0} not founded.",
                    standId));

        var teamID = stand.get().getTeam().getId();

        if (user.isEmpty() || !userTeamAccessRepository.existsByUserIdAndTeamId(userId, teamID))
            throw new NoAccessException(MessageFormat.format("User {0} has no access to booking stand{1}," +
                    " because he isn't included on the team having access.", user.get().getName(), standId));

        var booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()){
            throw new BookingNotFoundException(MessageFormat.format("Booking with id {0} not founded." +
                    " because he isn't included on the team having access.", bookingId));
        }
        booking.get().setUser(null);
        bookingRepository.delete(booking.get());
    }

    @Override
    public List<StandByTeamIdDto> getStandByTeamId(UUID teamId) throws TeamNotFoundException {
        var team = teamRepository.findById(teamId);
        if (team.isEmpty())
            throw new TeamNotFoundException(MessageFormat.format("Team with id {0} doesn't exist.", teamId));

        var stands = team.get().getStands();
        var standsByTeamIdDto = new ArrayList<StandByTeamIdDto>();

        for (var stand : stands) {
            var standDto = modelMapper.map(stand, StandByTeamIdDto.class);
            standsByTeamIdDto.add(standDto);
        }
        return standsByTeamIdDto;
    }

    @Override
    public List<StandEmploymentResponse> getStandEmploymentByTimePeriod(UUID standId, LocalDateTime from, LocalDateTime to) throws StandNotFoundException {

        var stand = standRepository.findById(standId);
        if (stand.isEmpty())
            throw new StandNotFoundException(MessageFormat.format("Stand with id {0} doesn't exist.", standId));

        var bookingsByStartTime = bookingRepository.findAllByStartTimeBetween(from, to);
        var bookingsByEndTime = bookingRepository.findAllByEndTimeBetween(from, to);

        var bookings = new HashSet<Booking>((Collection) bookingsByStartTime);
        bookings.addAll((Collection) bookingsByEndTime);

        var standsResponses = new ArrayList<StandEmploymentResponse>();
        var standTypeResponse = modelMapper.addMappings(new PropertyMap<Booking, StandEmploymentResponse>() {
            protected void configure() {
                map().getUser().setUserId(source.getUser().getId());
            }});

        for (var booking : bookings) {

            var standResponse = standTypeResponse.map(booking);
            standsResponses.add(standResponse);
        }

        return standsResponses;
    }
}
