package NelderMeade;

@FunctionalInterface
interface PolyFunc {
    double calculate(double[] row);
}

public class NelderMeade {
    private int numCols;
    private double[][] matrix;
    PolyFunc func;

    public NelderMeade() {}

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        this.numCols = matrix[0].length;
    }

    private double[] addRows(int row1, int row2) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = matrix[row1][i] + matrix[row2][i];

        return retVal;
    }

    private double[] divByN(double[] row, int divisor) {
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

    public double[] calcMidpoint(int row1, int row2) {
        return divByN(addRows(row1, row2), 2);
    }

    private double[] subtractRows(int row1, int row2) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = matrix[row1][i] - matrix[row2][i];

        return retVal;
    }

    private double[] multByN(double[] row, int multiplier) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = row[i] * multiplier;

        return retVal;
    }

    public double evalFuncOnRow(int row) {
        return this.func.calculate(this.matrix[row]);
    }


}
