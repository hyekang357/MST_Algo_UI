package sample;

import java.lang.reflect.Array;
import java.util.*;

public class Graph {

    private ArrayList<Edge> edges;
    private Set<Character> nodes;
    private ArrayList<Set<Character>> trees;

    Graph() {
        this.edges = new ArrayList<>();
        this.nodes = new HashSet<>();
        this.trees = new ArrayList<>();
    }

    public void addEdge(Edge e) {
        this.edges.add(e);
        this.nodes.add(e.getNode1());
        this.nodes.add(e.getNode2());
    }

    public Edge getEdge(int index) {
        return this.edges.get(index);
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public int getNumberEdges() {
        return this.edges.size();
    }

    public void sortEdges() {
        Collections.sort(this.edges);
    }

    public Edge getSmallestEdge(Set<Character> MSTNodes) {
        Edge smallestEdge = null;
        for(int i = 0; i < this.edges.size(); i++) {
            Edge currentEdge = this.edges.get(i);
            if (MSTNodes.contains(currentEdge.getNode1()) ^ MSTNodes.contains(currentEdge.getNode2())) {
                if (smallestEdge == null || smallestEdge.getWeight() > currentEdge.getWeight()) {
                    smallestEdge = currentEdge;
                }
            }
        }
        return smallestEdge;
    }

    public Set<Character> getNodes() {
        return this.nodes;
    }

    public void addNode(char nodeValue) {
        this.nodes.add(nodeValue);
    }

    public char getRandomNode() {
        Iterator<Character> it = this.nodes.iterator();
        return it.next();
    }

    public int getNumberNodes() {
        return nodes.size();
    }

    public void print() {
        Collections.sort(this.edges);
        for (int i = 0; i < this.edges.size(); i++) {
            edges.get(i).print();
        }

    }

    public String toString() {
        String stringified = "";
        Collections.sort(this.edges);
        for (int i = 0; i < this.edges.size(); i++) {
            stringified += edges.get(i).toString();
        }
        return stringified;

    }

    public boolean checkIfCycle(Edge addingEdge) {
        boolean foundCycle = false;
        // for each tree in trees arraylist
        for(int i = 0; i < this.trees.size(); i++) {

            Set<Character> currentTree = this.trees.get(i);
            if (currentTree.contains(addingEdge.getNode1()) && currentTree.contains(addingEdge.getNode2())) {
                foundCycle = true;
                break;
            }
        }
        return foundCycle;

    }

    public void addToTree(Edge e) {
        ArrayList<Integer> foundInTreeIndex = new ArrayList<>();

        for(int i = 0; i < this.trees.size(); i++) {
            Set<Character> currentTree = this.trees.get(i);
            if (currentTree.contains(e.getNode1()) || currentTree.contains(e.getNode2())) {
                foundInTreeIndex.add(i);
            }
        }

        if (foundInTreeIndex.size() == 0) {
            // create a new tree
            Set<Character> newTree = new HashSet<>();
            // add the edges
            newTree.add(e.getNode1());
            newTree.add(e.getNode2());
            // add the tree to the trees
            this.trees.add(newTree);

        } else if (foundInTreeIndex.size() == 1) {
            this.trees.get(foundInTreeIndex.get(0)).add(e.getNode1());
            this.trees.get(foundInTreeIndex.get(0)).add(e.getNode2());

        } else if (foundInTreeIndex.size() == 2) {
            // Merging two trees
            this.trees.get(foundInTreeIndex.get(0)).addAll(this.trees.get(foundInTreeIndex.get(1)));
            this.trees.remove(foundInTreeIndex.get(1).intValue());
        } else {
            System.out.println("Error in found in tree index size:" + foundInTreeIndex.size());
        }

    }
}
