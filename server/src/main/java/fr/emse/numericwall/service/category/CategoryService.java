package fr.emse.numericwall.service.category;

import java.nio.file.Path;
import java.util.Objects;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import fr.emse.numericwall.exception.DuplicateElementException;
import fr.emse.numericwall.model.Category;
import fr.emse.numericwall.model.QrCode;
import fr.emse.numericwall.repository.CategoryRepository;
import fr.emse.numericwall.repository.QrCodeRepository;
import fr.emse.numericwall.service.qrcode.QrCodeFileService;
import fr.emse.numericwall.service.qrcode.QrCodeGenerator;
import fr.emse.numericwall.service.svg.Point;
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

    @Value("numericwall.qrcode.margin")
    private String qrCodeMargin;

    @Autowired
    private QrCodeFileService qrCodeFileService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private QrCodeGenerator qrCodeGenerator;

    /**
     * When a category is created, we need to generate the Qr code linked to the category and all the QR
     * code availables to attached a text or a resource to this category. On update we can only change the name
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
            generateCategoryQRCodes(category, Integer.valueOf(qrCodeMargin));
            categoryRepository.save(category);
        }
        else {
            //On update we can only change the name
            savedCategory = categoryRepository.findOne(category.getId());
            //If user change the code we need to regenerate the codes
            if(savedCategory.getCode().equals(category.getCode())){
                generateCategoryQRCodes(savedCategory, Integer.valueOf(qrCodeMargin));
            }
            savedCategory.setName(category.getName()).setCode(category.getCode());
        }

        return savedCategory;
    }

    /**
     * Generate the main QR code and all the QR codes available for this category
     */
    public void generateCategoryQRCodes(Category category, int margin) {
        Objects.requireNonNull(category);

        String categoryUrl = qrCodePrefixUrl + "/" + category.getCode();

        //Prepares files tree
        Path directory = qrCodeFileService.createDirectoryForQrCode(category.getId());

        //We generate a main qrcode
        qrCodeRepository.save(new QrCode().setBig(true).setCategory(category).setUrl(categoryUrl));

        //A file is generated
        QRCode physicalQrCode = qrCodeGenerator.generateQRCode(categoryUrl);
        //This QRCode is saved
        qrCodeFileService.saveQrCodeAsFile(physicalQrCode, directory.resolve("cat.min.svg"));

        //We need now to generate a bigger QR Code compounded with smaller ones
        QRCode targetQRCode = new QRCode();
        targetQRCode.setMode(physicalQrCode.getMode());
        targetQRCode.setECLevel(physicalQrCode.getECLevel());
        targetQRCode.setVersion(physicalQrCode.getVersion());
        targetQRCode.setMaskPattern(physicalQrCode.getMaskPattern());

        int dimension = physicalQrCode.getMatrix().getWidth();
        int dimensionWithMargin = dimension + margin;

        ByteMatrix byteMatrix = new ByteMatrix((dimensionWithMargin + margin) * dimension, (dimensionWithMargin + margin) * dimension);
        targetQRCode.setMatrix(byteMatrix);

        int cpt = 0;

        //We need to read all the pixels of the main matrix
        for(int x=0 ; x<dimension ; x++){
            for(int y=0 ; y<dimension ; y++){
                //We construct the target matrix : each point is a new QrCode
                if(physicalQrCode.getMatrix().get(x,y)==1){
                    cpt++;
                    String newUrl = categoryUrl.concat("/").concat(String.valueOf(cpt));

                    QRCode smallQrCode = qrCodeGenerator.generateQRCode(newUrl);
                    QrCode qrCode = qrCodeRepository.save(new QrCode().setBig(false).setCategory(category).setUrl(categoryUrl)).setX(x).setY(y);
                    qrCodeFileService.saveQrCodeAsFile(smallQrCode, directory.resolve(qrCode.getId() + ".svg"));

                    qrCodeGenerator.writeQRCodeInByteMatrix(
                            smallQrCode,
                            byteMatrix,
                            Point.create(x * (dimensionWithMargin + margin) + margin, y * (dimensionWithMargin + margin) + margin),
                            Point.create(x * (dimensionWithMargin + margin) + margin + dimensionWithMargin  - 1 , y * (dimensionWithMargin + margin) + margin + dimensionWithMargin  - 1 )
                    );
                }
            }
        }

        //The big QRCode is saved
        qrCodeFileService.saveQrCodeAsFile(targetQRCode, directory.resolve("cat.max.svg"));
    }

    /**
     * Deletes a category and all te QRCode linked. The files are keep to simplify a restoration on error
     */
    public void deleteCategory(Long id){
        Objects.requireNonNull(id);

        Category category = categoryRepository.findOne(id);

        if(category!=null){
            category.getQrcodes().stream().forEach(qrCode -> qrCodeRepository.delete(qrCode));
        }
    }
}
