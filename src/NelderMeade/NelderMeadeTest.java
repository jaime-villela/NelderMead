package NelderMeade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NelderMeadeTest {

    private NelderMeade nm;
    private double[][] matrix = {
            {0, 0},
            {1.2, 0},
            {0, 0.8}
    };

    @BeforeEach
    public void setUp() {
        nm = new NelderMeade();
        nm.setMatrix(matrix);
    }

    @Test
    public void testDims() throws Exception {
        nm.setNumCols(2);
        nm.setNumRows(3);
        assertTrue(nm.doDimsMatch());
    }

    @Test
    public void testAddTwoRows() throws Exception {
        assertArrayEquals(nm.addRows(0,1), new double[]{1.2,0.0});
    }
}
