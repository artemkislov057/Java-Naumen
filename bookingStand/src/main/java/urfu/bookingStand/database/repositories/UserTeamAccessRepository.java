package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.UserTeamAccess;

public interface UserTeamAccessRepository extends CrudRepository<UserTeamAccess, Long> {
}
