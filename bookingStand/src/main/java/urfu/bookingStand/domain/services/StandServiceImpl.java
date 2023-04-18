package urfu.bookingStand.domain.services;

import org.springframework.stereotype.Component;
import urfu.bookingStand.domain.abstractions.StandService;
import urfu.bookingStand.domain.requests.AddStandRequest;

import java.util.UUID;

@Component
public class StandServiceImpl implements StandService {
    @Override
    public void AddStand(AddStandRequest request, UUID teamId, UUID userId) {
    }
}
