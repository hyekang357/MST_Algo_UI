package sample;

public class MST {

    public static Graph Kruskal(Graph inputGraph) {
        Graph MST = new Graph();
        // sort the edges from least weight to the greatest
        inputGraph.sortEdges();
        // Finding MST using Kruskal
        int numberNodes = inputGraph.getNumberNodes();
        for(int i = 0; i < inputGraph.getNumberEdges(); i++) {

            boolean cycle = MST.checkIfCycle(inputGraph.getEdge(i));
            if (!cycle) {
                MST.addEdge(inputGraph.getEdge(i));
                MST.addToTree(inputGraph.getEdge(i));
            }
            if (MST.getNumberEdges() == numberNodes - 1) {
                break;
            }
        }
        return MST;

    }

    public static Graph Prim(Graph inputGraph) {
        Graph MST = new Graph();
        int numberNodes = inputGraph.getNumberNodes();

        // pick any node from the set (This will pick the first one in the set)
        char startingNode = inputGraph.getRandomNode();
        MST.addNode(startingNode);

        while(MST.getNumberEdges() < numberNodes - 1) {
            Edge e = inputGraph.getSmallestEdge(MST.getNodes());
            MST.addEdge(e);
        }
        return MST;
    }
}
