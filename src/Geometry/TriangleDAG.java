package Geometry;

import Geometry.Native.Point;
import Geometry.Native.Vertex;
import Graphs.DAGNode;

/**
 * Directed acyclic graph representing a triangulation.
 * TriangleDag can be used maintained using flipEdge and split and be queried using findPoint
 */
public class TriangleDAG extends DAGNode<TriangleCarrier> {
    private final boolean debug = false;

    public TriangleDAG(TriangleCarrier data){
        super(data);
        data.triangleDAG = this;
    }

    public TriangleDAG findPoint(Vertex p){
        if(this.data.isInside(p)){
            if(debug) System.out.println(this.data.getA().getID() + "," + this.data.getB().getID() + "," + this.getData().getC().getID());
            if(debug) System.out.println("x: " + p.getX() + " ,y: " + p.getY() + ", id:" + p.getID());
            if(isLeaf())
                return this;
            for(DAGNode<TriangleCarrier> child : children){
                TriangleDAG t = (TriangleDAG)child;
                TriangleDAG tt = t.findPoint(p);
                if(tt != null)
                    return tt;
            }
            // This should never happen, if a point is inside the triangle it must be inside one of the subtriangles
            System.out.println("Not in any child! --> should never happen.");
            assert(false);
        } else if(this.isRoot()) {
            System.out.println("Vertex not inside root --> should never happen.");
            assert(false);
        }

        return null;
    }
}
