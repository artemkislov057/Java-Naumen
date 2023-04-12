package urfu.bookingStand.database.repositories;

import org.springframework.data.repository.CrudRepository;
import urfu.bookingStand.database.entities.RefreshTokenStore;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenStore, Long> {
}
