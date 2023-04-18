package urfu.bookingStand.domain.abstractions;

import urfu.bookingStand.domain.requests.AddStandRequest;

import java.util.UUID;

public interface StandService {
    void AddStand(AddStandRequest request, UUID teamId, UUID userId);
}
