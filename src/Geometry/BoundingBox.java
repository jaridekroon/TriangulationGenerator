package Geometry;

import Geometry.Native.Edge;
import Geometry.Native.Vertex;

public class BoundingBox {
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;
    private Vertex v4;

    private double boundingBoxHeight;
    private double boundingBoxWidth;

    public BoundingBox(double boundingBoxHeight, double boundingBoxWidth){

        //bounding box vertices
        v1 = new Vertex(0.0, 0.0 , -6);                      //0,0
        v2 = new Vertex(0.0, boundingBoxHeight, -7);            //0,h
        v3 = new Vertex(boundingBoxWidth, 0.0, -8);             //w,0
        v4 = new Vertex(boundingBoxWidth, boundingBoxHeight, -9);  //w,h

        this.boundingBoxHeight = boundingBoxHeight;
        this.boundingBoxWidth = boundingBoxWidth;

    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public Vertex getV3() {
        return v3;
    }

    public Vertex getV4() {
        return v4;
    }

    public double getHeight() {
        return boundingBoxHeight;
    }

    public double getWidth() {
        return boundingBoxWidth;
    }

    public boolean onBoundingBoxEdge(Vertex v){
        return v.getX() == 0 || v.getY() == 0 || v.getX() == this.boundingBoxWidth || v.getY() == this.boundingBoxHeight;
    }

    public Edge[] boundingBoxEdges(){
        return new Edge[]{
                new Edge(v1,v2),
                new Edge(v1,v3),
                new Edge(v2,v4),
                new Edge(v3,v4)
        };
    }

    public boolean inBoundingBox(Vertex v) {
        if(v.getX() >= 0 && v.getX() <= boundingBoxWidth && v.getY() >= 0 && v.getY() <= boundingBoxHeight) {
            return true;
        }
        return false;
    }
}
