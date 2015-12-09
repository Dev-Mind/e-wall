package fr.emse.numericwall.service.qrcode;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import fr.emse.numericwall.service.svg.Point;
import fr.emse.numericwall.service.svg.SvgConverter;
import fr.emse.numericwall.service.svg.SvgPath;
import org.assertj.core.api.StrictAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test de {@link QrCodeGenerator}
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 08/12/15.
 */
public class QrCodeGeneratorTest {

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    private QrCodeGenerator qrCodeGenerator;

    private SvgConverter svgConverter;

    @Before
    public void init(){
        svgConverter = new SvgConverter();
        qrCodeGenerator = new QrCodeGenerator();
        qrCodeGenerator.setMatrixParser(svgConverter);
    }

    /**
     * Test {@link QrCodeGenerator#generateQRCode(String)}
     */
    @Test(expected = NullPointerException.class)
    public void should_not_generate_QRCode_and_throw_npe_when_url_is_null(){
        qrCodeGenerator.generateQRCode(null);
    }

    /**
     * Test {@link QrCodeGenerator#generateQRCode(String)}
     */
    @Test
    public void should_generate_QRCode_from_URL(){
        QRCode qrCode =qrCodeGenerator.generateQRCode("https://dev-mind.fr");

        //For this tiny URL the QRCode has a version 3 (29*29 pixels)
        assertThat(qrCode.getVersion().getVersionNumber()).isEqualTo(3);
        assertThat(qrCode.getMatrix().getArray()).isNotEmpty();
    }

    /**
     * Test {@link QrCodeGenerator#generateQRCode(String)}}
     */
    @Test
    public void should_save_a_qr_code_in_svg() throws Exception{
        QRCode qrCode = qrCodeGenerator.generateQRCode("https://dev-mind.fr/" + UUID.randomUUID().toString());
        String svg = svgConverter.generateSvg(qrCode, "black");

        File createdFile= folder.newFile("QRTest.svg");

        try(FileOutputStream outputStream = new FileOutputStream(createdFile)){
            outputStream.write(svg.getBytes());
        }
    }

    /**
     * Test {@link QrCodeGenerator#writeQRCodeInByteMatrix(QRCode, ByteMatrix, Point, Point)}
     * We have a bytematrix
     * <pre>
     *     0 0 0 0
     *     0 0 0 0
     *     0 0 0 0
     *     0 0 0 0
     * </pre>
     * and we want to write the matrix below inside
     * <pre>
     *     1 1
     *     1 0
     * </pre>
     * and obtain
     * <pre>
     *     0 0 0 0
     *     0 0 0 0
     *     0 0 1 1
     *     0 0 1 0
     * </pre>
     */
    @Test
    public void should_write_qr_code_in_other() throws Exception{
        //The first matrix
        ByteMatrix firstMatrix = new ByteMatrix(4,4);

        //the second one
        ByteMatrix secondMatrix = new ByteMatrix(2,2);

        QRCode qrCode = new QRCode();
        qrCode.setMatrix(secondMatrix);
        secondMatrix.set(0, 0, true);
        secondMatrix.set(0, 1, true);
        secondMatrix.set(1, 0, true);

        qrCodeGenerator.writeQRCodeInByteMatrix(qrCode, firstMatrix, Point.create(2, 2), Point.create(3, 3));

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if ((x == 2 && y == 2) || (x == 2 && y == 3) || (x == 3 && y == 2)) {
                    assertThat(firstMatrix.get(x, y)==1).isTrue();
                }
                else {
                    assertThat(firstMatrix.get(x, y)==0).isTrue();
                }
            }
        }
    }

}