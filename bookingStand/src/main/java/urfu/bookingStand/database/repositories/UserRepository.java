package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsByShortname(String shortname);

    User findByShortname(String shortname);
}
