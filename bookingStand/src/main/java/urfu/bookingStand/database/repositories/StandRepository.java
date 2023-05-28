package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.Stand;

import java.util.List;
import java.util.UUID;

public interface StandRepository extends CrudRepository<Stand, UUID> {
    List<Stand> getByTeamId(UUID teamId);
}
