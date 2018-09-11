package Geometry;

import Geometry.Native.Triangle;
import Geometry.Native.Vertex;

/**
 * Holds geometrical triangle data, a reference to a DAGtriangle and a delauny triangle
 */
public class TriangleCarrier extends Triangle {
    protected TriangleDAG triangleDAG;

    public TriangleCarrier(Vertex a, Vertex b, Vertex c){
        super(a,b,c);
    }

    public TriangleDAG getTriangleDAG() {
        return triangleDAG;
    }

    public void setTriangleDAG(TriangleDAG triangleDAG) {
        this.triangleDAG = triangleDAG;
    }

    @Override
    public String toString(){
        return String.format("T(a: %d, b: %d, c: %d)", this.getA().getID(), this.getB().getID(), this.getC().getID());
    }
}
