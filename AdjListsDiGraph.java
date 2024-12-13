import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * AdjListsDiGraph.java
 * Implements the DiGraph Interface.
 * Uses a Vector of LinkedLists to keep track of the adjacent vertices.
 * 
 * @author (SK)
 * @version (2022.11.07) TM added saveToTGF code, moved toString() to GIVEN section
 */

public class AdjListsDiGraph<T> implements DiGraph<T>
{
    private ArrayList<T> vertices; //Vector to hold the vertices in the graph
    private ArrayList<LinkedList<T>> arcs;   // Lists of adjacent vertices

    /**
     * Constructor. Creates an empty graph.
     * 
     */
    public AdjListsDiGraph() {
        this.arcs = new ArrayList<LinkedList<T>>();
        this.vertices = new ArrayList<T>();
    }

    /**
     * Creates and returns a new graph of Strings using the data found
     * in the input tgf file.
     * If the file does not exist, a message is printed.
     *
     * @param String The name of the tgf file to read the graph from
     * @return AdjListsDiGraph<String> the graph of Strings created
     */
    public static AdjListsDiGraph<String> AdjListsGraphFromFile(String tgf_fName) {
        //create an empty graph of Strings
        AdjListsDiGraph<String> g = new AdjListsDiGraph<String>();

        try{
            Scanner scanner = new Scanner(new File(tgf_fName));

            //read vertices
            while (!scanner.next().equals("#")){ //discard special symbol (#)
                String token = scanner.next(); //read next String
                g.addVertex(token); //add it a vertex to the graph
            }

            //read arcs
            while (scanner.hasNext()){
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                g.addArc(from-1, to-1); //uses helper method
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println(" ***ERROR***  " +  tgf_fName + " file could not be read. ");
        }
        return g;
    }

    /**
     * Helper. Inserts an arc between two vertices of the graph.
     *
     * @param int The index of the first vertex in the vertices vector (starting from 0)
     * @param int The index of the second vertex in the vertices vector (starting from 0)
     */
    private void addArc (int index1, int index2) {
        T source = vertices.get(index1);
        T destination = vertices.get(index2);
        addArc(source, destination);
    }

    /**
     * Saves the current graph into a .tgf file.
     * @param the name of the file to write to
     */
    public void saveToTGF(String fName) {
        try {
            PrintWriter writer = new PrintWriter(new File(fName));
            //notice that indexing in the tgf format starts at 1 (not 0)

            //write vertices by iterating through vector "vertices"
            for (int i = 0; i < vertices.size(); i++) {
                writer.print((i+1) + " " + vertices.get(i));
                writer.println("");
            }
            writer.println("#"); // # symbol separates the vertices from the arcs

            //write arcs by iterating through arcs vector
            for (int i = 0; i < arcs.size(); i++){ //for each adjacent list
                for (T destinationVertex :arcs.get(i)) { //for each destination vertex in that list
                    int destinationIndex = vertices.indexOf(destinationVertex); //find the index of that vertex
                    writer.println((i+1) + " " + (destinationIndex+1));
                }
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("***ERROR***" +  fName + " could not be written");
        }
    }        

    /**
     *  Returns a string representation of the graph.
     *
     *  @return String a string representation of this graph
     */
    public String toString() {
        if (vertices.size() == 0) return "Graph is empty";

        String result = "Vertices: \n";
        result = result + vertices;

        result = result + "\n\nArcs: \n";
        for (int i=0; i< vertices.size(); i++)
            result = result + "from " + vertices.get(i) + ": "  + arcs.get(i) + "\n";

        return result;
    }
    // END OF GIVEN

    // START OF LAB
    /**
     * Returns true if the graph is empty and false otherwise.
     * @return boolean True iff the graph is empty
     * 
     */
    public boolean isEmpty() {
        return (getNumVertices() == 0 && getNumArcs() == 0);
    }

    /**
     * Returns the number of vertices in the graph
     * @return int The number of vertices in the graph
     * 
     */
    public int getNumVertices() {
        return vertices.size();
    }

    /**
     * Returns the number of arcs in the graph
     * @return int The number of arcs in the graph
     * 
     */
    public int getNumArcs() {
        return arcs.size();
    }

    /**
     * Adds the input vertex to the graph.
     * If the vertex already exists in the graph, the graph is not changed.
     * @param T the vertex to be added to the graph
     * 
     */
    public void addVertex (T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            arcs.add(new LinkedList<T>());
        }
    }

    /**
     * Adds an arc to the graph, from source to destination.
     * If source or destination do not exist in the graph,
     * the graph is not changed.
     * Verifies that source and destination are valid vertices in the graph,
     * and that the newly added arc does not already belong in the graph.
     *
     * @param T the source of the arc
     * @param T the destination of the arc
     * 
     */
    public void addArc(T source, T destination) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        // If either vertex does not exist, return without making any changes
        if (sourceIndex == -1 || destinationIndex == -1) {
            return;
        }

        // Ensure there is no duplicate arc
        LinkedList<T> sourceArcs = arcs.get(sourceIndex);
        if (!sourceArcs.contains(destination)) {
            // Add the destination to the source's adjacency list
            sourceArcs.add(destination);
        }
    }

