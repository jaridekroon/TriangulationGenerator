package Geometry.Native;

import Geometry.TriangleCarrier;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Edge {
    private Vertex first;
    private Vertex second;

    /** Edge has list of incident triangles */
    private ArrayList<TriangleCarrier> triangles;

    public Edge(Vertex first, Vertex second) {
        this.first = first;
        this.second = second;
        triangles = new ArrayList<TriangleCarrier>();
    }

    public Vertex getFirst() {
        return this.first;
    }

    public Vertex getSecond() {
        return this.second;
    }

    public ArrayList<TriangleCarrier> getTriangles() {
        return this.triangles;
    }

    public void addIncidentTriangle(TriangleCarrier t) {
        if (!this.triangles.contains(t)) {
            this.triangles.add(t);
        }
    }

    public boolean contains(Vertex v){
        return v.equals(this.getFirst()) || v.equals(this.getSecond());
    }

    public void removeIncidentTriangle(TriangleCarrier t) {
        this.triangles.remove(t);
    }

    @Override
    public int hashCode() {
        return Math.min(first.getID(), second.getID()) * 20000 + Math.max(first.getID(), second.getID());
    }

    @Override
    public boolean equals(Object o) {
        Edge l = (Edge) o;
        return (this.getFirst().equals(l.getFirst()) && this.getSecond().equals(l.getSecond()))
                || (this.getSecond().equals(l.getFirst()) && this.getFirst().equals(l.getSecond()));

    }

    @Override
    public String toString(){
        return String.format("(%d, %d)", this.first.getID(), this.second.getID());
    }

    public Vertex intersection(Edge s) throws InvalidParameterException {
        int x1 = (int)this.first.getX();
        int y1 = (int)this.first.getY();
        int x2 = (int)this.second.getX();
        int y2 = (int)this.second.getY();
        int x3 = (int)s.getFirst().getX();
        int y3 = (int)s.getFirst().getY();
        int x4 = (int)s.getSecond().getX();
        int y4 = (int)s.getSecond().getY();

        //if both lines vertical and x1 != x3, no intersection
        if(x1 == x2 && x3 == x4) {
            if (x1 != x3) {
                // no intersection
                return null;
            } else if ((Math.min(y1,y2) < y3 && y3 < Math.max(y1,y2)) || (Math.min(y1,y2) < y4 && y4 < Math.max(y1,y2))
                    || (Math.min(y3,y4) < y1 && y1 < Math.max(y3,y4)) || (Math.min(y3,y4) < y2 && y2 < Math.max(y3,y4))){
                // segments overlap, throw exception to catch (we do not want these types of intersections)
                throw new InvalidParameterException("Both vertical with overlap.");
            }
        }

        //first line vertical
        if(x1 == x2) {
            //y2 = a2*x + b2
            double a2 = (y4 - y3)/(x4-x3);
            double b2 = y3 - a2*x3;
            //fill in x1
            double in = a2*x1 + b2;
            //check of intersection within both line segments
            if(Math.min(x3,x4) < x1 && x1 < Math.max(x3,x4)) {
                //intersection point!
                return new Vertex(x1,in);
            } else {
                // no intersection
                return null;
            }
        }

        //second line vertical
        if(x3 == x4) {
            //y1 = a1*x + b1
            double a1 = (y2 - y1)/(x2-x1);
            double b1 = y1 - a1*x1;
            //fill in x3
            double in = a1*x3 + b1;
            //check of intersection within both line segments
            if(Math.min(x1,x2) < x3 && x3 < Math.max(x1,x2)) {
                //intersection point!
                return new Vertex(x3,in);
            } else {
                //no intersection point
                return null;
            }
        }

        //neither vertical
        double a1 = (y2-y1)/(x2-x1);
        double b1 = y1 - a1*x1;
        double a2 = (y4-y3)/(x4-x3);
        double b2 = y3 - a2*x3;
        if((y2-y1)*(x4-x3) == (x2-x1)*(y4-y3)) {
            //same slope
            if(b1 == b2) {
                //same intercept
                if ((Math.min(x1,x2) < x3 && x3 < Math.max(x1,x2)) || (Math.min(x1,x2) < x4 && x4 < Math.max(x1,x2))
                        || (Math.min(x3,x4) < x1 && x1 < Math.max(x3,x4)) || (Math.min(x3,x4) < x2 && x2 < Math.max(x3,x4))) {
                    //overlap, throw exception to catch (we do not want these types of intersections)
                    throw new InvalidParameterException("Same slope same intercept with overlap.");
                } else {
                    //no intersection
                    return null;
                }
            } else {
                //no intersection
                return null;
            }
        }

        //intersection point solution to y = a1*x + b1 and y = a2*x + b2
        double x0 = -(b1-b2)/(a1-a2);
        if(Math.min(x1, x2) < x0 && x0 < Math.max(x1, x2) && Math.min(x3, x4) < x0 && x0 < Math.max(x3, x4)) {
            //intersection point!
            return new Vertex(x0,a1*x0+b1);
        }
        //no intersection
        return null;
    }
}
