package fr.emse.ewall.repository;

import java.util.List;

import fr.emse.ewall.model.QrCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * {@link QrCode}
 */
public interface QrCodeRepository extends CrudRepository<QrCode, Long> {

    @Query(value = "SELECT q FROM QrCode q LEFT JOIN FETCH q.category c where c.id = :categoryId")
    List<QrCode> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = "SELECT q FROM QrCode q LEFT JOIN FETCH q.production p LEFT JOIN FETCH p.user u where p.id = :id")
    QrCode findById(@Param("id") Long id);
}
