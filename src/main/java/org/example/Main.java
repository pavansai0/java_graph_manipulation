import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.IOException;

public class Main{

    public static void main(String[] args) {

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
