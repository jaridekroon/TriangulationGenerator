package Graphs;

/**
 * Thrown when the integrity of a directed acyclic graph is violated
 */
public class DAGViolation extends Exception {
    public DAGViolation(String msg)
    {
        super(msg);
    }
}
