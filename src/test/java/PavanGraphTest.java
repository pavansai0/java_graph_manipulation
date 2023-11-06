import guru.nidi.graphviz.attribute.Color;
import org.example.PavanGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    @Test
    public void testRemoveNode() throws IOException {
        PavanGraph.parseGraph("test.dot");
        PavanGraph.removeNode("2", "test.dot");

        try (BufferedReader reader = new BufferedReader(new FileReader("test.dot"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                assertFalse(line.contains("\"" + "2" + "\""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRemoveNodes() throws IOException {
        PavanGraph.parseGraph("test.dot");
        String[] test_labels = new String[] {"2", "3"};
        PavanGraph.removeNodes(test_labels, "test.dot");

        // Verify that all labels in the list are removed
        try (BufferedReader reader = new BufferedReader(new FileReader("test.dot"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String label : test_labels) {
                    assertFalse(line.contains("\"" + label + "\""));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //testing removal of nodes that don't exist in graph
    @Test
    public void testRemoveNodesThatDontExist() throws IOException {
        PavanGraph.parseGraph("test.dot");
        String[] test_labels = new String[] {"2"};
        PavanGraph.removeNodes(test_labels, "test.dot");

        // Verify that all labels in the list are removed
        try (BufferedReader reader = new BufferedReader(new FileReader("test.dot"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String label : test_labels) {
                    assertFalse(line.contains("\"" + label + "\""));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //testing removal of duplicate nodes in the form of same label names within the string
    @Test
    public void testRemoveDuplicateNodes() throws IOException {
        PavanGraph.parseGraph("test.dot");
        String[] test_labels = new String[] {"2", "2"};
        PavanGraph.removeNodes(test_labels, "test.dot");

        // Verify that all labels in the list are removed
        try (BufferedReader reader = new BufferedReader(new FileReader("test.dot"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String label : test_labels) {
                    assertFalse(line.contains("\"" + label + "\""));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRemoveEdge() throws IOException {
        String src = "3";
        String dst = "5";
        PavanGraph.parseGraph("test.dot");
        PavanGraph.removeEdge(src, dst, "test.dot");

        // Verify that the specified edge is removed
        try (BufferedReader reader = new BufferedReader(new FileReader("test.dot"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                assertFalse(line.contains("\"" + src + "\"") && line.contains("\"" + dst + "\""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //tests for non-existent edge removal
    @Test
    public void testRemoveNoneExistentEdge() throws IOException {
        String src = "1";
        String dst = "3";
        PavanGraph.parseGraph("test.dot");
        PavanGraph.removeEdge(src, dst, "test.dot");

        // Verify that the specified edge is removed
        try (BufferedReader reader = new BufferedReader(new FileReader("test.dot"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                assertFalse(line.contains("\"" + src + "\"") && line.contains("\"" + dst + "\""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHasPathDFS_PathExists() throws IOException {

        PavanGraph.parseGraph("test.dot");
        int n = PavanGraph.graph.nodes().size();
        PavanGraph.Path p = new PavanGraph.Path(n);
        ArrayList<Integer> curpath = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0, 3, 5, 6));
        p.GraphSearchDFS("0","6",curpath);
        assertEquals(curpath, expected);
    }

    @Test
    public void testHasPathDFS_PathDoesNotExist() throws IOException {
        PavanGraph.parseGraph("test.dot");
        int n = PavanGraph.graph.nodes().size();
        PavanGraph.Path p = new PavanGraph.Path(n);
        ArrayList<Integer> curpath = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(0, 4, 5, 6));
        p.GraphSearchDFS("1","3",curpath);
        assertFalse(p.exist); // Path should be empty
    }


    @Test
    public void testHasPathDFS_SameSourceAndDestination() throws IOException {

        PavanGraph.parseGraph("test.dot");
        int n = PavanGraph.graph.nodes().size();
        PavanGraph.Path p = new PavanGraph.Path(n);
        ArrayList<Integer> curpath = new ArrayList<>();


        ArrayList<Integer> currentpath = new ArrayList<>();
        boolean result = p.GraphSearchDFS("0", "0", currentpath);
        assertTrue(result);
        assertTrue(p.exist);
        System.out.println(p.path);
        assertEquals(0, p.path.size()); //path length is 0 in this case
    }


}



