package Point;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    @Test
    public void addPoints() {
        Point a = new Point(1,2);
        Point b = new Point(3,4);
        Point c = a.add(b);
        assertArrayEquals(c.getCoords(), new double[]{4,6});
    }

    @Test
    public void subPoints() throws Exception {
        Point a = new Point(1,2);
        Point b = new Point(3,4);
        Point c = a.sub(b);
        assertArrayEquals(c.getCoords(), new double[]{-2,-2});
    }
}
