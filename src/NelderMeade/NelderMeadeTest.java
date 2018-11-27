package NelderMeade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DecimalStyle;

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
        nm.func = (double[] row)->Math.pow(row[0],2) - 4*row[0] + Math.pow(row[1], 2) - row[1] - row[0]*row[1];
    }

    @Test
    public void testCalcMidpoint() throws Exception {
        assertArrayEquals(nm.calcMidpoint(0,1), new double[]{0.6, 0.0});
    }

    @Test
    public void evalFuncAtV2() throws Exception {
        assertEquals(nm.evalFuncOnRow(1), -3.36);
    }

    @Test
    public void evalFuncAtV3() throws Exception {
        // Unfortunately, I have to play tricks with rounding to get this test to pass.
        assertEquals((double) Math.round(nm.evalFuncOnRow(2) * 100)/100, -0.16);
    }
}
