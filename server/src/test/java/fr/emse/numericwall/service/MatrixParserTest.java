package fr.emse.numericwall.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Test de {@link MatrixParser}
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
public class MatrixParserTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private MatrixParser matrixParser;

    /**
     * Test {@link MatrixParser#parseRectangles(com.google.zxing.qrcode.encoder.QRCode)}
     * Case
     * <pre>
     *     null
     * </pre>
     */
    @Test(expected = NullPointerException.class)
    public void should_throw_npe_when_parse_null_matrix() {
        matrixParser.parseRectangles(null);
    }

    /**
     * Test {@link MatrixParser#parseRectangles(com.google.zxing.qrcode.encoder.QRCode)}
     * Case plain line
     * <pre>
     *     0 0 0 0
     *     0 0 0 0
     * </pre>
     */
    @Test
    public void should_return_no_polygon_when_parse_matrix_with_empty_line() {
        QRCode qrCode = new QRCode();
        qrCode.setMatrix(new ByteMatrix(4, 2));
        assertThat(matrixParser.parseRectangles(qrCode)).isEmpty();
    }

    /**
     * Test {@link MatrixParser#parseRectangles(com.google.zxing.qrcode.encoder.QRCode)}
     * Case plain line
     * <pre>
     *     1 1 1 1
     *     1 1 1 1
     * </pre>
     */
    @Test
    public void should_return_two_polygons_when_parse_matrix_with_full_line() {
        ByteMatrix byteMatrix = new ByteMatrix(4, 2);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 2; y++) {
                byteMatrix.set(x, y, true);
            }
        }
        QRCode qrCode = new QRCode();
        qrCode.setMatrix(byteMatrix);

        List<Rectangle> rectangles = matrixParser.parseRectangles(qrCode);

        assertThat(rectangles).hasSize(2);
        assertThat(rectangles.get(0)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 0), 4, 1));
        assertThat(rectangles.get(1)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 1), 4, 1));
    }

    /**
     * Test {@link MatrixParser#parseRectangles(com.google.zxing.qrcode.encoder.QRCode)}
     * Case matrix with a blank in a square
     * <pre>
     *     1 1 1 1
     *     1 0 0 1
     *     1 1 1 1
     *     0 1 1 0
     * </pre>
     */
    @Test
    public void should_return_several_polygons_when_parse_complex_matrix() {
        ByteMatrix byteMatrix = new ByteMatrix(4, 4);
        for (int x = 0; x < 4; x++) {
            byteMatrix.set(x, 0, true);
            byteMatrix.set(x, 2, true);
        }
        byteMatrix.set(0, 1, true);
        byteMatrix.set(3, 1, true);
        byteMatrix.set(1, 3, true);
        byteMatrix.set(2, 3, true);
        QRCode qrCode = new QRCode();
        qrCode.setMatrix(byteMatrix);

        List<Rectangle> rectangles = matrixParser.parseRectangles(qrCode);

        assertThat(rectangles).hasSize(5);
        assertThat(rectangles.get(0)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 0), 4, 1));
        assertThat(rectangles.get(1)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 1), 1, 1));
        assertThat(rectangles.get(2)).isEqualToComparingFieldByField(Rectangle.create(Point.create(3, 1), 1, 1));
        assertThat(rectangles.get(3)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 2), 4, 1));
        assertThat(rectangles.get(4)).isEqualToComparingFieldByField(Rectangle.create(Point.create(1, 3), 2, 1));
    }
}