package sample;

import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    public TextArea TextAreaInput;
    public TextArea TextAreaOutput;
    public VBox VBoxForCanvas;

    public void populateSample(ActionEvent e) {
        String button_text = ((Button)e.getSource()).getText();
        String fileText = "";
        if (button_text.equals("Sample 1")) {
            fileText = CSVInput.readFromFile("src/sample/sample_1.csv");
        } else if (button_text.equals("Sample 2")) {
            fileText = CSVInput.readFromFile("src/sample/sample_2.csv");
        } else if (button_text.equals("Sample 3")) {
            fileText = CSVInput.readFromFile("src/sample/sample_3.csv");
        } else if (button_text.equals("Clear")) {
            fileText = "";
        } else {
            System.out.println("Error: populateSample button text unknown: " + button_text);
        }
        TextAreaInput.setText(fileText);
    }


    private WritableImage drawCircledNumber(char node) {
        //createCircledNumber() method always returns 26px X 26px sized image
        StackPane sPane = new StackPane();
        sPane.setPrefSize(40, 40);

        Circle c = new Circle(14);
        c.setStroke(Color.valueOf("b4b4b4"));
        c.setFill(Color.valueOf("e5e5e5"));
        c.setStrokeWidth(1);
        c.toFront();
        sPane.getChildren().add(c);

        Text nodeValue = new Text(String.valueOf(node));
        nodeValue.setFont(new Font(14));
        sPane.getChildren().add(nodeValue);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        return sPane.snapshot(parameters, null);
    }

    private static NodeCircles getNodesSpacing(Graph g) {

        // find a node that has the most connected edge
        NodeCircles ncs = new NodeCircles();
        for(char n : g.getNodes()) {
            NodeCircle nc = new NodeCircle(n);
            for (Edge e: g.getEdges()) {
                if (e.getNode1() == n) {
                    nc.addEdgeTo(e.getNode2());
                } else if (e.getNode2() == n) {
                    nc.addEdgeTo(e.getNode1());
                }
            }
            ncs.addNodeCircle(nc);
        }
        ncs.evaluateXY();
        ncs.print();
        return ncs;
    }

    private Canvas drawGraphOnCanvas(Graph g) {

        Canvas c = new Canvas(918.0, 483.0);
        GraphicsContext GC = c.getGraphicsContext2D();

        NodeCircles ncs = getNodesSpacing(g);
        // Draw lines first
        HashMap<Character, Double> xCoordinates = new HashMap<>();
        HashMap<Character, Double> yCoordinates = new HashMap<>();

        for(NodeCircle nc: ncs.getNcs()) {
            xCoordinates.put(nc.getValue(), nc.getX() + 11);
            yCoordinates.put(nc.getValue(), nc.getY() + 18);
        }

        ArrayList<Character> lineDrawn = new ArrayList<>();
        for(NodeCircle nc : ncs.getNcs()) {
            for(char e : nc.getEdgeTo()) {
                // if not already drawn
                if (!lineDrawn.contains(e)) {
                    // draw line
                    GC.setLineWidth(1.0);
                    GC.moveTo(xCoordinates.get(nc.getValue()), yCoordinates.get(nc.getValue()));  // from the nc.value
                    GC.lineTo(xCoordinates.get(e), yCoordinates.get(e)); // to nc.edgeto
                    GC.stroke();
                }
            }
            lineDrawn.add(nc.getValue());
        }

        for(NodeCircle nc : ncs.getNcs()) {
            GC.drawImage(drawCircledNumber(nc.getValue()), nc.getX(), nc.getY());
        }
        return c;
    }

    private Canvas drawMstOnCanvas(Graph g, Graph mst) {

        Canvas c = new Canvas(918.0, 483.0);
        GraphicsContext GC = c.getGraphicsContext2D();

        NodeCircles ncs = getNodesSpacing(g);
        // Draw lines first
        HashMap<Character, Double> xCoordinates = new HashMap<>();
        HashMap<Character, Double> yCoordinates = new HashMap<>();

        for(NodeCircle nc: ncs.getNcs()) {
            xCoordinates.put(nc.getValue(), nc.getX() + 11);
            yCoordinates.put(nc.getValue(), nc.getY() + 18);
        }

        ArrayList<String> mstEdges = new ArrayList<>();
        for(Edge e: mst.getEdges()) {
            mstEdges.add(String.valueOf(e.getNode1()) + String.valueOf(e.getNode2()));
            mstEdges.add(String.valueOf(e.getNode2()) + String.valueOf(e.getNode1()));
        }

        for(Edge e: mst.getEdges()) {
            GC.setLineWidth(5.0);
            GC.setStroke(Color.RED);
            GC.moveTo(xCoordinates.get(e.getNode1()), yCoordinates.get(e.getNode1()));
            GC.lineTo(xCoordinates.get(e.getNode2()), yCoordinates.get(e.getNode2()));
            GC.stroke();
        }

        for(Edge e: g.getEdges()) {
            if (!mstEdges.contains(String.valueOf(e.getNode1()) + String.valueOf(e.getNode2()))) {
                GC.setLineWidth(1.0);
                GC.setStroke(Color.BLACK);
                GC.moveTo(xCoordinates.get(e.getNode1()), yCoordinates.get(e.getNode1()));
                GC.lineTo(xCoordinates.get(e.getNode2()), yCoordinates.get(e.getNode2()));
                GC.stroke();
            }
        }

        for(NodeCircle nc : ncs.getNcs()) {
            GC.drawImage(drawCircledNumber(nc.getValue()), nc.getX(), nc.getY());
        }
        return c;
    }

    public void createGraph() {
        String input = TextAreaInput.getText();

        if (input.equals("")) {
            AlertBox.display("Error", "Input must not be empty!");

        } else {
            Graph g = new Graph();
            String[] lines = input.split("\\n");
            for(String line: lines) {
                String[] cell = line.split(",");
                Edge e = new Edge(cell[0].charAt(0), cell[1].charAt(0), Integer.parseInt(cell[2]));
                g.addEdge(e);
            }

            TextAreaOutput.setText(g.toString());
            Canvas c = drawGraphOnCanvas(g);
            VBoxForCanvas.getChildren().remove(0);
            VBoxForCanvas.getChildren().add(0, c);
        }
    }

    public void runKruskal() {
        String input = TextAreaOutput.getText();

        if (input.equals("")) {
            AlertBox.display("Error", "Input must not be empty!");

        } else {
            Graph g = new Graph();
            String[] lines = input.split("\\n");
            for (String line : lines) {
                String[] cell = line.split("---");
                Edge e = new Edge(cell[0].charAt(0), cell[2].charAt(0), Integer.parseInt(cell[1]));
                g.addEdge(e);
            }

            Graph mst = MST.Kruskal(g);
            String MSTString = "---------------Kruskal MST---------------\n" + mst.toString();
            TextAreaOutput.setText(TextAreaOutput.getText() + MSTString);

            Canvas c = drawMstOnCanvas(g, mst);
            VBoxForCanvas.getChildren().remove(0);
            VBoxForCanvas.getChildren().add(0, c);
        }
    }

    public void runPrims() {
        String input = TextAreaOutput.getText();

        if (input.equals("")) {
            AlertBox.display("Error", "Input must not be empty!");

        } else {
            Graph g = new Graph();
            String[] lines = input.split("\\n");
            for (String line : lines) {
                String[] cell = line.split("---");
                Edge e = new Edge(cell[0].charAt(0), cell[2].charAt(0), Integer.parseInt(cell[1]));
                g.addEdge(e);
            }

            Graph mst = MST.Prim(g);
            mst.print();

            String MSTString = "---------------PRIM MST---------------\n" + mst.toString();
            TextAreaOutput.setText(TextAreaOutput.getText() + MSTString);

            Canvas c = drawMstOnCanvas(g, mst);
            VBoxForCanvas.getChildren().remove(0);
            VBoxForCanvas.getChildren().add(0, c);
        }
    }

}
