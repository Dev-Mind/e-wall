package fr.emse.ewall.service.production;

import java.util.Objects;

import fr.emse.ewall.exception.ElementNotFoundException;
import fr.emse.ewall.model.Production;
import fr.emse.ewall.repository.ProductionRepository;
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

    public Production save(Production production) {
        Objects.requireNonNull(production);

//        Category savedCategory;
//
//        //Code have to be unique
//        Category unique = categoryRepository.findByCode(category.getCode());
//        if (unique != null && !unique.getId().equals(category.getId())) {
//            throw new DuplicateElementException();
//        }
//
//        if (category.getId() == null) {
//            savedCategory = categoryRepository.save(category);
//            generateCategoryQRCodes(savedCategory, qrCodeMargin);
//        }
//        else {
//            //On update we can only change the name
//            savedCategory = categoryRepository.findOne(category.getId());
//            //If user change the code we need to regenerate the codes
//            if (!savedCategory.getCode().equals(category.getCode())) {
//                //Old QrCode are deleted
//                qrCodeRepository.delete(category.getQrcodes());
//                savedCategory.getQrcodes().clear();
//                //And new ones generated
//                generateCategoryQRCodes(savedCategory, qrCodeMargin);
//            }
//            savedCategory.setName(category.getName()).setCode(category.getCode());
//        }

        // return savedCategory;
        return null;
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
        production.getQrcode().setProduction(null);
        productionRepository.delete(production);
    }
}
