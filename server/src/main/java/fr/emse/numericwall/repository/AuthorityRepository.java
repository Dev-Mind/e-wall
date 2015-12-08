package fr.emse.numericwall.repository;

import fr.emse.numericwall.model.Authority;
import fr.emse.numericwall.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link Authority}
 */
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByName(Role name);
}
