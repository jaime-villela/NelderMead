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
        return retVal;
    }
}

public class NelderMeadeTest {

    private NelderMeade nm;

    @BeforeEach
    public void setUp() {
        double[][] points = {
                {0, 0},
                {1.2, 0},
                {0, 0.8}
        };
        nm = new NelderMeade(points);
        nm.func = UserClass::userFunc;
        nm.calcBestGoodWorst();
    }

    @Test
    public void canCreate() throws Exception {
        assertThat(nm, instanceOf(NelderMeade.class));
        assertNotEquals(nm.getNumPoints(), 0);
    }

    @Test
    public void testBestGoodWorst() throws Exception {
        assertEquals(nm.evalFuncAtBestPoint(), -3.36);
        assertEquals(nm.evalFuncAtGoodPoint(), -0.16, 0.01);
        assertEquals(nm.evalFuncAtWorstPoint(), 0.0);
    }

    @Test
    public void testReflectionPoint() throws Exception {
        assertEquals(nm.evalFuncAtReflectPoint(), -4.48, 0.01);
    }

    @Test
    public void testExpansionPoint() throws Exception {
        assertEquals(nm.evalFuncAtExpandPoint(), -5.88);
    }

    @Test
    public void testBestGoodWorstAreEqual() throws Exception {
        assertTrue(nm.verticesNotConverged());
    }

    @Test
    public void testOptimization() throws Exception {
        nm.findLocalMin();
        assertEquals(nm.evalFuncAtBestPoint(), -6.99, 0.01);
        assertEquals(nm.evalFuncAtGoodPoint(), -6.99, 0.01);
        assertEquals(nm.evalFuncAtWorstPoint(), -6.99, 0.01);
        assertFalse(nm.verticesNotConverged());
    }
}
