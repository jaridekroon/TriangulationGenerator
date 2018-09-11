package InputGenerators;

import Geometry.BoundingBox;
import Geometry.Native.Vertex;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Generates random input vertices within some given bounding box
 */
public class RandomInputGenerator implements InputGenerator {

    private BoundingBox boundingBox;
    private int nrOfVerticesP1;
    private int nrOfVerticesP2;


    public RandomInputGenerator(BoundingBox bb, int P1Vertices, int P2Vertices){
        this.boundingBox = bb;
        this.nrOfVerticesP1 = P1Vertices;
        this.nrOfVerticesP2 = P2Vertices;
    }

    /**
     * Generate a point set for both players.
     * @return Vertex array with all the generated points
     */
    @Override
    public Vertex[] generate() {
        return concatenate(GenerateRandomPlayer1(),GenerateRandomPlayer2());
    }

    public Vertex[] GenerateRandomPlayer1(){
        return GenerateRandomVertices(1, this.nrOfVerticesP1, 0);
    }

    public Vertex[] GenerateRandomPlayer2(){
        return GenerateRandomVertices(2, this.nrOfVerticesP2, this.nrOfVerticesP1);
    }

    /**
     * Create and return an array of n unique random vertices with incremental ID's starting from a given integer.
     * @param playerID  Which player to generate for (1 or 2)
     * @param nrOfVertices number of vertices to generate
     * @param startID ID of the first generated vertex
     * @return Array of all generated vertices.
     */
    private Vertex[] GenerateRandomVertices(int playerID, int nrOfVertices, int startID){
        Random rand = new Random();
        ArrayList<Vertex> list = new ArrayList<>();
        Vertex[] random = new Vertex[nrOfVertices];
        for(int i = 0; i < nrOfVertices; i++) {
            Vertex v;
            do {
                v = new Vertex(rand.nextInt((int) boundingBox.getWidth()), rand.nextInt((int) boundingBox.getHeight())
                        , startID, playerID);
                random[i] = v;
                startID++;
            } while(duplicateCoords(list.toArray(new Vertex[list.size()]), new Vertex[]{v}));
            list.add(v);
        }
        return random;

    }

    /**
     * Concatenate two arrays into a new array of length n + m
     * @param a array 1
     * @param b array 2
     * @param <T> Array types
     * @return concatenated array
     */
    public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    /**
     * Check the given Vertex arrays on duplicate coordinates
     * @param a array 1
     * @param b array 2
     * @return True if any coordinates are duplicates, false otherwise.
     */
    public boolean duplicateCoords(Vertex[] a, Vertex[] b) {
        for(Vertex v : a) {
            for(Vertex vv : b) {
                if(v.getX() == vv.getX() && v.getY() == vv.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

}
