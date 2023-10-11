import org.example.PavanGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
/*


    @Test
    public void testAddEdge() {
        graphObject.addNode("A");
        graphObject.addNode("B");
        graphObject.addEdge("A", "B");
        assertEquals(1, graphObject.getNumEdges(graphObject.graph));
    }

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