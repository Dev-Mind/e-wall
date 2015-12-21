package fr.emse.ewall.service.qrcode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import com.google.common.annotations.VisibleForTesting;
import com.google.zxing.qrcode.encoder.QRCode;
import fr.emse.ewall.exception.QrCodeFileException;
import fr.emse.ewall.service.svg.SvgConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Class utils to manage the QR code files
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 09/12/15.
 */
@Service
public class QrCodeFileService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeFileService.class);

    @Value("${ewall.qrcode.path}")
    private String qrCodePath;

    @Autowired
    private SvgConverter svgConverter;



    /**
     * Return a QRCode file.
     */
    public byte[] getQrCode(String resource){

        Path path = Paths.get(qrCodePath, resource);

        if(!Files.exists(path)){
            logger.error("Not possible to find the QR code " + resource);
            throw new QrCodeFileException();
        }

        try {
            return Files.readAllBytes(path);
        }
        catch (IOException e) {
            logger.error("Error on QR codes read", e);
            throw new QrCodeFileException();
        }
    }

    /**
     * Save a QrCode on file system
     */
    public String saveQrCodeAsFile(QRCode qrCode, Path file){
        try(FileOutputStream outputStream1 = new FileOutputStream(file.toFile())) {
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
            //The content is save in database for the small QR codes. We have to regenerate the SVG in the HTML
            // to be able to change the style via CSS. Otherwise it's not possible
            String content = svgConverter.generatePathSvg(qrCode);
            outputStream1.write(svgConverter.generateSvg(qrCode, "black", content).getBytes());
            return content;
        }
        catch (IOException e){
            logger.error("Error on QR codes creation", e);
            throw new QrCodeFileException();
        }
    }

    /**
     * Creates a directory if it does not exist and archives old qr codes in a subdirectory
     */
    public Path createDirectoryForQrCode(Long idCategory) {
        Objects.requireNonNull(idCategory);

        //We need to know if the directory exist (if yes we need to move all actual qrCodes)
        Path directory = Paths.get(qrCodePath, idCategory.toString());
        try {
            if (Files.exists(directory)) {
                moveOldFilesInBackupDirectory(directory);
            }
            else {
                Files.createDirectory(directory);
            }
        }
        catch (IOException e) {
            logger.error("Error on QR codes directory creation", e);
            throw new QrCodeFileException();
        }
        return directory;
    }


    /**
     * Move all the directory files in a subdirectory for backup
     */
    @VisibleForTesting
    protected void moveOldFilesInBackupDirectory(Path directory) throws IOException {
        if (directory != null && Files.exists(directory)) {
            //We move the actual QrCodes in a subdirectory
            Path targetDirectory = directory.resolve(UUID.randomUUID().toString());
            Files.createDirectory(targetDirectory);

            logger.debug("Move files in " + targetDirectory.getFileName());
            Files.list(directory).forEach(path -> moveFile(path, targetDirectory));
        }
    }

    /**
     * Encapsulates the move in a function because of the checked exception. Me move only files
     */
    @VisibleForTesting
    protected void moveFile(Path fileToMove, Path targetDirectory) {
        if (fileToMove != null && targetDirectory != null && !Files.isDirectory(fileToMove)) {
            try {
                Files.move(fileToMove, targetDirectory.resolve(fileToMove.getFileName()), StandardCopyOption.ATOMIC_MOVE);
            }
            catch (IOException e) {
                logger.error("Error on file moving", e);
                throw new QrCodeFileException();
            }
        }
    }

    @VisibleForTesting
    protected void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }
}
