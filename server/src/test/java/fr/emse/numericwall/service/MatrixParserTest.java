package fr.emse.numericwall.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.google.zxing.common.BitMatrix;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Test de {@link MatrixParser}
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
public class MatrixParserTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private MatrixParser matrixParser;

    /**
     * Test {@link MatrixParser#parseRectangles(BitMatrix)}
     * Case
     * <pre>
     *     null
     * </pre>
     */
    @Test(expected = NullPointerException.class)
    public void should_throw_npe_when_parse_null_matrix(){
        matrixParser.parseRectangles(null);
    }

    /**
     * Test {@link MatrixParser#parseRectangles(BitMatrix)}
     * Case plain line
     * <pre>
     *     0 0 0 0
     *     0 0 0 0
     * </pre>
     */
    @Test
    public void should_return_no_polygon_when_parse_matrix_with_empty_line(){
        assertThat(matrixParser.parseRectangles(new BitMatrix(4, 2))).isEmpty();
    }

    /**
     * Test {@link MatrixParser#parseRectangles(BitMatrix)}
     * Case plain line
     * <pre>
     *     1 1 1 1
     *     1 1 1 1
     * </pre>
     */
    @Test
    public void should_return_two_polygons_when_parse_matrix_with_full_line(){
        BitMatrix bitMatrix = new BitMatrix(4, 2);
        bitMatrix.setRegion(0, 0, 4, 2);

        List<Rectangle> rectangles = matrixParser.parseRectangles(bitMatrix);

        assertThat(rectangles).hasSize(2);
        assertThat(rectangles.get(0)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 0), 4, 1));
        assertThat(rectangles.get(1)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 1), 4, 1));
    }

    /**
     * Test {@link MatrixParser#parseRectangles(BitMatrix)}
     * Case matrix with a blank in a square
     * <pre>
     *     1 1 1 1
     *     1 0 0 1
     *     1 1 1 1
     *     0 1 1 0
     * </pre>
     */
    @Test
    public void should_return_several_polygons_when_parse_complex_matrix(){
        BitMatrix bitMatrix = new BitMatrix(4, 4);
        bitMatrix.setRegion(0, 0, 4, 1);
        bitMatrix.setRegion(0, 1, 1, 2);
        bitMatrix.setRegion(1, 2, 2, 2);
        bitMatrix.setRegion(3, 1, 1, 2);

        List<Rectangle> rectangles = matrixParser.parseRectangles(bitMatrix);

        assertThat(rectangles).hasSize(5);
        assertThat(rectangles.get(0)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 0), 4, 1));
        assertThat(rectangles.get(1)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 1), 1, 1));
        assertThat(rectangles.get(2)).isEqualToComparingFieldByField(Rectangle.create(Point.create(3, 1), 1, 1));
        assertThat(rectangles.get(3)).isEqualToComparingFieldByField(Rectangle.create(Point.create(0, 2), 4, 1));
        assertThat(rectangles.get(4)).isEqualToComparingFieldByField(Rectangle.create(Point.create(1, 3), 2, 1));
    }
}