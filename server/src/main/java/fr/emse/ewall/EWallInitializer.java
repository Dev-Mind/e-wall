package fr.emse.ewall;

import javax.annotation.PostConstruct;

import fr.emse.ewall.model.Authority;
import fr.emse.ewall.model.Category;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.repository.AuthorityRepository;
import fr.emse.ewall.repository.CategoryRepository;
import fr.emse.ewall.service.category.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Initialize data if db is empty
 */
@Component
public class EWallInitializer {

    private static final Logger logger = LoggerFactory.getLogger(EWallInitializer.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @PostConstruct
    public void init() {
        if(categoryRepository.count()==0){
            logger.info("This is the first start of the application. We create the different categories");

            categoryService.save(new Category().setCode("territoire").setName("Territoire(s)").setShortCode("F").setMessage("Beaunier"));
            categoryService.save(new Category().setCode("international").setName("International").setShortCode("U").setMessage("Boussingault"));
            categoryService.save(new Category().setCode("rechindustrie").setName("Transfert recherche - industrie").setShortCode("T").setMessage("Grüner"));
            categoryService.save(new Category().setCode("orgatravail").setName("Organisation du travail").setShortCode("U").setMessage("Fayol"));
            categoryService.save(new Category().setCode("responsabilite").setName("Responsabilité sociétale et environnementale de l’ingénieur").setShortCode("R").setMessage("Neltner"));

            authorityRepository.save(new Authority().setId(1L).setName(Role.PUBLIC));
            authorityRepository.save(new Authority().setId(2L).setName(Role.ADMIN));
            authorityRepository.save(new Authority().setId(3L).setName(Role.WRITER));

        }
    }

}
