package Point;

import java.util.Arrays;

public class Point {
    private double[] coordArray;

    Point(double... args) {
        if (args == null)
            throw new IllegalArgumentException("Cannot take null as an argument.");

        if (args.length < 2)
            throw new IllegalArgumentException("A point in space must have at least 2 coordinates.");

        this.coordArray = Arrays.copyOf(args, args.length);
    }

    public Point add(Point b) {
        Point c = new Point(this.coordArray);

        for (int i = 0; i < this.coordArray.length; i++)
                c.coordArray[i] += b.coordArray[i];

        return c;
    }

    public Point sub(Point b) {
        Point c = new Point(this.coordArray);

        for (int i = 0; i < this.coordArray.length; i++)
            c.coordArray[i] -= b.coordArray[i];

        return c;
    }

    public Point multByN(int mulitplier) {
        Point c = new Point(this.coordArray);

        for (int i = 0; i < c.coordArray.length; i++)
            c.coordArray[i] *= mulitplier;

        return c;
    }

    public Point divByN(int divisor) {
        Point c = new Point(this.coordArray);

        try {
            for (int i = 0; i < c.coordArray.length; i++)
                c.coordArray[i] /= divisor;
        } catch (ArithmeticException e) {
            System.out.println("Division by zero.");
            return null;
        }

        return c;
    }

    public double[] getCoords() {
        return this.coordArray;
    }
}
