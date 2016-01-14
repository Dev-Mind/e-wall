package fr.emse.ewall.service.production;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Strings;
import fr.emse.ewall.api.dto.ProductionDto;
import fr.emse.ewall.exception.ElementNotFoundException;
import fr.emse.ewall.exception.ForbiddenException;
import fr.emse.ewall.model.Production;
import fr.emse.ewall.model.ProductionState;
import fr.emse.ewall.model.QrCode;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.ProductionRepository;
import fr.emse.ewall.repository.QrCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Production saveMyProduction(Long idCategory, Production production, User user) {
        Objects.requireNonNull(idCategory);
        Objects.requireNonNull(production);

        Production savedProduction;
        boolean adminMode = user.getRoles().contains(Role.ADMIN.name());

        if(production.getId()==null){
            savedProduction = productionRepository.save(production.setUser(user).setUserMaj(user.getEsmeid()));
            linkToQrCode(idCategory, savedProduction);
        }
        else{
            savedProduction = productionRepository.findOne(production.getId());
            //Only pending productions can be saved
            if(!adminMode && !ProductionState.PENDING.equals(production.getState())) {
                throw new ForbiddenException();
            }
            savedProduction
                    .setUserMaj(user.getEsmeid())
                    .setContent(production.getContent())
                    .setAuthor(production.getAuthor())
                    .setState(production.getState());

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


    private String prepareForLike(String value){
        return "%" + value + "%";
    }
    public Page<Production> filterProductions(Integer page, Integer listMaxSize, ProductionDto filter){
        Page<Production> productions;
        Pageable pageable = new PageRequest(page, listMaxSize);
        if(Objects.nonNull(filter.getCategory()) && !Strings.isNullOrEmpty(filter.getContent())){
            productions = productionRepository.findAllByStateAndCategoryAndContent(pageable, filter.getState(), filter.getCategory(), prepareForLike(filter.getContent()));
        }
        else if(Objects.nonNull(filter.getCategory())){
            productions = productionRepository.findAllByStateAndCategory(pageable, filter.getState(), filter.getCategory());
        }
        else if(!Strings.isNullOrEmpty(filter.getContent())){
            productions = productionRepository.findAllByStateAndContent(pageable, filter.getState(), prepareForLike(filter.getContent()));
        }
        else{
            productions = productionRepository.findAllByState(pageable, filter.getState());
        }
        return productions;
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
        qrCodeRepository.save(production.getQrcode().setProduction(null));
        production.setQrcode(null);
    }
}
