package fr.emse.ewall.service.qrcode;

import java.util.Hashtable;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import fr.emse.ewall.exception.QrCodeGeneratorException;
import fr.emse.ewall.service.svg.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Generate a SVG
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
@Service
public class QrCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeGenerator.class);

    /**
     * Generates a QRCode from an URL. We generate a QRCode which an error correction capability available at 30%
     *
     * @see <a href="https://en.wikipedia.org/wiki/QR_code#Error_correction">QRCode Error levels</a>
     */
    public QRCode generateQRCode(String url, ErrorCorrectionLevel errorCorrectionLevel) {
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel == null ? ErrorCorrectionLevel.H : errorCorrectionLevel);
        hintMap.put(EncodeHintType.MARGIN, 0);

        try {
            //We need to know informations on QRCode, so we generate a first QRCode like QRCodeWriter will do that
            return Encoder.encode(url, errorCorrectionLevel == null ? ErrorCorrectionLevel.H : errorCorrectionLevel, hintMap);
        }
        catch (WriterException e) {
            logger.error("Error on QrCode generation for url=" + url, e);
            throw new QrCodeGeneratorException();
        }
    }


    /**
     * Writes a QRCode in a matrix to generate a big QR code compounded with small QR codes
     */
    public void writeQRCodeInByteMatrix(QRCode smallQrCode, ByteMatrix byteMatrix, Point start, Point end) {

        for (int xBig = start.x(), xSmall = 0; xBig < end.x() + 1; xBig++) {

            for (int yBig = start.y(), ySmall = 0; yBig < end.y() + 1; yBig++) {
                if (xSmall < smallQrCode.getMatrix().getWidth() && ySmall < smallQrCode.getMatrix().getWidth()) {
                    byteMatrix.set(xBig, yBig, smallQrCode.getMatrix().get(xSmall, ySmall));
                }
                ySmall++;
            }
            xSmall++;
        }
    }
}
