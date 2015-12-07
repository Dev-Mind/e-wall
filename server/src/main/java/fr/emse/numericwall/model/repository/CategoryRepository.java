package fr.emse.numericwall.model.repository;

import fr.emse.numericwall.model.entity.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link fr.emse.numericwall.model.entity.Category}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
