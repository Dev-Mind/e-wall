package fr.emse.numericwall.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import com.google.common.base.Strings;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import fr.emse.numericwall.model.Category;
import org.junit.Before;
import org.junit.Test;

/**
 * In this test I want to prove myself that a big QR Code can be created with a set of  smaller QR codes
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 08/12/15.
 */
public class BigQrCodeTest {

    private QrCodeGenerator qrCodeGenerator;

    @Before
    public void init(){
        qrCodeGenerator = new QrCodeGenerator();
        qrCodeGenerator.setMatrixParser(new MatrixParser());
    }

    private void writeQRCodeInByteMatrix(ByteMatrix byteMatrix, QRCode smallQrCode, Point start, Point end){
        for(int xBig=start.x(),xSmall=0  ; xBig<end.x() ; xBig++) {
            for (int yBig = start.y(),ySmall=0; yBig < end.y(); yBig++) {
                if(xSmall < byteMatrix.getWidth() && ySmall < byteMatrix.getWidth()) {
                    byteMatrix.set(xBig, yBig, smallQrCode.getMatrix().get(xSmall, ySmall));
                }
                ySmall++;
            }
            xSmall++;
        }
    }

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

        //We take the next version because URL can be longer
        ByteMatrix byteMatrix = new ByteMatrix((dimension+4)*(dimension+4), (dimension+4)*(dimension+4));
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

                    writeQRCodeInByteMatrix(byteMatrix, smallQrCode, Point.create(x*(dimension+4), y*(dimension+4)), Point.create(x*(dimension+4) + (dimension+4-1), y*(dimension+4) + (dimension+4-1)));
                }
            }
        }

        //System.out.println(targetQRCode);
        String filePath = "/home/ehret_g/tmp/QRTest1.svg";
        String filePath1 = "/home/ehret_g/tmp/QRTest2.svg";

        String svg = qrCodeGenerator.generateSvg(targetQRCode, "black");

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
        outputStream1.write(qrCodeGenerator.generateSvg(qrCode, "black").getBytes());
        outputStream1.flush();
        outputStream1.close();
    }
}