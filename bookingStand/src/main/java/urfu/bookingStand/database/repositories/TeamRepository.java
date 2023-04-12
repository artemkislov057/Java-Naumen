package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Team;

import java.util.UUID;

public interface TeamRepository extends CrudRepository<Team, UUID> {
}
