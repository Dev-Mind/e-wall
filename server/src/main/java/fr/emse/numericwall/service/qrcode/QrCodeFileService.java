package fr.emse.numericwall.service.qrcode;

import java.io.File;
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
import fr.emse.numericwall.exception.QrCodeFileException;
import fr.emse.numericwall.service.svg.SvgConverter;
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

    @Value("numericwall.qrcode.path")
    private String qrCodePath;

    @Autowired
    private SvgConverter svgConverter;

    /**
     * Save a QrCode on file system
     */
    public void saveQrCodeAsFile(QRCode qrCode, Path file){
        try(FileOutputStream outputStream1 = new FileOutputStream(file.toFile())) {
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
            outputStream1.write(svgConverter.generateSvg(qrCode, "black").getBytes());
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
