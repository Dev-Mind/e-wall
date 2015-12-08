package fr.emse.numericwall.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 08/12/15.
 */
public class QrCodeGeneratorTest {

    private QrCodeGenerator qrCodeGenerator;

    @Before
    public void init(){
        qrCodeGenerator = new QrCodeGenerator();
        qrCodeGenerator.setMatrixParser(new MatrixParser());
    }

    @Test(expected = NullPointerException.class)
    public void should_not_generate_QRCode_and_throw_npe_when_url_is_null(){
        qrCodeGenerator.generateQRCode(null);
    }

    @Test
    public void should_generate_QRCode_from_URL(){
        QRCode qrCode =qrCodeGenerator.generateQRCode("https://dev-mind.fr");

        //For this tiny URL the QRCode has a version 3 (29*29 pixels)
        assertThat(qrCode.getVersion().getVersionNumber()).isEqualTo(3);
        assertThat(qrCode.getMatrix().getArray()).isNotEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void should_not_generate_svg_when_qrcode_is_nul(){
        qrCodeGenerator.generateSvg(null, "black");
    }

    @Test
    public void should_generate_svg(){
        QRCode qrCode = new QRCode();
        qrCode.setMatrix(new ByteMatrix(4, 2));
        assertThat(qrCodeGenerator.generateSvg(qrCode, "black")).isNotEmpty();
    }
}