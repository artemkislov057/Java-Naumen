package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Team;
import urfu.bookingStand.database.entities.UserTeamAccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTeamAccessRepository extends CrudRepository<UserTeamAccess, Long> {
    boolean existsByUserIdAndTeamId(UUID userId, UUID teamId);

    List<UserTeamAccess> getByUserId(UUID userId);
}
