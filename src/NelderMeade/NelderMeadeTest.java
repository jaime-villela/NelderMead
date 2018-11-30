package NelderMeade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

class UserClass {
    static double userFunc(double[] row) {
        double retVal = Math.pow(row[0],2) - 4*row[0] + Math.pow(row[1], 2) - row[1] - row[0]*row[1];
        // Force the returned value to 2 decimal places
        return (double) Math.round(retVal * 100)/100;
    }
}

public class NelderMeadeTest {

    private NelderMeade nm;
    private double[][] points = {
            {0, 0},
            {1.2, 0},
            {0, 0.8}
    };

    @BeforeEach
    public void setUp() {
        nm = new NelderMeade();
        nm.setMatrix(points);
        nm.func = UserClass::userFunc;
        nm.calcBestGoodWorst();
    }

    @Test
    public void canCreate() throws Exception {
        double[][] points = {
                {0, 0},
                {1.2, 0},
                {0, 0.8}
        };
        NelderMeade nm = new NelderMeade(points);
        assertThat(nm, instanceOf(NelderMeade.class));
    }

    @Test
    public void testCalcBestGoodWorst() {
        assertEquals(nm.evalFuncAtBestPoint(), -3.36);
        assertEquals(nm.evalFuncAtGoodPoint(), -0.16);
        assertEquals(nm.evalFuncAtWorstPoint(), 0.0);
    }

    @Test
    public void testReflectionPoint() throws Exception {
        assertEquals(nm.evalFuncAtReflectPoint(), -4.48);
    }

    @Test
    public void testExpansionPoint() throws Exception {
        assertEquals(nm.evalFuncAtExpandPoint(), -5.88);
    }
}
