package fr.emse.numericwall.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import com.google.common.annotations.VisibleForTesting;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import fr.emse.numericwall.exception.QrCodeGeneratorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generate a SVG
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
public class QrCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeGenerator.class);

    private MatrixParser matrixParser;

    public String generateSvg(String url, int dimension, String color){
        //see https://en.wikipedia.org/wiki/QR_code#Error_correction
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hintMap.put(EncodeHintType.MARGIN, 0);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix byteMatrix = null;
        try {
            byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, dimension, dimension, hintMap);
        }
        catch (WriterException e) {
            logger.error("Error on QrCode generation for url=" + url, e);
            throw new QrCodeGeneratorException();
        }

        List<Rectangle> rectangles = matrixParser.parseRectangles(byteMatrix);
        StringBuilder svgContent = new StringBuilder();

        rectangles
                .stream()
                .forEach(
                        rectangle -> svgContent.append(
                                String.format("\n<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" style=\"fill:%s\" />",
                                        rectangle.getOrigin().x(),
                                        rectangle.getOrigin().y(),
                                        rectangle.getWidth(),
                                        rectangle.getHeight(),
                                        color)));

        return String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<svg viewBox=\"0 0 %d %d\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                "    %s\n" +
                "</svg>", dimension, dimension, svgContent);
    }

    @VisibleForTesting
    public void setMatrixParser(MatrixParser matrixParser) {
        this.matrixParser = matrixParser;
    }
}
