package fr.emse.ewall.repository;

import java.util.List;

import fr.emse.ewall.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * {@link Category}
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByCode(String code);

    @Query(value = "SELECT c FROM Category c LEFT JOIN FETCH c.qrcodes q LEFT JOIN FETCH q.production p where c.id = :id")
    Category findByIdFetchMode(@Param("id") Long id);

    @Query(value = "SELECT c FROM Category c LEFT JOIN FETCH c.qrcodes q where q.big=true")
    List<Category> findAllithQrCode();
}
