import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private static MutableGraph graph;

    public static void parseGraph(String filepath) throws IOException {
        graph = new Parser().read(new File(filepath));
    }

    public static int getNumNodes(MutableGraph graph) {
        return graph.nodes().size();
    }
    public static String getNodeLabels(MutableGraph graph) {
        Collection<MutableNode> nodes = graph.nodes();
        String result = "";
        for (MutableNode node : nodes) {
            Label x = node.name();
            result = result.concat(" " + x);
        }
        return result;
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
                "Node labels: " + getNodeLabels(graph) + "\n" +
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


    public static void main(String[] args) {
        try {
            String filename = "test.dot";
            parseGraph(filename);
            String output_filename = "output.png";
            outputGraph(output_filename);
            getNodeLabels(graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}