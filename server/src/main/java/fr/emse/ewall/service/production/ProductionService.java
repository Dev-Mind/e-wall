package fr.emse.ewall.service.production;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import fr.emse.ewall.exception.ElementNotFoundException;
import fr.emse.ewall.exception.ForbiddenException;
import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.ProductionState;
import fr.emse.ewall.model.QrCode;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.CategoryRepository;
import fr.emse.ewall.repository.ProductionRepository;
import fr.emse.ewall.repository.QrCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manage {@link fr.emse.ewall.model.Production}
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 09/12/15.
 */
@Service
@Transactional
public class ProductionService {

    private static final Logger logger = LoggerFactory.getLogger(ProductionService.class);

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private QrCodeRepository qrCodeRepository;

    public Production save(Long idCategory, Production production, User user, boolean adminMode) {
        Objects.requireNonNull(idCategory);
        Objects.requireNonNull(production);

        Production savedProduction;

        if(production.getId()==null){
            savedProduction = productionRepository.save(production.setUser(user));
            linkToQrCode(idCategory, savedProduction);
        }
        else{
            savedProduction = productionRepository.findOne(production.getId());
            //Only pending productions can be saved
            if(!adminMode && !ProductionState.PENDING.equals(production.getState())) {
                throw new ForbiddenException();
            }
            savedProduction.setContent(production.getContent());
            if(!idCategory.equals(savedProduction.getQrcode().getCategory().getId())){
                deleteLinkQRCode(savedProduction);
                linkToQrCode(idCategory, savedProduction);
            }
        }
        return savedProduction;
    }

    /**
     * Link to QRCode. The link is made with the category
     */
    public void linkToQrCode(Long idCategory, Production production) {
        //We need to find
        List<QrCode> qrcodes = qrCodeRepository.findByCategoryId(idCategory);
        Collections.shuffle(qrcodes);

        Iterator<QrCode> iterator = qrcodes.iterator();
        while (iterator.hasNext()){
            QrCode qrCode = iterator.next();
            if(qrCode.getProduction()==null){
                qrCode.setProduction(production);
                break;
            }
        }
    }


    /**
     * Deletes a production and the link in the qrcode
     */
    public void deleteProduction(Long id) {
        Objects.requireNonNull(id);

        Production production = productionRepository.findOne(id);
        if (production == null) {
            throw new ElementNotFoundException();
        }
        deleteLinkQRCode(production);
        productionRepository.delete(production);
    }

    protected void deleteLinkQRCode(Production production) {
        production.getQrcode().setProduction(null);
        qrCodeRepository.save(production.getQrcode());
        production.setQrcode(null);
    }
}
