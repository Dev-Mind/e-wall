package fr.emse.numericwall.service.qrcode;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import fr.emse.numericwall.exception.QrCodeFileException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test of {@link QrCodeFileService}
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 09/12/15.
 */
public class QrCodeFileServiceTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private QrCodeFileService qrCodeFileService;
    private Path directory;

    @Before
    public void init() throws Exception {
        directory = folder.getRoot().toPath();

        qrCodeFileService = new QrCodeFileService();
        qrCodeFileService.setQrCodePath(directory.toString());
    }

    /**
     * Test {@link QrCodeFileService#moveFile(Path, Path)}
     */
    @Test
    public void should_do_noting_when_move_file_null() throws Exception {
        qrCodeFileService.moveFile(null, directory);
    }

    /**
     * Test {@link QrCodeFileService#moveFile(Path, Path)}
     */
    @Test
    public void should_do_noting_when_move_file_in_directory() throws Exception {
        qrCodeFileService.moveFile(directory.resolve("test.svg"), null);
    }

    /**
     * Test {@link QrCodeFileService#moveFile(Path, Path)}
     */
    @Test(expected = QrCodeFileException.class)
    public void should_throw_exception_when_move_invalid_file() throws Exception {
        qrCodeFileService.moveFile(directory.resolve("test.svg"), directory);
    }

    /**
     * Test {@link QrCodeFileService#moveFile(Path, Path)}
     */
    @Test
    public void should_move_file() throws Exception {
        Path fileToMove = folder.newFile("test.svg").toPath();
        Path backupDirectory = folder.newFolder("backup").toPath();
        assertThat(fileToMove).exists();

        qrCodeFileService.moveFile(directory.resolve("test.svg"), backupDirectory);

        assertThat(fileToMove).doesNotExist();
        assertThat(backupDirectory.resolve("test.svg")).exists();
    }

    /**
     * Test {@link QrCodeFileService#moveOldFilesInBackupDirectory(Path)}
     */
    @Test
    public void should_do_noting_when_move_file_in_backup_directory_when_directory_nul() throws Exception {
        qrCodeFileService.moveOldFilesInBackupDirectory(null);
    }

    /**
     * Test {@link QrCodeFileService#moveOldFilesInBackupDirectory(Path)}
     */
    @Test
    public void should_move_file_in_backup_directory() throws Exception {
        Path fileToMove = folder.newFile("test.svg").toPath();
        assertThat(fileToMove).exists();

        qrCodeFileService.moveOldFilesInBackupDirectory(directory);

        assertThat(fileToMove).doesNotExist();
    }

    /**
     * Test {@link QrCodeFileService#createDirectoryForQrCode(Long)}
     */
    @Test(expected = NullPointerException.class)
    public void should_not_create_directory_when_id_null() throws Exception {
        qrCodeFileService.createDirectoryForQrCode(null);
    }

    /**
     * Test {@link QrCodeFileService#createDirectoryForQrCode(Long)}
     */
    @Test
    public void should_create_directory() throws Exception {
        qrCodeFileService.createDirectoryForQrCode(123L);

        assertThat(directory.resolve("123")).exists().isDirectory();
    }
}