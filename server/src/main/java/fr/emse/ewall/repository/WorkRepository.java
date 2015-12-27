package fr.emse.ewall.repository;

import fr.emse.ewall.model.Production;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link Production}
 */
public interface WorkRepository extends CrudRepository<Production, Long> {

}
