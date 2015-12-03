package fr.emse.numericwall.model.repository;

import fr.emse.numericwall.model.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link User}
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
