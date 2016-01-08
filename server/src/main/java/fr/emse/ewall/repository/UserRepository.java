package fr.emse.ewall.repository;

import fr.emse.ewall.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * {@link User}
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT u FROM User u  where u.esmeid = :esmeid")
    User findByEsmeid(@Param("esmeid")String esmeid);

    @Query(value = "SELECT u FROM User u  where u.token = :token")
    User findByToken(@Param("token") String token);
}
