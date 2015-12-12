package fr.emse.numericwall.service.qrcode;


import java.io.File;
import java.io.FileOutputStream;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import fr.emse.numericwall.model.Category;
import fr.emse.numericwall.service.svg.Point;
import fr.emse.numericwall.service.svg.SvgConverter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * In this test I want to prove myself that a big QR Code can be created with a set of  smaller QR codes
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 08/12/15.
 */
@Ignore
public class BigQrCodeTest {

    private QrCodeGenerator qrCodeGenerator;

    private SvgConverter svgConverter;

    @Before
    public void init(){
        svgConverter = new SvgConverter();
        qrCodeGenerator = new QrCodeGenerator();
        qrCodeGenerator.setMatrixParser(svgConverter);
    }

//    private void writeQRCodeInByteMatrix(ByteMatrix byteMatrix, QRCode smallQrCode, Point start, Point end){
//        for(int xBig=start.x(),xSmall=0  ; xBig<end.x() ; xBig++) {
//            for (int yBig = start.y(),ySmall=0; yBig < end.y(); yBig++) {
//                if(xSmall <= byteMatrix.getWidth() && ySmall <= byteMatrix.getWidth()) {
//                    byteMatrix.set(xBig, yBig, smallQrCode.getMatrix().get(xSmall, ySmall));
//                }
//                ySmall++;
//            }
//            xSmall++;
//        }
//    }

    @Test
    public void should_generate_a_big_QRCode() throws Exception{
        Category category = new Category().setName("a");

        String url = "http://www.emse.fr/nw/a";

        //In the first step we generate a qrCode
        QRCode qrCode = qrCodeGenerator.generateQRCode(url);

        QRCode targetQRCode = new QRCode();
        targetQRCode.setMode(qrCode.getMode());
        targetQRCode.setECLevel(qrCode.getECLevel());
        targetQRCode.setVersion(qrCode.getVersion());
        targetQRCode.setMaskPattern(qrCode.getMaskPattern());

        int dimension = qrCode.getMatrix().getWidth();
        int margin = 6;

        //We take the next version because URL can be longer
        ByteMatrix byteMatrix = new ByteMatrix((dimension+4)*(dimension) + margin*dimension + margin, (dimension+4)*(dimension)+ margin*dimension + margin);
        targetQRCode.setMatrix(byteMatrix);

        //TODO add big QRCode to category
        //...

        int cpt = 0;

        //We need to read all the pixels of the main matrix
        for(int x=0 ; x<dimension ; x++){
            for(int y=0 ; y<dimension ; y++){
                //We construct the target matrix
                if(qrCode.getMatrix().get(x,y)==1){
                    cpt++;
                    String newUrl = url.concat("/").concat(String.valueOf(cpt));
                    QRCode smallQrCode = qrCodeGenerator.generateQRCode(newUrl);
//                    if(smallQrCode.getVersion().getVersionNumber()!=qrCode.getVersion().getVersionNumber()){
//                        throw new IllegalArgumentException("Pas bonne version big="
//                                + qrCode.getVersion().getVersionNumber()
//                                + " small="
//                                + smallQrCode.getVersion().getVersionNumber());
//                   }

                    //TODO add small QRCode to category
                    //...
                    int d = dimension +4 ;
                    qrCodeGenerator.writeQRCodeInByteMatrix(
                            smallQrCode,
                            byteMatrix,
                            Point.create(x * d + (x + 1) * margin, y * d + (y + 1) * margin),
                            Point.create(x * d + d -1 + (x + 1) * margin, y * d + d -1 + (y + 1) * margin));
                }
            }
        }

        //System.out.println(targetQRCode);
        String filePath = "/home/ehret_g/tmp/QRTest1.svg";
        String filePath1 = "/home/ehret_g/tmp/QRTest2.svg";

        String svg = svgConverter.generateSvg(targetQRCode, "black", svgConverter.generatePathSvg(targetQRCode));

        File myFile = new File(filePath);
        File myFile1 = new File(filePath1);

        FileOutputStream outputStream = new FileOutputStream(myFile);
        if (!myFile.exists()) {
            myFile.createNewFile();
        }



        // get the content in bytes
        byte[] contentInBytes = svg.getBytes();

        outputStream.write(contentInBytes);
        outputStream.flush();
        outputStream.close();

        FileOutputStream outputStream1 = new FileOutputStream(myFile1);
        if (!myFile1.exists()) {
            myFile1.createNewFile();
        }
        outputStream1.write(svgConverter.generateSvg(qrCode, "black", svgConverter.generatePathSvg(qrCode)).getBytes());
        outputStream1.flush();
        outputStream1.close();
    }
}