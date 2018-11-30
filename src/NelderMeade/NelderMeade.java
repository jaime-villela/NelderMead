package NelderMeade;

import java.util.*;

import MyVertex.MyVertex;
import jdk.nashorn.api.tree.Tree;

@FunctionalInterface
interface UserDefinedFunc {
    double calculate(double[] coords);
}

public class NelderMeade {
    private ArrayList<MyVertex> allVertices;
    private int best;
    private int good;
    private int worst;
    private MyVertex reflectMyVertex;
    private MyVertex expandMyVertex;
    private MyVertex contractMyVertex;
    private MyVertex goodMidpoint;
    private MyVertex badMidpoint;
    UserDefinedFunc func;
    private int numPoints;
    private int numIterations;

    NelderMeade() {}

    NelderMeade(double[][] pointMatrix) {
        int numRows = pointMatrix.length;

        allVertices = new ArrayList<MyVertex>();

        for(int i = 0; i < numRows; i++)
            allVertices.add(new MyVertex(pointMatrix[i]));

        numPoints = allVertices.size();
    }

    private void calcContractPoint() {
        calcGoodMidpoint();
        contractMyVertex = calcMidpoint(allVertices.get(worst), goodMidpoint);
    }

    public double evalFuncAtContractPoint() {
        calcContractPoint();
        return evalFuncAtPoint(contractMyVertex);
    }

    public boolean verticesNotConverged() {
        boolean notConverged = false;
        double firstVal = evalFuncAtPoint(allVertices.get(0));

        for (MyVertex v : allVertices) {
            if(Double.compare(evalFuncAtPoint(v), firstVal) != 0)
                notConverged = true;
        }

        return notConverged;
    }

    public void replaceVertexAtIndex(MyVertex p, int index) {
        allVertices.set(index, p);
    }

    public void findLocalMin() {
        numIterations = 1;

        while (verticesNotConverged()) {
            if (isItBetterToReflect())
                reflectOrExtend();
            else
                contractOrShrink();

            calcBestGoodWorst();
            numIterations++;
        }
    }

    private boolean isItBetterToReflect() {
        return (evalFuncAtReflectPoint() < evalFuncAtGoodPoint()) ? true : false;
    }

    private void contractOrShrink() {
        if (evalFuncAtReflectPoint() < evalFuncAtWorstPoint())
            replaceVertexAtIndex(reflectMyVertex, worst);

        calcContractPoint();

        if (evalFuncAtContractPoint() < evalFuncAtWorstPoint()) {
            replaceVertexAtIndex(contractMyVertex, worst);
        } else {
            evalFuncAtBadMidpoint();
            replaceVertexAtIndex(badMidpoint, worst);
            calcGoodMidpoint();
            replaceVertexAtIndex(goodMidpoint, good);
        }
    }

    private void reflectOrExtend() {
        if (evalFuncAtBestPoint() < evalFuncAtReflectPoint()) {
            replaceVertexAtIndex(reflectMyVertex, worst);
        } else {

            calcExpandPoint();

            if (evalFuncAtExpandPoint() < evalFuncAtBestPoint()) {
                replaceVertexAtIndex(expandMyVertex, worst);
            } else {
                replaceVertexAtIndex(reflectMyVertex,worst);
            }
        }
    }

    private MyVertex calcMidpoint(MyVertex myVertex1, MyVertex myVertex2) {
        MyVertex c = myVertex1.add(myVertex2);
        return c.divByN(2);
    }

    private double evalFuncAtPoint(MyVertex p) {
        return func.calculate(p.getCoords());
    }

    public void calcBestGoodWorst() {
        /*
        double[][] values = new double[numPoints][2];

        for (int i = 0; i < numPoints; i++) {
            values[i][0] = i;
            values[i][1] = func.calculate(allVertices.get(i).getCoords());
        }

        Arrays.sort(values, Comparator.comparingDouble(a->a[1]));

        best = (int)values[0][0];
        good = (int)values[1][0];
        worst = (int)values[2][0];
        */
        HashMap<Double, Integer> values = new HashMap<Double, Integer>();
        int i = 0;

        for (MyVertex v : allVertices) {
            values.put(evalFuncAtPoint(v), i++);
        }

        double[] arr = values.values().toArray();
        worst = (int)values.values().toArray()[0];
        good = (int)values.values().toArray()[1];
        best = (int)values.values().toArray()[2];
    }

    public double evalFuncAtBestPoint() {
        return func.calculate(allVertices.get(best).getCoords());
    }

    public double evalFuncAtGoodPoint() {
        return func.calculate(allVertices.get(good).getCoords());
    }

    public double evalFuncAtWorstPoint() {
        return func.calculate(allVertices.get(worst).getCoords());
    }

    private void calcGoodMidpoint() {
        goodMidpoint = calcMidpoint(allVertices.get(best), allVertices.get(good));
    }

    private void calcReflectionPoint() {
        MyVertex modMidpoint;

        calcGoodMidpoint();
        modMidpoint = goodMidpoint.multByN(2);
        reflectMyVertex = modMidpoint.sub(allVertices.get(worst));
    }

    public double evalFuncAtReflectPoint() {
        calcReflectionPoint();
        return evalFuncAtPoint(reflectMyVertex);
    }

    private void calcExpandPoint() {
        MyVertex modReflectMyVertex;

        calcGoodMidpoint();
        calcReflectionPoint();
        modReflectMyVertex = reflectMyVertex.multByN(2);
        expandMyVertex = modReflectMyVertex.sub(goodMidpoint);
    }

    public double evalFuncAtExpandPoint() {
        calcExpandPoint();;
        return evalFuncAtPoint(this.expandMyVertex);
    }

    public void calcBadMidpoint() {
        badMidpoint = calcMidpoint(allVertices.get(best), allVertices.get(worst));
    }

    public double evalFuncAtBadMidpoint() {
        calcBadMidpoint();;
        return evalFuncAtPoint(badMidpoint);
    }

    public int getNumPoints() {
        return numPoints;
    }

    public int getNumIterations() {
        return numIterations;
    }
}
