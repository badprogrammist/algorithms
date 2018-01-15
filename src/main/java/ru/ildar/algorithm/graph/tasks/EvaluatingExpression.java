package ru.ildar.algorithm.graph.tasks;

import ru.ildar.algorithm.graph.DepthFirstRecursiveTraversal;
import ru.ildar.algorithm.graph.Graph;
import ru.ildar.algorithm.graph.GraphTraversal;

import java.util.function.BiFunction;

/**
 * @author Ildar Gafarov (ildar.gafarov.ufa@gmail.com)
 *         <p>
 *         5-9. [3] Suppose an arithmetic expression is given as a tree.
 *         Each leaf is an integer and each internal node is one of the standard arithmetical operations (+,−,∗,/).
 *         For example, the expression 2 + 3 ∗ 4 + (3 ∗ 4)/5 is represented by the tree in Figure 5.17(a).
 *         Give an O(n) algorithm for evaluating such an expression, where there are n nodes in the tree.
 *         </p>
 *         <p>
 *         <p>
 *         5-10. [5] Suppose an arithmetic expression is given as a DAG (directed acyclic graph)
 *         with common subexpressions removed. Each leaf is an integer and each internal
 *         node is one of the standard arithmetical operations (+,−,∗,/).
 *         Give an O(n + m) algorithm for evaluating such a DAG, where there are n nodes and m edges in the DAG.
 *         Hint: modify an algorithm for the tree case to achieve the desired efficiency.
 *         </p>
 */
public class EvaluatingExpression {

    private VertexData[] verticesData;

    public double eval(Graph graph, String[] verticesData) {
        initVerticesData(verticesData);
        DepthFirstRecursiveTraversal traversal = new DepthFirstRecursiveTraversal(graph);
        traversal.setVertexPostProcessor(this::postProcess);
        int start = 0;

        traversal.traverse(start);

        return this.verticesData[start].getNumber();
    }

    private void postProcess(GraphTraversal traversal, int vertex) {
        VertexData vertexData = verticesData[vertex];

        if (vertexData.getType() == VertexType.OPERATION) {
            int[] adjacentVertices = traversal.getGraph().getAdjacentVertices(vertex);

            if (adjacentVertices.length != 2) {
                throw new RuntimeException("The amount of operands should equals 2");
            }

            double n1 = verticesData[adjacentVertices[0]].getNumber();
            double n2 = verticesData[adjacentVertices[1]].getNumber();

            vertexData.eval(n1, n2);
        }
    }

    private void initVerticesData(String[] verticesData) {
        this.verticesData = new VertexData[verticesData.length];

        for (int i = 0; i < verticesData.length; i++) {
            this.verticesData[i] = createVertexData(verticesData[i]);
        }
    }

    private VertexData createVertexData(String data) {
        VertexType type;
        double number = 0;

        OperationType operationType = OperationType.getOperation(data);
        if (operationType == null) {
            type = VertexType.NUMBER;
            number = Double.valueOf(data);
        } else {
            type = VertexType.OPERATION;
        }

        return new VertexData(number, type, operationType);
    }

    private enum VertexType {
        NUMBER, OPERATION
    }

    private enum OperationType {
        ADD((n1, n2) -> n1 + n2),
        SUB((n1, n2) -> n1 - n2),
        MUL((n1, n2) -> n1 * n2),
        DIV((n1, n2) -> n1 / n2);

        private BiFunction<Double, Double, Double> f;

        OperationType(BiFunction<Double, Double, Double> f) {
            this.f = f;
        }

        static OperationType getOperation(String data) {
            if ("+".equals(data)) {
                return ADD;
            } else if ("/".equals(data)) {
                return DIV;
            } else if ("*".equals(data)) {
                return MUL;
            } else if ("-".equals(data)) {
                return SUB;
            }

            return null;
        }

        double eval(double n1, double n2) {
            return f.apply(n1, n2);
        }

    }

    private class VertexData {
        private double number;
        private VertexType type;
        private OperationType operationType;

        public VertexData(double number, VertexType type, OperationType operationType) {
            this.number = number;
            this.type = type;
            this.operationType = operationType;
        }

        public double getNumber() {
            return number;
        }

        public void eval(double n1, double n2) {
            if (operationType != null) {
                number = operationType.eval(n1, n2);
            }
        }

        public VertexType getType() {
            return type;
        }

    }

}
