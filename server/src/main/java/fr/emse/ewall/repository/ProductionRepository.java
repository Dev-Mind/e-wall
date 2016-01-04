package fr.emse.ewall.repository;

import java.util.List;

import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.ProductionState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q where p.state = :state")
    Page<Production> findAll(Pageable pageable, @Param("state") ProductionState state);

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q LEFT JOIN FETCH q.category where p.state = :state and q.code = :category")
    Page<Production> findAll(Pageable pageable, @Param("state") ProductionState state, @Param("category") String category);

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q LEFT JOIN FETCH q.category where p.state = :state and q.code = :category and (p.content like :content or u.esmeid like :content)")
    Page<Production> findAll(Pageable pageable, @Param("state") ProductionState state, @Param("category") String category, @Param("content") String content);

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q LEFT JOIN FETCH q.category where p.state = :state and (p.content like :content or u.esmeid like :content)")
    Page<Production> findAll(Pageable pageable, @Param("state") ProductionState state, @Param("content") String content, boolean byContent);
}
