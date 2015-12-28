package fr.emse.ewall.repository;

import java.util.List;

import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.QrCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * {@link Production}
 */
public interface ProductionRepository extends CrudRepository<Production, Long> {

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q where u.id = :userId")
    List<Production> findByUserId(@Param("userId") Long userId);
}
