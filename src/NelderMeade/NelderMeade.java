package NelderMeade;

public class NelderMeade {
    private int numRows;
    private int numCols;
    private double[][] matrix;

    public NelderMeade() {}

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        this.numRows = matrix.length;
        this.numCols = matrix[0].length;
    }

    public double[] addRows(int row1, int row2) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = matrix[row1][i] + matrix[row2][i];

        return retVal;
    }

    public double[] divByN(double[] row, int divisor) {
        double[] retVal = new double[this.numCols];

        try {
            for (int i = 0; i < this.numCols; i++)
                retVal[i] = row[i] / divisor;
        }

        catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
        }

        return retVal;
    }
}
