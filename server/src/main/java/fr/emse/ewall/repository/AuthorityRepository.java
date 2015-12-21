package fr.emse.ewall.repository;

import fr.emse.ewall.model.Authority;
import fr.emse.ewall.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link Authority}
 */
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByName(Role name);
}
