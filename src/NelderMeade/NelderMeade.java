package NelderMeade;

import java.util.Arrays;
import java.util.Comparator;

@FunctionalInterface
interface UserDefinedFunc {
    double calculate(double[] row);
}

public class NelderMeade {
    private int numRows;
    private int numCols;
    private double[][] allPoints;
    private int best;
    private int good;
    private int worst;
    private double[] reflectPoint;
    private double[] expandPoint;
    private double[] contractPoint;
    private double[] shrinkPoint;
    private double[] goodMidpoint;
    private double[] badMidpoint;
    UserDefinedFunc func;

    NelderMeade() {}

    NelderMeade(double[][] pointMatrix) {

    }

    public void setMatrix(double[][] matrix) {
        this.allPoints = matrix;
        this.numRows = matrix.length;
        this.numCols = matrix[0].length;
    }

    private void calcContractPoint() {
        this.contractPoint = calcMidpoint(this.allPoints[worst], goodMidpoint);
    }

    public double evalFuncAtContractPoint() {
        return evalFuncAtPoint(this.contractPoint);
    }

    private boolean areBestGoodWorstEqual() {
        boolean doesBestEqualGood = Double.compare(evalFuncAtBestPoint(), evalFuncAtGoodPoint()) == 0 ? true : false;
        boolean doesGoodEqualWorst = Double.compare(evalFuncAtGoodPoint(), evalFuncAtWorstPoint()) == 0 ? true : false;
        return doesBestEqualGood && doesGoodEqualWorst;

    }

    private void swapPoints(double[] point1, double[] point2) {
        double[] tmpArr;

        tmpArr = Arrays.copyOfRange(point1, 0, point1.length);
        point1 = Arrays.copyOfRange(point2, 0, point2.length);
        point2 = Arrays.copyOfRange(tmpArr, 0, tmpArr.length);
    }

    public void minimize() {
        while (! areBestGoodWorstEqual()) { // while all vertices not converged
            if (evalFuncAtReflectPoint() < evalFuncAtGoodPoint()) {
                if (evalFuncAtBestPoint() < evalFuncAtReflectPoint()) {
                    swapPoints(allPoints[worst], reflectPoint);
                } else {
                    if (evalFuncAtExpandPoint() < evalFuncAtBestPoint()) {
                        swapPoints(reflectPoint, expandPoint);// replace W with E
                    } else {
                        swapPoints(allPoints[worst], reflectPoint);// replace W with R
                    }
                }
            } else {
                if (evalFuncAtReflectPoint() < evalFuncAtWorstPoint()) {
                    swapPoints(allPoints[worst], reflectPoint);// replace W with R
                }
                calcContractPoint(); // compute C = (W+M)/2

                if (evalFuncAtContractPoint() < evalFuncAtWorstPoint()) {
                    swapPoints(allPoints[worst], contractPoint);// replace W with C
                } else {
                    // compute S and f(S)
                }
                // replace W with S
                // replace G with M
            }

            calcBestGoodWorst();
        }
    }

    private double[] addPoints(double[] point1, double[] point2) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = point1[i] + point2[i];

        return retVal;
    }

    private double[] subtractPoints(double[] point1, double[] point2) {
        double[] retVal = new double[this.numCols];

        for (int i = 0; i < this.numCols; i++)
            retVal[i] = point1[i] - point2[i];

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

    private double[] calcMidpoint(double[] point1, double[] point2) {
        return divByN(addPoints(point1, point2), 2);
    }

    private double evalFuncAtPoint(double[] row) {
        return this.func.calculate(row);
    }

    public void calcBestGoodWorst() {
        double[][] values = new double[this.numRows][this.numCols];

        for (int i = 0; i < this.numRows; i++) {
            values[i][0] = i;
            values[i][1] = this.func.calculate(allPoints[i]);
        }

        Arrays.sort(values, Comparator.comparingDouble(a->a[1]));

        this.best = (int)values[0][0];
        this.good = (int)values[1][0];
        this.worst = (int)values[2][0];
    }

    public double evalFuncAtBestPoint() {
        return this.func.calculate(allPoints[best]);
    }

    public double evalFuncAtGoodPoint() {
        return this.func.calculate(allPoints[good]);
    }

    public double evalFuncAtWorstPoint() {
        return this.func.calculate(allPoints[worst]);
    }

    private void calcGoodMidpoint() {
        goodMidpoint = calcMidpoint(this.allPoints[best], this.allPoints[good]);
    }

    private void calcReflectionPoint() {
        double[] modMidpoint;

        calcGoodMidpoint();
        modMidpoint = multByN(this.goodMidpoint, 2);
        reflectPoint = subtractPoints(modMidpoint, this.allPoints[worst]);
    }

    public double evalFuncAtReflectPoint() {
        calcReflectionPoint();
        return evalFuncAtPoint(this.reflectPoint);
    }

    private void calcExpandPoint() {
        double[] modReflectPoint;

        calcGoodMidpoint();
        calcReflectionPoint();
        modReflectPoint = multByN(this.reflectPoint, 2);
        this.expandPoint = subtractPoints(modReflectPoint, this.goodMidpoint);
    }

    public double evalFuncAtExpandPoint() {
        calcExpandPoint();;
        return evalFuncAtPoint(this.expandPoint);
    }

}