    // END OF LAB
    // START OF ASSIGNMENT
    /**
     * Returns true iff a directed connection exists between the two input vertices
     * @param T the first vertex
     * @param T the second vertex
     * @return boolean true iff a directed connection
     * exists from the first vertex to the second
     */
    public boolean isArc (T v1, T v2){
        return false;
    }

    /**
     * Removes the input vertex from the graph.
     * If the input vertex does not belong in the graph, the graph is not changed.
     * Uses equals() for identidying the vertex to be removed.
     * @param T The vertex to be removed.
     */
    public void removeVertex (T vertex) {

    }

    /**
     * Removes the arc between v1 and v2.
     * If v1 or v2, or the arc from v1 to v2 does not exist,
     * the graph does not change.
     *
     * @param T the source of the arc to be removed
     * @param T the destination of the arc to be removed
     */
    public void removeArc (T v1, T v2) {

    }
    // END OF ASSIGNMENT

    /**
     * Very Basic Driver program.
     */

    public static void main (String args[]){
        System.out.println("TESTING METHODS");
        System.out.println("_________________");
        AdjListsDiGraph<String> g = new AdjListsDiGraph<String>();
        System.out.println("New graph created.");
        System.out.println("isEmpty() Expect true. Got: " + g.isEmpty());

        System.out.println("Testing addVertex()");
        g.addVertex("A"); g.addVertex("B");
        g.addVertex("C"); g.addVertex("D");
        g.addVertex("A"); //try adding existing vertex

        System.out.println("isEmpty() Expect false. Got: " + g.isEmpty());
        System.out.println("Printing graph with 4 verices and no arcs. \nGot:\n" + g);

        System.out.println("Testing addArc()");
        g.addArc("A", "B");
        g.addArc("B", "A");
        g.addArc("A", "C");
        g.addArc("C", "B");
        g.addArc("C", "Z"); //invalid destination
        g.addArc("C", "B"); //try adding existing arc
        System.out.println("Printing graph with vertices [A, B, C, D) and arcs: A->B, A->C, B->A, C->B. \nGot:\n" + g);

        System.out.println("Testing removeVertex()");
        g.removeVertex("K"); //try remove invalid vertex
        System.out.println("try remove invalid vertex");
        System.out.println("Expect vertices [A, B, C, D] and arcs: A->B, A->C, B->A, C->B. \nGot:");
        System.out.println(g);

        g.removeVertex("A"); //try remove valid vertex
        System.out.println("try remove valid vertex A w 2 arcs");
        System.out.println("Expect vertices [B, C, D] and arcs: C->B. \nGot:");
        System.out.println(g);

        System.out.println("try remove vertex D w no arcs");
        System.out.println("Expect vertices [B, C] and arcs: C->B. \nGot:");
        g.removeVertex("D");
        System.out.println(g);

        System.out.println("try remove vertex from empty graph");
        System.out.println("Expect vertices [] and arcs: . \nGot:");
        g.removeVertex("B");
        g.removeVertex("C");
        g.removeVertex("C");
        System.out.println(g);

        g.saveToTGF("out.tgf");
    }
}
