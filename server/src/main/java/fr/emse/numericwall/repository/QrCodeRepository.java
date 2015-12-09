package fr.emse.numericwall.repository;

import fr.emse.numericwall.model.NwQrCode;
import org.springframework.data.repository.CrudRepository;

/**
 * {@link NwQrCode}
 */
public interface QrCodeRepository extends CrudRepository<NwQrCode, Long> {

}
