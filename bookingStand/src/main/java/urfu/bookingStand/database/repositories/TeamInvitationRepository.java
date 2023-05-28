package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.TeamInvitation;

import java.util.List;
import java.util.UUID;

public interface TeamInvitationRepository extends CrudRepository<TeamInvitation, Long> {
    boolean existsByTeamIdAndUserId(UUID teamId, UUID userId);

    List<TeamInvitation> getByUserId(UUID userId);
}
