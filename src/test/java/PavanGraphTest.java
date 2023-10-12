import guru.nidi.graphviz.model.MutableNode;
import org.example.PavanGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PavanGraphTest {

    private PavanGraph graphObject;

    @BeforeEach
    public void init() {
        graphObject = new PavanGraph();
    }

    @Test
    public void testAddNode() {
        graphObject.addNode("11");
        assertTrue(graphObject.nodeExists("11"));
    }

    @Test
    public void testGetNumNodes() {
        int numNodes = graphObject.getNumNodes(graphObject.graph);

        assertEquals(0, numNodes);  // The initial graph should have 0 nodes.
    }

    @Test
    public void testGetLabels(){
        String nodeLabels = graphObject.getNodeLabels();
        assertEquals("", nodeLabels);
    }
    @Test
    public void testGetEdgeDirections() {
        String edgeDirections = graphObject.getEdgeDirections(graphObject.graph);
        assertEquals("", edgeDirections); // The initial graph should have no edges.
    }

    @Test
    public void testAddEdge() {
        graphObject.addNode("Node1");
        graphObject.addNode("Node2");
        graphObject.addEdge("Node1", "Node2");
        String edgeDirections = graphObject.getEdgeDirections(graphObject.graph);
        assertTrue(edgeDirections.contains("Node1 -> Node2"));
    }

    @Test
    public void testGetNode(){
        graphObject.addNode("A");
        Integer f=0;
        MutableNode x = graphObject.getNode("A");
        if( x==null){
            f=1;
        }


    }
    @Test
    public void testtoString(){
        graphObject.addNode("A");
        assertNotNull(graphObject.toString(graphObject.graph));
    }
    @Test
    public void testoutputformat(){
        graphObject.addNode("A");
        graphObject.addNode("B");

    }

}
/*



    @Test
    public void testOutputGraph() {
        try {
            graphObject.parseGraph("test.dot");
            graphObject.outputGraph("output.png");
            // Add assertions to check if the output file was created as expected
            // You can also compare the output with an expected output if needed
        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testOutputDOTGraph() {
        try {
            graphObject.parseGraph("test.dot");
            graphObject.outputDOTGraph("output.dot");
            // Add assertions to check if the output DOT file was created as expected
            // You can also compare the output with an expected output if needed
        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testOutputGraphics() {
        try {
            graphObject.parseGraph("test.dot");
            graphObject.outputGraphics("output.png", "png");
            // Add assertions to check if the output graphics file was created as expected
        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}*/