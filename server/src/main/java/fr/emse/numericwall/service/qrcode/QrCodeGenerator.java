package fr.emse.numericwall.service.qrcode;

import java.util.Hashtable;

import com.google.common.annotations.VisibleForTesting;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import fr.emse.numericwall.exception.QrCodeGeneratorException;
import fr.emse.numericwall.service.svg.SvgConverter;
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

    private SvgConverter matrixParser;

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



    @VisibleForTesting
    public void setMatrixParser(SvgConverter svgConverter) {
        this.matrixParser = svgConverter;
    }
}
