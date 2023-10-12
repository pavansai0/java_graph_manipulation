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

        //graph = mutGraph("makeNewGraph").setDirected(true).graphAttrs().add(Color.BLACK);

    }


    public static MutableGraph graph = mutGraph("makeNewGraph").setDirected(true).graphAttrs().add(Color.BLACK);;

    public static void parseGraph(String filepath) throws IOException {
        graph = new Parser().read(new File(filepath));
    }

    public static int getNumNodes(MutableGraph graph) {
        return graph.nodes().size();
    }
    public static String getNodeLabels() {
        Collection<MutableNode> n = graph.nodes();
        String res = "";
        for (MutableNode node : n) {
            Label label = node.name();
            res = res.concat(" " + label);
        }
        return res;
    }
    public static int getNumEdges(MutableGraph graph) {
        return graph.edges().size();
    }
    public static String getEdgeDirections(MutableGraph graph) {
        StringBuilder edge_directions = new StringBuilder();
        graph.edges().forEach(edge -> {
            String source = edge.from().name().toString();
            String target = edge.to().name().toString();
            edge_directions.append(source).append(" -> ").append(target).append("\n");
        });
        return edge_directions.toString();
    }

    public static String toString(MutableGraph graph) {
        return "Total number of nodes in the graph: " + getNumNodes(graph) + "\n" +
                "Label of the Nodes: " + getNodeLabels() + "\n" +
                "Total number of edges in the graph: " + getNumEdges(graph) + "\n" +
                "Direction of the Edges:" + getEdgeDirections(graph);
    }


    public static void outputGraph(String filepath) throws IOException {
        File outputImage = new File(filepath);
        Graphviz.fromGraph(graph).width(800).render(Format.PNG).toFile(outputImage);
        String res = toString(graph);
        System.out.println(res);

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


    public static void addNodes(String[] labels) {
        for (String label : labels) {
            addNode(label);
        }
    }

    public static MutableNode getNode(String label) {
        Collection<MutableNode> n = graph.nodes();
        for (MutableNode node : n) {
            Label labl = node.name();
            if (labl.toString().equals(label)) {
                return node;
            }
        }

        return null;
    }

    public static void addEdge(String src, String dst) {
        MutableNode n1 = getNode(src);
        MutableNode n2 = getNode(dst);
        n1.addLink(n2);
    }

    public static void outputDOTGraph(String path) {
        File dotFile = new File(path);
        try {
            Files.write(dotFile.toPath(), graph.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void outputGraphics(String path, String format) {
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


            graphObject.outputDOTGraph("output.dot");
            graphObject.outputGraphics("output2.png", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}