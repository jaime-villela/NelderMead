package NelderMeade;

public class NelderMeade {
    private int numRows;
    private int numCols;
    private double[][] matrix;

    public NelderMeade() {}

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public boolean doDimsMatch() {
        if ((matrix.length == numRows) && (matrix[0].length == numCols)) {
            return true;
        } else {
            return false;
        }
    }

    public double[] addRows(int row1, int row2) {
        double[] retVal = new double[2];
        retVal[0] = matrix[row1][0]+matrix[row2][0];
        retVal[1] = matrix[row1][1]+matrix[row2][1];
        return retVal;
    }
}
