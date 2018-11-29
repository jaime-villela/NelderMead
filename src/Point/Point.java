package Point;

import com.siyeh.ig.numeric.DivideByZeroInspection;
import org.jdom.internal.ArrayCopy;

import java.util.Arrays;

public class Point {
    private double[] coords;

    Point(double... args) {
        int i = 0;

        if (args == null)
            throw new IllegalArgumentException("Cannot take null as an argument.");

        if (args.length < 2)
            throw new IllegalArgumentException("A point in space must have at least 2 coordinates.");

        this.coords = Arrays.copyOf(args, args.length);
    }

    public Point add(Point b) {
        Point c = new Point(this.coords);

        for (int i = 0; i < this.coords.length; i++)
                c.coords[i] += b.coords[i];

        return c;
    }

    public Point sub(Point b) {
        Point c = new Point(this.coords);

        for (int i = 0; i < this.coords.length; i++)
            c.coords[i] -= b.coords[i];

        return c;
    }

    public Point multByN(int mulitplier) {
        Point c = new Point(this.coords);

        for (int i = 0; i < c.coords.length; i++)
            c.coords[i] *= mulitplier;

        return c;
    }

    public Point divByN(int divisor) {
        Point c = new Point(this.coords);

        try {
            for (int i = 0; i < c.coords.length; i++)
                c.coords[i] /= divisor;
        } catch (ArithmeticException e) {
            System.out.println("Division by zero.");
            return null;
        }

        return c;
    }

    public double[] getCoords() {
        return this.coords;
    }
}
