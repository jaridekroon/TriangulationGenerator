package Geometry.Native;

import java.util.ArrayList;


public class Triangle {
    private final double epsilon = Math.pow(10, -6);
    private Vertex a;
    private Vertex b;
    private Vertex c;

    private int ID;

    public Triangle(Vertex a, Vertex b, Vertex c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Triangle(Vertex a, Vertex b, Vertex c, int ID) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Vertex getA() {
        return this.a;
    }

    public Vertex getB() {
        return this.b;
    }

    public Vertex getC() {
        return this.c;
    }

    public Vertex[] getAllVertices(){
        return new Vertex[]{this.getA(), this.getB(), this.getC()};
    }

    public boolean contains(Vertex vertex){
        return vertex.equals(this.getA()) || vertex.equals(this.getB()) || vertex.equals(this.getC());
    }

    public Vertex findThirdVertex(Vertex v1, Vertex v2){
        if((v1.equals(this.getA()) && v2.equals(this.getB())) || (v2.equals(this.getA()) && v1.equals(this.getB()))){
            return this.getC();
        }
        else if((v1.equals(this.getB()) && v2.equals(this.getC())) || (v2.equals(this.getB()) && v1.equals(this.getC()))){
            return this.getA();
        }
        else if((v1.equals(this.getA()) && v2.equals(this.getC())) || (v2.equals(this.getA()) && v1.equals(this.getC()))){
            return this.getB();
        }
        else {
            return null;
        }
    }

    public boolean isInside(Vertex p){
        // if point p on the same side of all three edges of the triangle, then it is inside
        boolean b1, b2, b3;
        double d1 = sign(p, this.a, this.b);
        double d2 = sign(p, this.b, this.c);
        double d3 = sign(p, this.c, this.a);

        b1 = d1 <= 0.0d;
        b2 = d2 <= 0.0d;
        b3 = d3 <= 0.0d;

        boolean b4, b5, b6;
        b4 = d1 >= 0.0d;
        b5 = d2 >= 0.0d;
        b6 = d3 >= 0.0d;

        return ((b1 == b2) && (b2 == b3)) || ((b4 == b5) && (b5 == b6));
    }

    public boolean isStrictlyInside(Vertex p){
        // if point p on the same side of all three edges of the triangle, then it is inside
        Vertex p1 = this.a;
        Vertex p2 = this.b;
        Vertex p3 = this.c;

        double alpha = ((p2.getY() - p3.getY())*(p.getX() - p3.getX()) + (p3.getX() - p2.getX())*(p.getY() - p3.getY())) /
                ((p2.getY() - p3.getY())*(p1.getX() - p3.getX()) + (p3.getX() - p2.getX())*(p1.getY() - p3.getY()));
        double beta = ((p3.getY() - p1.getY())*(p.getX() - p3.getX()) + (p1.getX() - p3.getX())*(p.getY() - p3.getY())) /
                ((p2.getY() - p3.getY())*(p1.getX() - p3.getX()) + (p3.getX() - p2.getX())*(p1.getY() - p3.getY()));
        double gamma = 1.0d - alpha - beta;

        return (alpha > 0.0d && beta > 0.0d && gamma > 0.0d);
    }

    /** Check on which side of half plane p1 is with respect to edge (p2,p3), pos means left */
    //todo write test cases
    public double sign(Vertex p1, Vertex p2, Vertex p3) {
        if(p2.getID() == -1 && p3.getID() == -2) {
            return -1;
        } else if (p2.getID() == -2 && p3.getID() == -1) {
            return 1;
        } else if (p2.getID() == -1) {
            if(p1.getY() > p3.getY() || (p1.getY() == p3.getY() && p1.getX() < p3.getX())) {
                return -1;
            } else {
                return 1;
            }
        } else if (p2.getID() == -2) {
            if(p1.getY() > p3.getY() || (p1.getY() == p3.getY() && p1.getX() < p3.getX())) {
                return 1;
            } else {
                return -1;
            }
        } else if (p3.getID() == -1) {
            if(p1.getY() > p2.getY() || (p1.getY() == p2.getY() && p1.getX() < p2.getX())) {
                return 1;
            } else {
                return -1;
            }
        } else if (p3.getID() == -2) {
            if(p1.getY() > p2.getY() || (p1.getY() == p2.getY() && p1.getX() < p2.getX())) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
        }
    }

    @Override
    public boolean equals(Object o) {
        Triangle t = (Triangle) o;

        Vertex[] currentTriangle = {this.getA(), this.getB(), this.getC()};
        Vertex[] queryTriangle = {t.getA(), t.getB(), t.getC()};

        int matchCount = 0;
        for (Vertex v : currentTriangle) {
            for (Vertex u : queryTriangle) {
                if (v.equals(u)) {
                    matchCount++;
                }
            }
        }
        return (matchCount == 3);
    }
}
