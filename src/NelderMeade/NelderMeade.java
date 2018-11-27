package NelderMeade;

import org.codehaus.groovy.runtime.typehandling.BigDecimalMath;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

@FunctionalInterface
interface UserDefinedFunc {
    double calculate(double[] row);
}

public class NelderMeade {
    private int numRows;
    private int numCols;
    private double[][] matrix;
    private int best;
    private int good;
    private int worst;
    private double[] reflectPoint;
    private double[] expandPoint;
    private double[] contractPoint;
    private double[] shrinkPoint;
    private double[] midpointGoodSide;
    UserDefinedFunc func;

    public NelderMeade() {}

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        this.numRows = matrix.length;
        this.numCols = matrix[0].length;
    }

    private void calcContractPoint() {
        this.contractPoint = calcMidpoint(this.matrix[worst], midpointGoodSide);
    }

    public double evalFuncAtContractPoint() {
        return evalFuncOnRow(this.contractPoint);
    }

    public void minimize() {
        while (true) { // while all vertices not converged
            if (evalFuncAtReflectPoint() < evalFuncAtGoodRow()) {
                if (evalFuncAtBestRow() < evalFuncAtReflectPoint()) {
                    //replace W with R
                } else {
                    if (evalFuncAtExpandPoint() < evalFuncAtBestRow()) {
                        // replace W with E
                    } else {
                        // replace W with R
                    }
                }
            } else {
                if (evalFuncAtReflectPoint() < evalFuncAtWorstRow()) {
                    // replace W with R
                }
                // compute C = (W+M)/2

                if (evalFuncAtContractPoint() < evalFuncAtWorstRow()) {
                    // replace W with C
                } else {
                    // compute S and f(S)
                }
                // replace W with S
                // replace G with M
            }
        }
    }

    private double[] addRows(double[] row1, double[] row2) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = row1[i] + row2[i];

        return retVal;
    }

    private double[] subtractRows(double[] row1, double[] row2) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = row1[i] - row2[i];

        return retVal;
    }

    private double[] multByN(double[] row, int multiplier) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = row[i] * multiplier;

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

    private double[] calcMidpoint(double[] row1, double[] row2) {
        return divByN(addRows(row1, row2), 2);
    }

    private double evalFuncOnRow(double[] row) {
        return this.func.calculate(row);
    }

    public void calcBestGoodWorst() {
        double[][] values = new double[this.numRows][this.numCols];

        for (int i = 0; i < this.numRows; i++) {
            values[i][0] = i;
            values[i][1] = this.func.calculate(matrix[i]);
        }

        Arrays.sort(values, Comparator.comparingDouble(a->a[1]));

        this.best = (int)values[0][0];
        this.good = (int)values[1][0];
        this.worst = (int)values[2][0];
    }

    public double evalFuncAtBestRow() {
        return this.func.calculate(matrix[best]);
    }

    public double evalFuncAtGoodRow() {
        return this.func.calculate(matrix[good]);
    }

    public double evalFuncAtWorstRow() {
        return this.func.calculate(matrix[worst]);
    }

    private void calcGoodMidpoint() {
        midpointGoodSide = calcMidpoint(this.matrix[best], this.matrix[good]);
    }

    private void calcReflectionPoint() {
        double[] modMidpoint;

        calcGoodMidpoint();
        modMidpoint = multByN(this.midpointGoodSide, 2);
        reflectPoint = subtractRows(modMidpoint, this.matrix[worst]);
    }

    public double evalFuncAtReflectPoint() {
        calcReflectionPoint();
        return evalFuncOnRow(this.reflectPoint);
    }

    private void calcExpandPoint() {
        double[] modReflectPoint;

        calcGoodMidpoint();
        calcReflectionPoint();
        modReflectPoint = multByN(this.reflectPoint, 2);
        this.expandPoint = subtractRows(modReflectPoint, this.midpointGoodSide);
    }

    public double evalFuncAtExpandPoint() {
        calcExpandPoint();;
        return evalFuncOnRow(this.expandPoint);
    }

}
