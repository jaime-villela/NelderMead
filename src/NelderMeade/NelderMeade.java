package NelderMeade;

import java.util.*;

import MyVertex.MyVertex;

@FunctionalInterface
interface UserDefinedFunc {
    double calculate(double[] coords);
}

public class NelderMeade {
    private ArrayList<MyVertex> allVertices;
    private int best;
    private int good;
    private int worst;
    private MyVertex reflectedVertex;
    private MyVertex expandedVertex;
    private MyVertex contractedVertex;
    private MyVertex goodMidpoint;
    private MyVertex badMidpoint;
    UserDefinedFunc func;
    private int numPoints;

    NelderMeade(double[][] pointMatrix) {
        int numRows = pointMatrix.length;

        allVertices = new ArrayList<MyVertex>();

        for(int i = 0; i < numRows; i++)
            allVertices.add(new MyVertex(pointMatrix[i]));

        numPoints = allVertices.size();
    }

    private void calcContractPoint() {
        calcGoodMidpoint();
        contractedVertex = calcMidpoint(allVertices.get(worst), goodMidpoint);
    }

    public boolean verticesNotConverged() {
        boolean notConverged = false;
        double bestVal = evalFuncAtPoint(allVertices.get(best));

        for (MyVertex v : allVertices) {
            if(Double.compare(evalFuncAtPoint(v), bestVal) != 0)
                notConverged = true;
        }

        return notConverged;
    }

    public double evalFuncAtContractPoint() {
        calcContractPoint();
        return evalFuncAtPoint(contractedVertex);
    }

    public void replaceVertexAtIndex(MyVertex p, int index) {
        allVertices.set(index, p);
    }

    private boolean isItBetterToReflect() {
        return (evalFuncAtReflectPoint() < evalFuncAtGoodPoint()) ? true : false;
    }

    private boolean isContractionPointBetterThanWorst() {
        return evalFuncAtContractPoint() < evalFuncAtWorstPoint();
    }

    private void shrinkTheSimplex() {
        calcBadMidpoint();
        replaceVertexAtIndex(badMidpoint, worst);
        calcGoodMidpoint();
        replaceVertexAtIndex(goodMidpoint, good);
    }

    private boolean isReflectionBetterThanWorst() {
        return evalFuncAtReflectPoint() < evalFuncAtWorstPoint();
    }

    private void contractOrShrink() {
        if (isReflectionBetterThanWorst())
            replaceVertexAtIndex(reflectedVertex, worst);

        calcContractPoint();

        if (isContractionPointBetterThanWorst())
            replaceVertexAtIndex(contractedVertex, worst);
        else
            shrinkTheSimplex();
    }

    private boolean isBestBetterThanReflection() {
        return evalFuncAtBestPoint() < evalFuncAtReflectPoint();
    }

    private boolean isExpansionBetterThanBest() {
        return evalFuncAtExpandPoint() < evalFuncAtBestPoint();
    }

    private void reflectOrExtend() {
        if (isBestBetterThanReflection())
            replaceVertexAtIndex(reflectedVertex, worst);
        else {
            calcExpandPoint();

            if (isExpansionBetterThanBest())
                replaceVertexAtIndex(expandedVertex, worst);
            else
                replaceVertexAtIndex(reflectedVertex,worst);
        }
    }

    private MyVertex calcMidpoint(MyVertex myVertex1, MyVertex myVertex2) {
        MyVertex c = myVertex1.add(myVertex2);
        return c.divByN(2);
    }

    private double evalFuncAtPoint(MyVertex p) {
        return func.calculate(p.getCoords());
    }

    private HashMap<Integer, Double> evalFuncAtAllPoints() {
        HashMap<Integer, Double> values = new HashMap<Integer, Double>();
        int i = 0;

        for (MyVertex v : allVertices)
            values.put(i++, evalFuncAtPoint(v));

        return values;
    }

    private List<Map.Entry<Integer, Double>> sortAllValues(HashMap<Integer, Double> hm) {
        Set<Map.Entry<Integer, Double>> set = hm.entrySet();
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(set);
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2) {
                return (entry2.getValue().compareTo(entry1.getValue()));
            }
        });
        return list;
    }

    public void calcBestGoodWorst() {
        HashMap<Integer, Double> allValues = evalFuncAtAllPoints();
        List<Map.Entry<Integer, Double>> sortedValues = sortAllValues(allValues);

        worst = (int) sortedValues.get(0).getKey();
        good = (int) sortedValues.get(1).getKey();
        best = (int) sortedValues.get(2).getKey();
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
        reflectedVertex = modMidpoint.sub(allVertices.get(worst));
    }

    public double evalFuncAtReflectPoint() {
        calcReflectionPoint();
        return evalFuncAtPoint(reflectedVertex);
    }

    private void calcExpandPoint() {
        MyVertex modReflectMyVertex;

        calcGoodMidpoint();
        calcReflectionPoint();
        modReflectMyVertex = reflectedVertex.multByN(2);
        expandedVertex = modReflectMyVertex.sub(goodMidpoint);
    }

    public double evalFuncAtExpandPoint() {
        calcExpandPoint();
        return evalFuncAtPoint(this.expandedVertex);
    }

    private void calcBadMidpoint() {
        badMidpoint = calcMidpoint(allVertices.get(best), allVertices.get(worst));
    }

    public int getNumPoints() {
        return numPoints;
    }

    public void findLocalMin() {
        int numIterations = 1;

        while (verticesNotConverged()) {
            if (isItBetterToReflect())
                reflectOrExtend();
            else
                contractOrShrink();

            calcBestGoodWorst();
            numIterations++;
        }
        System.out.format("It took %d iterations to converge.", numIterations);
    }
}
