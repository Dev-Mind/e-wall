package fr.emse.numericwall.repository;

import fr.emse.numericwall.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link User}
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
