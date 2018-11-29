package Point;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    private Point a;
    private Point b;
    private IllegalArgumentException exception;

    @Test
    public void givenNoArgs_throwException() throws Exception {
        exception = assertThrows(IllegalArgumentException.class, ()->{new Point();});
        assertEquals("A point in space must have at least 2 coordinates.", exception.getMessage());
    }

    @Test
    public void givenOneArg_throwException() throws Exception {
        exception = assertThrows(IllegalArgumentException.class, ()->{new Point(1);});
        assertEquals("A point in space must have at least 2 coordinates.", exception.getMessage());
    }

    @Test
    public void givenNull_throwException() throws Exception {
        exception = assertThrows(IllegalArgumentException.class, ()->{new Point(null);});
        assertEquals("Cannot take null as an argument.", exception.getMessage());
    }

    @BeforeEach
    void setUp() {
        a = new Point(1,2);
        b = new Point(3,4);
    }

    @Test
    public void givenNumericValues_createObj() throws Exception {
        Point c = new Point(0,0);
        assertThat(c, instanceOf(Point.class));
        assertArrayEquals(c.getCoords(), new double[]{0,0});
    }

    @Test
    public void givenArray_createObj() throws Exception {
        double[] arr = {3.0, 5.0};
        Point c = new Point(arr);
        assertThat(c, instanceOf(Point.class));
        assertArrayEquals(c.getCoords(), arr);
    }

    @Test
    public void addPoints() {
        Point c = a.add(b);
        assertArrayEquals(c.getCoords(), new double[]{4,6});
    }

    @Test
    public void subtractPoints() throws Exception {
        Point c = a.sub(b);
        assertArrayEquals(c.getCoords(), new double[]{-2,-2});
    }

    @Test
    public void multiplyByNumber() throws Exception {
        Point c = a.multByN(2);
        assertArrayEquals(c.getCoords(), new double[]{2, 4});

    }

    @Test
    public void divideByNumber() throws Exception {
        Point c = a.divByN(2);
        assertArrayEquals(c.getCoords(), new double[]{0.5, 1});
    }

    @Test
    public void divideByZeroException() throws Exception {
        Point c = new Point(0,0);
        exception = assertThrows(ArithmeticException.class, ()->{c.divByN(0);});
        assertEquals("Division by zero.", exception.getMessage());
    }
}
