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
    public PavanGraph() {

        graph = mutGraph("makeNewGraph").setDirected(true).graphAttrs().add(Color.RED);

    }


    public static MutableGraph graph;

    public static void parseGraph(String filepath) throws IOException {
        graph = new Parser().read(new File(filepath));
    }

    public static int getNumNodes(MutableGraph graph) {
        return graph.nodes().size();
    }
    public static String getNodeLabels() {
        Collection<MutableNode> nodes = graph.nodes();
        String res = "";
        for (MutableNode node : nodes) {
            Label label = node.name();
            res = res.concat(" " + label);
        }
        return res;
    }
    public static int getNumEdges(MutableGraph graph) {
        return graph.edges().size();
    }
    public static String getEdgeDirections(MutableGraph graph) {
        StringBuilder directions = new StringBuilder();
        graph.edges().forEach(edge -> {
            String source = edge.from().name().toString();
            String target = edge.to().name().toString();
            directions.append(source).append(" -> ").append(target).append("\n");
        });
        return directions.toString();
    }

    public static String toString(MutableGraph graph) {
        return "Number of nodes: " + getNumNodes(graph) + "\n" +
                "Node labels: " + getNodeLabels() + "\n" +
                "Number of edges: " + getNumEdges(graph) + "\n" +
                "Edge directions:" + getEdgeDirections(graph);
    }


    public static void outputGraph(String filepath) throws IOException {
        File outputImage = new File(filepath);
        Graphviz.fromGraph(graph).width(800).render(Format.PNG).toFile(outputImage);
        String res = toString(graph);
        System.out.println(res);
        System.out.println("Graph created and saved as output.png");
    }

    public static boolean nodeExists(String label) {
        String labels = getNodeLabels();
        return !labels.contains(label);
    }

    public static void addNode(String label) {
        if(nodeExists(label)) {
            graph.add(mutNode(label));
            System.out.println("added node" + label);
        }
    }


    public void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

    public static MutableNode getNode(String label) {
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
            String filename = "test.dot";
//            String output_filename = "output.png";
            String[] set_of_labels = new String[] {"12", "13", "14"};
            graphObject.parseGraph(filename);

            graphObject.addNode("11");
            graphObject.addNodes(set_of_labels);

            graphObject.addEdge("6", "11");
            graphObject.addEdge("5", "11");
            graphObject.addEdge("11", "12");
            graphObject.addEdge("7", "11");
            graphObject.addEdge("9", "13");
            graphObject.addEdge("9", "14");
            graphObject.addEdge("13", "10");
            graphObject.addEdge("14", "10");

//            graphObject.outputGraph(output_filename);
            graphObject.outputDOTGraph("output.dot");
            graphObject.outputGraphics("output2.png", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}