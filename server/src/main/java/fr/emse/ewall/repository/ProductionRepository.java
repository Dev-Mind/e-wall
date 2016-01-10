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

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q where p.state = 'VALIDATED' or p.state = 'CATEGORY'")
    List<Production> findAllValidated();

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q where p.id = :id")
    Production findByIdFetchMode(@Param("id") Long id);

    @Query(value = "SELECT p FROM Production p LEFT JOIN FETCH p.qrcodes q where q.url = :url and  (p.state = 'VALIDATED' or p.state = 'CATEGORY')")
    Production findByUrl(@Param("url") String url);


    /**
     * To use a fetch with Spring data we must specify how Spring has to count the number of enreg
     * http://codingexplained.com/coding/java/spring-framework/fetch-query-not-working-spring-data-jpa-pageable
     */
    @Query(
            value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q LEFT JOIN FETCH q.category c where p.state = :state",
            countQuery = "SELECT count(p) FROM Production p LEFT JOIN p.user u LEFT JOIN p.qrcodes q LEFT JOIN q.category c where p.state = :state and p.state<>'CATEGORY'")
    Page<Production> findAllByState(Pageable pageable, @Param("state") ProductionState state);

    @Query(
            value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q LEFT JOIN FETCH q.category c where p.state = :state and c.code = :category",
            countQuery = "SELECT count(p) FROM Production p LEFT JOIN p.user u LEFT JOIN p.qrcodes q LEFT JOIN q.category c where p.state = :state and c.code = :category  and p.state<>'CATEGORY'")
    Page<Production> findAllByStateAndCategory(Pageable pageable, @Param("state") ProductionState state, @Param("category") String category);

    @Query(
            value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q LEFT JOIN FETCH q.category c where p.state = :state and c.code = :category and (LOWER(p.content) like :content or u.esmeid like :content)",
            countQuery = "SELECT count(p) FROM Production p LEFT JOIN p.user u LEFT JOIN p.qrcodes q LEFT JOIN q.category c where p.state = :state  and p.state<>'CATEGORY'and c.code = :category and (LOWER(p.content) like :content or u.esmeid like :content)")
    Page<Production> findAllByStateAndCategoryAndContent(Pageable pageable, @Param("state") ProductionState state, @Param("category") String category, @Param("content") String content);

    @Query(
            value = "SELECT p FROM Production p LEFT JOIN FETCH p.user u LEFT JOIN FETCH p.qrcodes q LEFT JOIN FETCH q.category c where p.state = :state and (LOWER(p.content) like :content or u.esmeid like :content)",
            countQuery = "SELECT count(p) FROM Production p LEFT JOIN p.user u LEFT JOIN p.qrcodes q LEFT JOIN q.category c where p.state = :state  and p.state<>'CATEGORY' and (LOWER(p.content) like :content or u.esmeid like :content)")
    Page<Production> findAllByStateAndContent(Pageable pageable, @Param("state") ProductionState state, @Param("content") String content);
}
