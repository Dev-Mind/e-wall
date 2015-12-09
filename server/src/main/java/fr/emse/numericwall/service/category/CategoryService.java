package fr.emse.numericwall.service.category;

import java.util.Objects;

import fr.emse.numericwall.exception.DuplicateElementException;
import fr.emse.numericwall.model.Category;
import fr.emse.numericwall.model.NwQrCode;
import fr.emse.numericwall.repository.CategoryRepository;
import fr.emse.numericwall.service.qrcode.QrCodeFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manage {@link fr.emse.numericwall.model.Category}
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 09/12/15.
 */
@Service
@Transactional
public class CategoryService {

    @Value("numericwall.qrcode.url")
    private String qrCodePrefixUrl;

    @Autowired
    private QrCodeFileService qrCodeFileService;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * When a category is created, we need to generate the Qr code linked to the category and all the QR
     * code availables to attached a text or a resource to this category. On update we can only change the name
     *
     * @param category
     * @return
     */
    public Category save(Category category) {
        Objects.requireNonNull(category);

        Category savedCategory;

        //Code have to be unique
        Category unique = categoryRepository.findByCode(category.getCode());
        if (unique != null && !unique.getId().equals(category.getId())) {
            throw new DuplicateElementException();
        }

        if (category.getId() == null) {
            savedCategory = category;
            generateCategoryQRCodes(category);
            categoryRepository.save(category);
        }
        else {
            //On update we can only change the name
            savedCategory = categoryRepository.findOne(category.getId());
            savedCategory.setName(category.getName()).setCode(category.getCode());
        }

        return savedCategory;
    }

    /**
     * Generate the main QR code and all the QR codes available for this category
     *
     * @param category
     */
    public void generateCategoryQRCodes(Category category) {
        Objects.requireNonNull(category);

        //Prepares files tree
        qrCodeFileService.createDirectoryForQrCode(category.getId());

        //We generate a main qrcode
        //NwQrCode qrCode = new NwQrCode().

    }
}
