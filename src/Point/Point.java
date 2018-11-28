package Point;

public class Point {
    private double[] coords;

    public Point(double ...args) {
        int i = 0;

        if (args.length < 2) {
            System.out.println("A point in space must have at least 2 coordinates.");
        }

        this.coords = new double[args.length];
        for (double d : args) {
            coords[i++] = d;
        }
    }

    public Point() {}

    public Point add(Point b) {
        Point c = new Point(0,0);

        try {
            for (int i = 0; i < this.coords.length; i++) {
                c.coords[i] = this.coords[i] + b.coords[i];
            }
        }

        catch (Exception e) {
            System.out.println("Addition failed:");
            System.out.println(e);
        }
        return c;
    }

    public Point sub(Point b) {
        Point c;

        c.coords = new double[this.coords.length];

        try {
            for (int i = 0; i < this.coords.length; i++) {
                c.coords[i] = this.coords[i] - b.coords[i];
            }
        }

        catch (Exception e) {
            System.out.println("Subtraction failed:");
            System.out.println(e);
        }
        return c;
    }

    public double[] getCoords() {
        return this.coords;
    }
}
