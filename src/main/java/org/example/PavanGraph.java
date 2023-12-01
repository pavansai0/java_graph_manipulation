package org.example;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.Parser;


import java.io.*;
import java.nio.file.Files;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class PavanGraph {
    public PavanGraph() {

        graph = mutGraph("makeNewGraph").setDirected(true).graphAttrs().add(Color.BLACK);

    }


    public static MutableGraph graph = mutGraph("makeNewGraph").setDirected(true).graphAttrs().add(Color.BLACK);

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


        if (nodeExists(label)) {
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

            //refactor 1 using extract variable
            boolean temp = labl.toString().equals(label);
            //end of refactor 1
            if (temp) {
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

    //refactor 2 using Extract method
    public static void outputgraphicshelper(Format gf, File outputFile) throws IOException {
        Graphviz.fromGraph(graph).width(800).render(gf).toFile(outputFile);
    }


    public static void outputGraphics(String path, String format) {
        Format graphFormat = Format.PNG;
        if ("png".equalsIgnoreCase(format)) {
            graphFormat = Format.PNG;
        }

        File outputFile = new File(path);
        try {
            outputgraphicshelper(graphFormat, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public static void removeNode(String label, String filepath) {
        File file = new File(filepath);
        String quotedLabel = "\"" + label + "\"";
        File tempFile = new File("tempFile.dot"); // Temporary file to store modified content

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(quotedLabel)) {
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original filename
        if (file.delete()) {
            tempFile.renameTo(file);
        }

    }

    public static void removeNodes(String[] labels, String filename) {
        for (String label : labels) {
            removeNode(label, filename);
        }

    }

    //refactoring 3 using Extract Method
    public static String AddQuotes(String input) {
        String output = "\"" + input + "\"";
        return output;
    }

    public static void removeEdge(String src, String dst, String filename) {
        File file = new File(filename);
        String quotedsrc = AddQuotes(src);
        String quoteddst = AddQuotes(dst);
        File tempFile = new File("tempFile.dot"); // Temporary file to store modified content

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                //refactor 4 using Extract variable
                boolean checkvalid = !(line.contains(quotedsrc) && line.contains(quoteddst));

                if (checkvalid) {
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original filename
        if (file.delete()) {
            tempFile.renameTo(file);
        }
    }


    // refactoring using Template pattern

    public static abstract class Path_Template {

        searchalgo search_obj;
        public static boolean exist;
        public static boolean[] visiteddfs;
        public static ArrayList<Integer> path;

        public enum Level {
            DFS,
            BFS,
            RWS
        }




        public abstract boolean BFSorDFS(String src, String dst, boolean[] visited, ArrayList<Integer> currentpath);


        //BFS and DFS implementation using the Strategy pattern design principle
        interface  searchalgo
        {
            public boolean search(String src, String dst, boolean[] visited, ArrayList<Integer> currentpath);
        }

        public static class bfsStrategy implements searchalgo
        {
            public int size = 0;

            public boolean search(String src, String dst, boolean[] visited, ArrayList<Integer> currentpath)
            {

                if (Integer.parseInt(src) == Integer.parseInt(dst)) {
                    exist = true;
                    path = new ArrayList<>();
                    path.addAll(currentpath);
                    System.out.println(path);
                    size = path.size();




                    return true;
                }
                visited[Integer.parseInt(src)] = true;
                MutableNode n1 = getNode(src);
                List<Link> x = n1.links();

                for (Link l : x) {
                    LinkTarget t = l.to();
                    String lb = t.name().toString();
                    if (!visited[Integer.parseInt(lb)]) {
                        currentpath.add(Integer.parseInt(lb));
                        if(size!=0)
                        {
                            break;
                        }
                        search(lb, dst, visited, currentpath);
                        currentpath.remove(currentpath.size() - 1);
                    }
                }
                exist = false;
                visited[Integer.parseInt(src)] = false;




                return false;

            }

        }
        public static class dfsStrategy implements searchalgo
        {
            public static int size =0;
            public dfsStrategy(int n) {
                visiteddfs = new boolean[n];
                path = new ArrayList<>();

            }
            public boolean search(String src, String dst, boolean[] visited, ArrayList<Integer> currentpath)
            {
                visiteddfs[Integer.parseInt(src)] = true;

                if (currentpath.size() == 0) {
                    currentpath.add(Integer.parseInt(src));
                }


                if (Integer.parseInt(src) == Integer.parseInt(dst)) {
                    exist = true;
                    return true;
                }
                MutableNode n1 = getNode(src);
                List<Link> x = n1.links();
                for (Link l : x) {
                    LinkTarget t = l.to();
                    String lb = t.name().toString();
                    currentpath.add(Integer.parseInt(lb));

                    //refactor 5 using Extract variable
                    boolean check = !visiteddfs[Integer.parseInt(lb)] && search(lb, dst, visited, currentpath);
                    if (check) {
                        path = currentpath;
                        exist = true;
                        size = path.size();

                        System.out.println(path);
                        if(size!=0)
                        {
                            break;
                        }
                        return true;
                    }

                    currentpath.remove(currentpath.size() - 1);

                }

                exist = false;


                return false;

            }

        }

    }

    //BFS and DFS implementation using the Template pattern design principle

    public static class bfsClass extends Path_Template {

        public bfsClass(int n) {
            visiteddfs = new boolean[n];
            path = new ArrayList<>();

        }

        @Override
        public boolean BFSorDFS(String src, String dst, boolean[] visited, ArrayList<Integer> currentpath) {

            if (Integer.parseInt(src) == Integer.parseInt(dst)) {
                exist = true;
                path = new ArrayList<>();
                path.addAll(currentpath);
                return true;
            }
            visited[Integer.parseInt(src)] = true;
            MutableNode n1 = getNode(src);
            List<Link> x = n1.links();
            for (Link l : x) {
                LinkTarget t = l.to();
                String lb = t.name().toString();
                if (!visited[Integer.parseInt(lb)]) {
                    currentpath.add(Integer.parseInt(lb));
                    BFSorDFS(lb, dst, visited, currentpath);
                    currentpath.remove(currentpath.size() - 1);
                }
            }
            exist = false;
            visited[Integer.parseInt(src)] = false;

            return false;
        }

    }

    public static class dfsClass extends Path_Template {

        public dfsClass(int n) {
            visiteddfs = new boolean[n];
            path = new ArrayList<>();

        }


        @Override
        public boolean BFSorDFS(String src, String dst, boolean[] visited, ArrayList<Integer> currentpath) {
            visiteddfs[Integer.parseInt(src)] = true;

            if (currentpath.size() == 0) {
                currentpath.add(Integer.parseInt(src));
            }


            if (Integer.parseInt(src) == Integer.parseInt(dst)) {
                exist = true;
                return true;
            }
            MutableNode n1 = getNode(src);
            List<Link> x = n1.links();
            for (Link l : x) {
                LinkTarget t = l.to();
                String lb = t.name().toString();
                currentpath.add(Integer.parseInt(lb));
                //refactor 5 using Extract variable
                boolean check = !visiteddfs[Integer.parseInt(lb)] && BFSorDFS(lb, dst, visited, currentpath);
                if (check) {
                    path = currentpath;
                    exist = true;
                    return true;
                }
                currentpath.remove(currentpath.size() - 1);
            }
            exist = false;
            return false;


        }

    }

    public static class randomWalkClass extends PavanGraph.Path_Template {


        public ArrayList<String> final_path;
        public String printString;
        public String source;

        public randomWalkClass(int n, String src) {
            visiteddfs = new boolean[n];
            path = new ArrayList<>();
            source = src;
            final_path = new ArrayList<String>();
            final_path.add(src);
            System.out.println("random testing");
            printString = "visiting Path{nodes=[Node{a}]}";
            System.out.println(printString);
        }




        @Override
        public boolean BFSorDFS(String src, String dst, boolean[] visited, ArrayList<Integer> currentpath) {

            char sc = src.charAt(0);
            int source_index = sc - 'a'  + 1;
            char dt = dst.charAt(0);
            int dest_index = dt - 'a'  + 1;
            if(src == dst){
                exist = true;
                return true;
            }

            while(!final_path.contains(dst)){

                //exit condition
                //clear the path
                //restart visited from scratch
                MutableNode source_node = getNode(src);
                List<Link> nodes_linked = source_node.links();
                Random random = new Random();
                int n = nodes_linked.size();
                int random_increment = -1;
                // Generate a random number within the range [0, n+1)
                if(n>0){
                    random_increment = random.nextInt(n);
                }
                else break;

//                int random_increment = random.nextInt(n);

                Link new_node = nodes_linked.get(random_increment);
                LinkTarget t = new_node.to();
                String lb= t.name().toString();

                if(final_path.contains(lb)){
                    final_path = new ArrayList<String>();
                    System.out.println("Cycle found, rejecting existing path");
                    lb = source;
                }

                final_path.add(lb);


                //priinting path code
                printString = "";
                String pre_temp = "";
                for(int i = 0; i < final_path.size(); i++){
                    String temp = "";
                    temp = "Node{" + final_path.get(i) + "}";
                    if(i!=0)
                        pre_temp = pre_temp + ", " + temp;
                    else
                        pre_temp = temp;

                }
                printString = "visiting Path{nodes=[" + pre_temp + "]}";
                System.out.println(printString);



                if(final_path.contains(dst)){
                    break;
                }
                else
                    BFSorDFS(lb,dst, visited, currentpath);

            }
            return exist;
        }

    }



    public static Path_Template helper(int n, String src, Path_Template.Level l)
    {
        Path_Template p;
        if(l == Path_Template.Level.DFS)
        {
            p = new dfsClass(n);

        }
        else if(l == PavanGraph.Path_Template.Level.BFS){
            p = new bfsClass(n);
        }
        else{
            p = new randomWalkClass(n, src);
        }

        return p;
    }

    public static Path_Template.searchalgo helper_strat(int n , Path_Template.Level l)
    {
        PavanGraph.Path_Template.searchalgo obj;
        if(l == Path_Template.Level.DFS)
        {
            obj = new Path_Template.dfsStrategy(n);
        }
        else{
            obj = new Path_Template.bfsStrategy();
        }
        return obj;
    }


    public static void main(String[] args) {
        PavanGraph graphObject = new PavanGraph();
        try {
            String filename = "input_canvas.dot";

            String[] set_of_labels = new String[] {"2", "3"};
            graphObject.parseGraph(filename);
//
//            graphObject.addNode("11");
//            graphObject.addNodes(set_of_labels);
//
//            graphObject.addEdge("6", "11");
//            graphObject.addEdge("5", "11");
//            graphObject.addEdge("11", "12");
//            graphObject.addEdge("7", "11");
//            graphObject.addEdge("9", "13");
//            graphObject.addEdge("9", "14");
//            graphObject.addEdge("13", "10");
//            graphObject.addEdge("14", "10");






            graphObject.outputDOTGraph("output.dot");
            graphObject.outputGraphics("output2.png", "png");

            int n = graphObject.graph.nodes().size();


//            // strategy pattern implementation
           boolean[] visited = new boolean[1000];
            ArrayList<Integer> temp = new ArrayList<>();
//            temp.add(Integer.parseInt("a"));
//            PavanGraph.Path_Template.searchalgo obj = helper_strat(n,Path_Template.Level.DFS);
//            boolean b = obj.search("0","9",visited,temp);



            Path_Template p = helper(n,"a",Path_Template.Level.RWS);
            p.BFSorDFS("a","h",visited,temp);






//            graphObject.parseGraph("output.dot");
//            graphObject.outputGraphics("output2.png", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



