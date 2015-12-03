package fr.emse.numericwall.model.repository;

import fr.emse.numericwall.model.entity.Authority;
import fr.emse.numericwall.model.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link fr.emse.numericwall.model.entity.Authority}
 */
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByName(Role name);
}
