package org.example;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class PavanGraph {

    private MutableGraph graph;

    public PavanGraph() {

        graph = mutGraph("makeNewGraph").setDirected(true).graphAttrs().add(Color.RED);

    }




    public void parseGraph(String filepath) throws IOException {
        this.graph = new Parser().read(new File(filepath));
    }

    public int getNumNodes(MutableGraph graph) {

        String r = getNodeLabels();
        String[] elements =r.split(" ");
        return elements.length;

    }
    public String processNumberString(String input) {
        // Split the input string by spaces to get an array of number strings
        String[] numberStrings = input.split(" ");

        // Create a list to store the unique numbers
        List<Integer> uniqueNumbers = new ArrayList<>();

        for (String numberStr : numberStrings) {
            try {
                int number = Integer.parseInt(numberStr);
                if (!uniqueNumbers.contains(number)) {
                    uniqueNumbers.add(number);
                }
            } catch (NumberFormatException e) {
                // Ignore non-integer values in the input
            }
        }

        // Convert the unique numbers to a space-separated string
        StringBuilder result = new StringBuilder();
        for (int number : uniqueNumbers) {
            result.append(number).append(" ");
        }

        // Remove the trailing space, if any
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
    public String getNodeLabels() {
        Collection<MutableNode> nodes = graph.nodes();
        String res = "";
        for (MutableNode node : nodes) {
            Label label = node.name();
            res = res.concat(" " + label);
        }
        return processNumberString(res);
    }
    public int getNumEdges(MutableGraph graph) {
        String r = getEdgeDirections(graph);
        String[] t = r.split("\n");

        return t.length;
    }
    public String getEdgeDirections(MutableGraph graph) {
        StringBuilder directions = new StringBuilder();
        graph.edges().forEach(edge -> {
            String source = edge.from().name().toString();
            String target = edge.to().name().toString();
            if(!"A".equals(source)){
            directions.append(source).append(" -> ").append(target).append("\n");}
        });
        return directions.toString();
    }

    public String toString(MutableGraph graph) {
        return "Number of nodes: " + getNumNodes(graph) + "\n" +
                "Node labels: " + getNodeLabels() + "\n" +
                "Number of edges: " + getNumEdges(graph) + "\n" +
                "Edge directions:" + getEdgeDirections(graph);
    }


    public void outputGraph(String filepath) throws IOException {
        File outputImage = new File(filepath);
        Graphviz.fromGraph(graph).width(800).render(Format.PNG).toFile(outputImage);
        String res = toString(graph);
        System.out.println(res);
        System.out.println("Graph created and saved as output.png");
    }

    public boolean nodeExists(String label) {
        String labels = getNodeLabels();
        return !labels.contains(label);
    }

    public void addNode(String label) {
        if(!nodeExists(label)) {
            graph.add(mutNode(label));
        }
    }


    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

    public MutableNode getNode(String label) {
        Collection<MutableNode> nodes = graph.nodes();
        for (MutableNode node : nodes) {
            Label labl = node.name();
            if (labl.toString().equals(label)) {
                return node;
            }
        }
        // If the node with the given label is not found, return null or throw an exception as appropriate.
        return null;
    }

    public void addEdge(String srcLabel, String dstLabel) {
//        Collection<MutableNode> nodes = graph.nodes();
//        MutableNode node1 = null, node2 = null;
//        for(MutableNode node : nodes){
//            //System.out.println(node.name());
//            if(node.name().toString().equals(srcLabel)) {
//                node1 = node;
//            }
//            else if(node.name().toString().equals(dstLabel)){
//                node2 = node;
//            }
//        }
//        if (node1 != null){
//            if(node2 != null){
//                graph.add(node1).addLink(node2);
//            }
//        }
        MutableNode node1 = getNode(srcLabel);
        MutableNode node2 = getNode(dstLabel);
        node1.addLink(node2);
    }

    public void outputDOTGraph(String path) {
        File dotFile = new File(path);
        try {
            Files.write(dotFile.toPath(), graph.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void outputGraphics(String path, String format) {
        Format graphFormat = Format.PNG;
        if ("png".equalsIgnoreCase(format)) {
            graphFormat = Format.PNG;
        }
        // You can add more format options as needed.

        File outputFile = new File(path);
        try {
            Graphviz.fromGraph(graph).width(800).render(graphFormat).toFile(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PavanGraph graphObject = new PavanGraph();
        try {
            // Replace "test.dot" with the path to your .dot file
            MutableGraph graph;
            graph = new Parser().read(new File("D:\\edu\\software security\\softwaretest\\test.dot"));

            // Output the graph in a specific format (e.g., PNG)
            File outputImage = new File("output.png");
            Graphviz.fromGraph(graph).width(800).render(Format.PNG).toFile(outputImage);

            System.out.println("Graph created and saved as output.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
