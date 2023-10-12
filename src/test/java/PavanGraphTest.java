import guru.nidi.graphviz.attribute.Color;
import org.example.PavanGraph;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static org.junit.jupiter.api.Assertions.*;

public class PavanGraphTest {

    @BeforeEach
    public void init() {
        PavanGraph.graph = mutGraph("makeNewGraph").setDirected(true).graphAttrs().add(Color.BLACK);
    }

    @Test
    public void testAddNode() {

        PavanGraph.addNode("11");
        assertFalse(PavanGraph.nodeExists("11"));
    }

    @Test
    public void testGetNumNodes() {
        int numNodes = PavanGraph.getNumNodes(PavanGraph.graph);

        assertEquals(0, numNodes);  // The initial graph should have 0 nodes.
    }

    @Test
    public void testGetLabels(){
        String nodeLabels = PavanGraph.getNodeLabels();
        assertEquals("", nodeLabels);
    }
    @Test
    public void testGetEdgeDirections() {
        String edgeDirections = PavanGraph.getEdgeDirections(PavanGraph.graph);
        assertEquals("", edgeDirections); // The initial graph should have no edges.
    }

    @Test
    public void testAddEdge() {
        String[] s = {"Node1","Node2"};
        PavanGraph.addNodes(s);
        PavanGraph.addEdge("Node1", "Node2");
        String edgeDirections = PavanGraph.getEdgeDirections(PavanGraph.graph);
        assertTrue(edgeDirections.contains("Node1 -> Node2"));
    }

    @Test
    public void testGetNode(){
        PavanGraph.addNode("A");
        assertEquals(1,PavanGraph.getNumNodes(PavanGraph.graph));


    }
    @Test
    public void testtoString(){
        PavanGraph.addNode("A");
        assertNotNull(PavanGraph.toString(PavanGraph.graph));
    }
    @Test
    public void testoutputformat() throws IOException {
        PavanGraph.addNode("A");
        PavanGraph.addNode("B");
        String output_filename = "output.png";

        PavanGraph.outputGraph(output_filename);
        PavanGraph.outputDOTGraph("output.dot");
        PavanGraph.outputGraphics("output2.png", "png");
        assertNotNull("output.png");
        assertNotNull("output2.png");


    }

}



