package fr.emse.ewall.repository;

import fr.emse.ewall.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link Category}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByCode(String code);
}
