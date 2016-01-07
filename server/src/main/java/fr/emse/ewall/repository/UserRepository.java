package fr.emse.ewall.repository;

import fr.emse.ewall.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link User}
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEsmeid(String esmeid);

    User findByToken(String token);
}
