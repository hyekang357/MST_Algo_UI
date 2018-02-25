package sample;

import java.util.ArrayList;
import java.util.Collections;

public class NodeCircles {

    private ArrayList<NodeCircle> ncs;

    private int[][] nodePoints = {
            {350, 380},
            {475, 110},
            {389, 232},
            {650, 182},
            {280, 82},
            {220, 300},
            {650, 300},
            {150, 150},
            {525, 340}
    };

    NodeCircles(){
       ncs = new ArrayList<>();
    }

    public ArrayList<NodeCircle> getNcs() {
        return this.ncs;
    }

    public void addNodeCircle(NodeCircle nc) {
        this.ncs.add(nc);
    }

    public NodeCircle getNCMaxEdgeTo() {
        Collections.sort(this.ncs);
        return this.ncs.get(0);
    }

    public void print() {
        for(NodeCircle nc: this.ncs) {
            nc.print();
        }
    }

    public void evaluateXY() {
        int np = 0;
        Collections.sort(this.ncs);
        ArrayList<NodeCircle> notDrawn = new ArrayList<>(this.ncs);
        ArrayList<NodeCircle> drawn = new ArrayList<>();

        System.out.println(notDrawn);
        System.out.println(drawn);

        this.print();

        while (notDrawn.size() > 0){

            System.out.println(notDrawn.size()); // size = 6
            System.out.println(np);
            System.out.println(drawn.size());

            if (drawn.size() == 0) {
                System.out.println("first node added: " + notDrawn.get(0).getValue());

                notDrawn.get(0).setX(nodePoints[np][0]);
                notDrawn.get(0).setY(nodePoints[np][1]);
                NodeCircle firstNC = new NodeCircle(notDrawn.get(0));
                drawn.add(firstNC);
                notDrawn.remove(0);
            } else {
                // for each of the edgeto
                for(char c : notDrawn.get(0).getEdgeTo()) {
                    boolean foundInDrawn = false;

                    System.out.println(c);

                    // check if it is already drawn
                    for(NodeCircle drawnNode : drawn) {

                        System.out.println("drawn node: " + drawnNode.getValue());

                        if (drawnNode.getValue() != c) {
                            NodeCircle addingNC = new NodeCircle(notDrawn.get(0));
                            notDrawn.get(0).setX(nodePoints[np][0]);
                            notDrawn.get(0).setY(nodePoints[np][1]);
                            drawn.add(addingNC);

                            notDrawn.remove(0);
                            foundInDrawn = true;
                            break;
                        }
                    }
                    if (foundInDrawn == true) {
                        break;
                    }
                }
            }
            np += 1;
            System.out.println(notDrawn);
            System.out.println(drawn);
            if (np > 12) {
                System.out.println("WE ARE BREAKING");
                break;
            }
        }

    }



}
