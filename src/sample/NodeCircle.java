package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NodeCircle implements Comparable<NodeCircle> {

    private char value;
    private ArrayList<Character> edgeTo;
    private double x;
    private double y;
    private ArrayList<NodeCircle> edgeToNode;

    NodeCircle (char value) {
        this.value = value;
        this.edgeTo = new ArrayList<>();
        this.x = 0.0;
        this.y = 0.0;
        this.edgeToNode = new ArrayList<>();
    }

    NodeCircle (NodeCircle c) {
        this.value = c.getValue();
        this.edgeTo = c.getEdgeTo();
        this.x = c.getX();
        this.y = c.getY();
    }

    public char getValue() {
        return this.value;
    }

    public void addEdgeTo(char n) {
        this.edgeTo.add(n);
    }

    public int getEdgeToSize() {
        return this.edgeTo.size();
    }

    public ArrayList<Character> getEdgeTo() {
        return this.edgeTo;
    }

    public ArrayList<NodeCircle> getEdgeToNode() {
        return this.edgeToNode;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }


    @Override
    public int compareTo(NodeCircle comparingNC) {
        int comparingSize=((NodeCircle)comparingNC).getEdgeToSize();
        return comparingSize - this.edgeTo.size();
    }

    public void print() {
        System.out.println("Node: " + this.value);
        System.out.print("Connected to: ");
        for(Character c: this.edgeTo){
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.println("X: " + this.x + " Y: " + this.y);

    }
}
