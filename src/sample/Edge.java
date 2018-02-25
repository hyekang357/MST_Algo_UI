package sample;

public class Edge implements Comparable<Edge> {
    private char node1;
    private char node2;
    private int weight;

    public Edge(char node1, char node2, int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public char getNode1() {
        return this.node1;
    }

    public char getNode2() {
        return this.node2;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public int compareTo(Edge comparingEdge) {
        int comparingWeight=((Edge)comparingEdge).getWeight();
        return this.weight - comparingWeight;
    }

    public void print() {
        System.out.println(node1 + "---" + weight + "---" + node2);
    }

    public String toString() {
        return node1 + "---" + weight + "---" + node2 + "\n";
    }
}
