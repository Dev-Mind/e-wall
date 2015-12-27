package fr.emse.ewall;

import javax.annotation.PostConstruct;

import fr.emse.ewall.model.Category;
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

    @PostConstruct
    public void init() {
        if(categoryRepository.count()==0){
            logger.info("This is the first start of the application. We create the different categories");

            categoryService.save(new Category().setCode("territoire").setName("Territoire(s)").setMessage("F"));
            categoryService.save(new Category().setCode("international").setName("International").setMessage("U"));
            categoryService.save(new Category().setCode("rechindustrie").setName("Transfert recherche - industrie").setMessage("T"));
            categoryService.save(new Category().setCode("orgatravail").setName("Organisation du travail").setMessage("U"));
            categoryService.save(new Category().setCode("responsabilite").setName("Responsabilité sociétale et environnementale de l’ingénieur").setMessage("R"));

            //TODO add characters
//            Beaunier  - Territoire(s)
//
//            Boussingault  - International
//
//            Grüner  - Transfert recherche - industrie
//
//            Fayol  - Organisation du travail
//
//            Neltner - Responsabilité sociétale et environnementale de l’ingénieur
        }
    }

}
