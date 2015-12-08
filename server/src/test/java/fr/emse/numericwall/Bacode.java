package fr.emse.numericwall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Test;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
public class Bacode {

    @Test
    public void generateBarCode() throws Exception{
        String url = "https://dev-mind.fr";
        String filePath = "/home/ehret_g/tmp/QRTest1.png";
        int size = 100;
        String fileType = "png";
        File myFile = new File(filePath);

        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, size, size, hintMap);

        MatrixToImageWriter.writeToPath(byteMatrix, "png", myFile.toPath());


//        int matrixWidth = byteMatrix.getWidth();
//        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
//        image.createGraphics();
//
//        Graphics2D graphics = (Graphics2D) image.getGraphics();
//        graphics.setColor(Color.WHITE);
//        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
//        graphics.setColor(Color.BLACK);
//
//        for (int i = 0; i < matrixWidth; i++) {
//            for (int j = 0; j < matrixWidth; j++) {
//                if (byteMatrix.get(i, j)) {
//                    graphics.fillRect(i, j, 1, 1);
//                }
//            }
//        }
//        ImageIO.write(image, fileType, myFile);
    }
}
