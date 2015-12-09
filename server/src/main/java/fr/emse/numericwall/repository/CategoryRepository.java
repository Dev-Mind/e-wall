package fr.emse.numericwall.repository;

import fr.emse.numericwall.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link Category}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByCode(String code);
}
