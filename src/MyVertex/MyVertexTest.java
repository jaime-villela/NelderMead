package MyVertex;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MyVertexTest {
    private MyVertex a;
    private MyVertex b;
    private IllegalArgumentException exception;

    @Test
    public void givenNoArgs_throwException() throws Exception {
        exception = assertThrows(IllegalArgumentException.class, ()->{new MyVertex();});
        assertEquals("A point in space must have at least 2 coordinates.", exception.getMessage());
    }

    @Test
    public void givenOneArg_throwException() throws Exception {
        exception = assertThrows(IllegalArgumentException.class, ()->{new MyVertex(1);});
        assertEquals("A point in space must have at least 2 coordinates.", exception.getMessage());
    }

    @Test
    public void givenNull_throwException() throws Exception {
        exception = assertThrows(IllegalArgumentException.class, ()->{new MyVertex(null);});
        assertEquals("Cannot take null as an argument.", exception.getMessage());
    }

    @BeforeEach
    void setUp() {
        a = new MyVertex(1,2);
        b = new MyVertex(3,4);
    }

    @Test
    public void givenNumericValues_createObj() throws Exception {
        MyVertex c = new MyVertex(0,0);
        assertThat(c, instanceOf(MyVertex.class));
        assertArrayEquals(c.getCoords(), new double[]{0,0});
    }

    @Test
    public void givenArray_createObj() throws Exception {
        double[] arr = {3.0, 5.0};
        MyVertex c = new MyVertex(arr);
        assertThat(c, instanceOf(MyVertex.class));
        assertArrayEquals(c.getCoords(), arr);
    }

    @Test
    public void addPoints() {
        MyVertex c = a.add(b);
        assertArrayEquals(c.getCoords(), new double[]{4,6});
    }

    @Test
    public void subtractPoints() throws Exception {
        MyVertex c = a.sub(b);
        assertArrayEquals(c.getCoords(), new double[]{-2,-2});
    }

    @Test
    public void multiplyByNumber() throws Exception {
        MyVertex c = a.multByN(2);
        assertArrayEquals(c.getCoords(), new double[]{2, 4});

    }

    @Test
    public void divideByNumber() throws Exception {
        MyVertex c = a.divByN(2);
        assertArrayEquals(c.getCoords(), new double[]{0.5, 1});
    }

    @Test
    public void divideByZeroException() throws Exception {
        ArithmeticException exception = assertThrows(ArithmeticException.class, ()->{ a.divByN(0); });
        assertEquals("Division by zero.", exception.getMessage());
    }
}
