package MyVertex;

import java.util.Arrays;

public class MyVertex {
    private double[] coordArray;

    public MyVertex(double... args) {
        if (args == null)
            throw new IllegalArgumentException("Cannot take null as an argument.");

        if (args.length < 2)
            throw new IllegalArgumentException("A point in space must have at least 2 coordinates.");

        this.coordArray = Arrays.copyOf(args, args.length);
    }

    public MyVertex add(MyVertex b) {
        MyVertex c = new MyVertex(this.coordArray);

        for (int i = 0; i < this.coordArray.length; i++)
                c.coordArray[i] += b.coordArray[i];

        return c;
    }

    public MyVertex sub(MyVertex b) {
        MyVertex c = new MyVertex(this.coordArray);

        for (int i = 0; i < this.coordArray.length; i++)
            c.coordArray[i] -= b.coordArray[i];

        return c;
    }

    public MyVertex multByN(int mulitplier) {
        MyVertex c = new MyVertex(this.coordArray);

        for (int i = 0; i < c.coordArray.length; i++)
            c.coordArray[i] *= mulitplier;

        return c;
    }

    public MyVertex divByN(int divisor) {
        MyVertex c = new MyVertex(this.coordArray);

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
        return coordArray;
    }
}
