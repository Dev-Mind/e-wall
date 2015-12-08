package fr.emse.numericwall.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

    @Test
    public void generateBarCode() throws Exception{
        String svg = qrCodeGenerator.generateSvg("https://dev-mind.fr", 100, "black");

        String filePath = "/home/ehret_g/tmp/QRTest1.svg";
        File myFile = new File(filePath);

        FileOutputStream outputStream = new FileOutputStream(myFile);
        if (!myFile.exists()) {
            myFile.createNewFile();
        }

        // get the content in bytes
        byte[] contentInBytes = svg.getBytes();

        outputStream.write(contentInBytes);
        outputStream.flush();
        outputStream.close();


    }
}