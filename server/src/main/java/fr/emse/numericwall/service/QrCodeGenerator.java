package fr.emse.numericwall.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import com.google.common.annotations.VisibleForTesting;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
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

    /**
     * Generates a QRCode from an URL. We generate a QRCode which an error correction capability available at 30%
     *
     * @see <a href="https://en.wikipedia.org/wiki/QR_code#Error_correction">QRCode Error levels</a>
     */
    public QRCode generateQRCode(String url) {
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hintMap.put(EncodeHintType.MARGIN, 0);

        try {
            //We need to know informations on QRCode, so we generate a first QRCode like QRCodeWriter will do that
            return Encoder.encode(url, ErrorCorrectionLevel.H, hintMap);
        }
        catch (WriterException e) {
            logger.error("Error on QrCode generation for url=" + url, e);
            throw new QrCodeGeneratorException();
        }
    }

    /**
     * Transforms a QRCode to a SVG image. The size of the image depends on the version of the QR code and the version depend on the size of
     * the encoded bytes.
     *
     * @param color (black by default if this arg is null)
     * @see <a href="https://en.wikipedia.org/wiki/QR_code#Storage">QRCode Version</a>
     */
    public String generateSvg(QRCode qrCode, String color) {
        Objects.requireNonNull(qrCode);
        StringBuilder svgContent = new StringBuilder();
        List<Rectangle> rectangles = matrixParser.parseRectangles(qrCode);

        rectangles
                .stream()
                .forEach(
                        rectangle -> svgContent.append(
                                String.format("\n<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" style=\"fill:%s\" />",
                                        rectangle.getOrigin().x(),
                                        rectangle.getOrigin().y(),
                                        rectangle.getWidth(),
                                        rectangle.getHeight(),
                                        color==null ? "black" : color)));

        //the image dimension depend on the version of the QRCode
        return String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<svg viewBox=\"0 0 %d %d\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                        "    %s\n" +
                        "</svg>",
                qrCode.getMatrix().getWidth(),
                qrCode.getMatrix().getHeight(),
                svgContent);
    }

    @VisibleForTesting
    public void setMatrixParser(MatrixParser matrixParser) {
        this.matrixParser = matrixParser;
    }
}
