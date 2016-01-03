package fr.emse.ewall.repository;

import java.util.List;

import fr.emse.ewall.model.Production;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * {@link Production}
 */
public interface ProductionRepository extends CrudRepository<Production, Long> {

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q where u.id = :userId")
    List<Production> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q where p.state = 'VALIDATED'")
    List<Production> findAllValidated();

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q where p.id = :id")
    Production findByIdFetchMode(@Param("id") Long id);
}
