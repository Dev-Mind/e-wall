package fr.emse.numericwall.service;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
@Service
public class MatrixParser {

    /**
     * When we build a QRCode a matrix is generated with all points. We use the method {@link BitMatrix#get(int, int)} which return true if a pixel is painted.
     * The goal is to build a svg image from this matrix. Each line is analysed to know one by one. We don't use a complex search because a square can contain
     * a blank area<br>
     * Example with a blank in a square<br>
     * <pre>
     *     1 1 1
     *     1 0 1
     *     1 1 1
     * </pre>
     *
     * @param bitMatrix
     */
    public List<Rectangle> parseRectangles(BitMatrix bitMatrix) {
        Objects.requireNonNull(bitMatrix);
        List<Rectangle> rectangles = Lists.newArrayList();

        //We read line by line
        for (int y = 0; y < bitMatrix.getHeight(); y++) {

            //We read the point from the left to the right
            for (int x = 0; x < bitMatrix.getWidth(); x++) {

                Point startedPoint = Point.create(x, y);

                if (bitMatrix.get(x, y)) {
                    //If we are on the last cell we create a recangle with a size of 1
                    if(x + 1 == bitMatrix.getWidth()){
                        rectangles.add(Rectangle.create(startedPoint, 1, 1));
                    }
                    //We search the next point to the right
                    for (x = x + 1; x < bitMatrix.getWidth(); x++) {
                        //If the next point is not used or if we are on the last column
                        if (!bitMatrix.get(x, y)) {
                            rectangles.add(Rectangle.create(startedPoint, x - startedPoint.x(), 1));
                            break;
                        }
                        else if(x + 1 == bitMatrix.getWidth()){
                            rectangles.add(Rectangle.create(startedPoint, x - startedPoint.x() + 1, 1));
                        }
                    }

                }
            }
        }

        return rectangles;
    }


}
