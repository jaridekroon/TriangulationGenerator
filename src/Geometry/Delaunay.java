package Geometry;

import Geometry.Native.Edge;
import Geometry.Native.Triangle;
import Geometry.Native.Vertex;
import Graphs.DAGViolation;
import Visualization.SVGVisualizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Delaunay {
    private Vertex[] vertices;
    private BoundingBox boundingBox;

    private final double epsilon = Math.pow(10, -6);

    private final boolean debug = false;
    private Triangulation tri;

    public Delaunay(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public Delaunay(Vertex[] vertices, BoundingBox boundingBox) {
        this.vertices = vertices;
        this.boundingBox = boundingBox;
    }

    public Delaunay(Vertex[] vertices, BoundingBox boundingBox,Triangulation triangulation) {
        this.vertices = vertices;
        this.boundingBox = boundingBox;
        this.tri = triangulation;
    }

    public Triangulation triangulate() {
        /** Compute large initial triangle */
        TriangleCarrier root = createLargeTriangle();

        /** Compute random order on input */
        Vertex[] permutation = shuffle(vertices);

        /** Initialize Triangulation T with large triangle*/
        Triangulation T = new Triangulation();
        T.addTriangle(root);

        /** Initialize point location structure*/
        TriangleDAG D = new TriangleDAG(root);

        /** Set carrier reference */
        root.setTriangleDAG(D);

        for(int i = 0; i < permutation.length; i++) {
            printIncident(T);
            Vertex p = permutation[i];
            if(debug) System.out.println("Inserting Vertex " + p.getID());
            if (!T.getVertices().contains(p)) {
                TriangleDAG t = D.findPoint(p);
                TriangleCarrier tc = t.getData();

                if(debug) System.out.println(onEdge(tc,p));
                if (!onEdge(tc,p)) {
                    Vertex a = tc.getA();
                    Vertex b = tc.getB();
                    Vertex c = tc.getC();

                    TriangleCarrier new1 = new TriangleCarrier(a, b, p);
                    TriangleCarrier new2 = new TriangleCarrier(b, c, p);
                    TriangleCarrier new3 = new TriangleCarrier(c, a, p);

                    TriangleDAG newNode1 = new TriangleDAG(new1);
                    TriangleDAG newNode2 = new TriangleDAG(new2);
                    TriangleDAG newNode3 = new TriangleDAG(new3);

                    new1.setTriangleDAG(newNode1);
                    new2.setTriangleDAG(newNode2);
                    new3.setTriangleDAG(newNode3);

                    try {
                        t.addChild(newNode1);
                        t.addChild(newNode2);
                        t.addChild(newNode3);
                    } catch (DAGViolation v) {
                        System.out.println("Cannot add child");
                    }

                    T.removeTriangle(tc);
                    T.addTriangle(new1);
                    T.addTriangle(new2);
                    T.addTriangle(new3);

                    /** In order to generator triangulation, we do not legalize */
                    //legalizeEdge(p, T.findEdgeByVertices(tc.getA(),tc.getB()),T);
                    //legalizeEdge(p, T.findEdgeByVertices(tc.getB(),tc.getC()),T);
                    //legalizeEdge(p, T.findEdgeByVertices(tc.getC(),tc.getA()),T);

                } else {
                    Edge ij = findOnEdge(tc,p,T);
                    List<TriangleCarrier> incident = ij.getTriangles();
                    TriangleCarrier other = null;
                    for (TriangleCarrier tt : incident) {
                        if (tt != tc) {
                            other = tt;
                            break;
                        }
                    }
                    if (other != null) {
                        Vertex l = tc.findThirdVertex(ij.getFirst(), ij.getSecond());
                        Vertex k = other.findThirdVertex(ij.getFirst(), ij.getSecond());

                        TriangleCarrier new1 = new TriangleCarrier(l, ij.getFirst(), p);
                        TriangleCarrier new2 = new TriangleCarrier(l, ij.getSecond(), p);
                        TriangleCarrier new3 = new TriangleCarrier(k, ij.getFirst(), p);
                        TriangleCarrier new4 = new TriangleCarrier(k, ij.getSecond(), p);

                        TriangleDAG newNode1 = new TriangleDAG(new1);
                        TriangleDAG newNode2 = new TriangleDAG(new2);
                        TriangleDAG newNode3 = new TriangleDAG(new3);
                        TriangleDAG newNode4 = new TriangleDAG(new4);

                        new1.setTriangleDAG(newNode1);
                        new2.setTriangleDAG(newNode2);
                        new3.setTriangleDAG(newNode3);
                        new4.setTriangleDAG(newNode4);

                        try {
                            t.addChild(newNode1);
                            t.addChild(newNode2);
                            other.getTriangleDAG().addChild(newNode3);
                            other.getTriangleDAG().addChild(newNode4);
                        } catch (DAGViolation v) {
                            System.out.println("Cannot add child");
                        }

                        T.removeTriangle(tc);
                        T.removeTriangle(other);
                        T.addTriangle(new1);
                        T.addTriangle(new2);
                        T.addTriangle(new3);
                        T.addTriangle(new4);

                        /** In order to generator triangulation, we do not legalize */
                        //legalizeEdge(p, T.findEdgeByVertices(l, ij.getFirst()),T);
                        //legalizeEdge(p, T.findEdgeByVertices(l, ij.getSecond()),T);
                        //legalizeEdge(p, T.findEdgeByVertices(k, ij.getFirst()),T);
                        //legalizeEdge(p, T.findEdgeByVertices(k, ij.getSecond()),T);

                    } else {
                        System.out.println("NO OTHER TRIANGLE, SHOULD NEVER HAPPEN");
                    }
                }
            }
        }

        //remove negative edges
        ArrayList<Edge> copy = new ArrayList<>(T.getEdges());
        for (Edge e : copy) {
            if(e.getFirst().getID() < 0 || e.getSecond().getID() < 0) {
                T.removeEdge(e);
            }
        }
        //remove negative triangles
        ArrayList<TriangleCarrier> copy2 = new ArrayList<>(T.getTriangles());
        for (TriangleCarrier t : copy2) {
            if(t.getA().getID() < 0 || t.getB().getID() < 0 || t.getC().getID() < 0) {
                T.removeTriangle(t);
            }
        }
        //remove negative vertices
        ArrayList<Vertex> copy3 = new ArrayList<>(T.getVertices());
        for (Vertex v : copy3) {
            if(v.getID() < 0) {
                T.removeVertex(v);
            }
        }

        int ID = 0;
        for(TriangleCarrier t: T.getTriangles()){
            t.setID(ID);
            ID++;
        }

        this.tri = T;
        return T;
    }

    public int countLegalize() {
        /** Compute number of legalize edge flips needed based on lexico id*/
        int count = 0;
        boolean flipFound = true;
        while(flipFound) {
            flipFound = false;
            List<Edge> li = new ArrayList<>(tri.getEdges());
            Collections.sort(li, (a, b) -> a.getFirst().getID() < b.getFirst().getID() ? -1
                    : a.getFirst().getID() > b.getFirst().getID() ? 1
                    : a.getSecond().getID() < b.getSecond().getID() ? -1 : 0);
            for(Edge e : li) {
                if(e.getTriangles().size() != 2) {
                    //edge is on boundary, always legal
                }
                else if(!isLegal(e,
                        e.getTriangles().get(0).findThirdVertex(e.getFirst(),e.getSecond()),
                        e.getTriangles().get(1).findThirdVertex(e.getFirst(),e.getSecond()))) {
                        count++;                //illegal edge found
                    legalizeSingleEdge(e);      //legalize the edge
                    flipFound = true;           //flip found, need another iteration of while loop
                    break;                      //redo sorting, lexicographic order might have changed
                }
            }
        }
        return count;
    }

    public Triangulation getTri() {
        return this.tri;
    }

    public TriangleCarrier createLargeTriangle() {
        Vertex p0 = new Vertex(-boundingBox.getHeight(),0,vertices.length+0,1);
        Vertex p1 = new Vertex(boundingBox.getWidth()+boundingBox.getHeight(),0,vertices.length+1,1);
        Vertex p2 = new Vertex(boundingBox.getWidth()/2,boundingBox.getHeight()+boundingBox.getWidth()/2,vertices.length+2,1);

        return new TriangleCarrier(p0,p1,p2);
    }

    public Vertex[] shuffle(Vertex[] vertices) {
        if(!debug) {
            ArrayList<Vertex> temp = new ArrayList<>();
            Collections.addAll(temp, vertices);
            Collections.shuffle(temp);
            Vertex[] result = new Vertex[vertices.length];
            temp.toArray(result);
            return result;
        }
        return vertices;
    }

    public boolean onEdge(Triangle t, Vertex p) {
        double d1 = t.sign(p,t.getA(),t.getB());
        double d2 = t.sign(p,t.getB(),t.getC());
        double d3 = t.sign(p,t.getC(),t.getA());
        return (Math.abs(d1) < epsilon || Math.abs(d2) < epsilon || Math.abs(d3) < epsilon);
    }

    public void legalizeSingleEdge(Edge ab) {
        TriangleCarrier t1 = ab.getTriangles().get(0);
        TriangleCarrier t2 = ab.getTriangles().get(1);
        Vertex h = t1.findThirdVertex(ab.getFirst(), ab.getSecond());
        Vertex p = t2.findThirdVertex(ab.getFirst(), ab.getSecond());

        TriangleDAG original1 = t1.getTriangleDAG();
        TriangleDAG original2 = t2.getTriangleDAG();

        tri.removeTriangle(t1);
        tri.removeTriangle(t2);

        TriangleCarrier new1 = new TriangleCarrier(p, h, ab.getFirst());
        TriangleCarrier new2 = new TriangleCarrier(p, h, ab.getSecond());

        TriangleDAG d1 = new TriangleDAG(new1);
        TriangleDAG d2 = new TriangleDAG(new2);
        new1.setTriangleDAG(d1);
        new2.setTriangleDAG(d2);

        try {
            original1.addChild(d1);
            original1.addChild(d2);
            original2.addChild(d1);
            original2.addChild(d2);
        } catch (DAGViolation dagViolation) {
            dagViolation.printStackTrace();
        }

        tri.addTriangle(new1);
        tri.addTriangle(new2);
    }

    public void legalizeEdge(Vertex p, Edge ab, Triangulation T) {
        printIncident(T);
        List<TriangleCarrier> incident = ab.getTriangles();
        if(debug) System.out.println(incident.size());
        if(incident.size() == 3) {
            Math.abs(1);
        }
        TriangleCarrier other = null;
        TriangleCarrier current = null;
        for (TriangleCarrier t : incident) {
            if (!t.contains(p)) {
                other = t;
            } else {
                current = t;
            }
        }
        if(other == null){
            return;
        }
        Vertex h = other.findThirdVertex(ab.getFirst(), ab.getSecond());

        if(!isLegal(ab, p, h)) {
            if (current != null) {
                TriangleDAG original1 = current.getTriangleDAG();
                TriangleDAG original2 = other.getTriangleDAG();

                T.removeTriangle(other);
                T.removeTriangle(current);

                TriangleCarrier new1 = new TriangleCarrier(p, h, ab.getFirst());
                TriangleCarrier new2 = new TriangleCarrier(p, h, ab.getSecond());

                TriangleDAG d1 = new TriangleDAG(new1);
                TriangleDAG d2 = new TriangleDAG(new2);
                new1.setTriangleDAG(d1);
                new2.setTriangleDAG(d2);


                try {
                    original1.addChild(d1);
                    original1.addChild(d2);
                    original2.addChild(d1);
                    original2.addChild(d2);
                } catch (DAGViolation dagViolation) {
                    dagViolation.printStackTrace();
                }

                T.addTriangle(new1);
                T.addTriangle(new2);
                legalizeEdge(p,T.findEdgeByVertices(h,ab.getFirst()),T);
                legalizeEdge(p,T.findEdgeByVertices(h,ab.getSecond()),T);
                if(debug) System.out.println("edge flip done on ab" + ab.getFirst().getID() + "," + ab.getSecond().getID() + " p:" +p.getID() + " h:"+h.getID());
            } else {
                System.out.println("error, no current triangle found.");
            }
        }
        printIncident(T);
    }

    public boolean isLegal(Edge ab, Vertex p, Vertex h) {
        if (ab.getTriangles().size() < 2) {
            return true;
        }
        else if(ab.getFirst().getID() >= 0 && ab.getSecond().getID() >= 0 && p.getID() >= 0 && h.getID() >= 0){
            if(new Triangle(null,null,null).sign(ab.getFirst(), ab.getSecond(), p) > 0.0d) {
                return !inCircle(ab.getFirst(), ab.getSecond(), p, h);
            } else {
                return !inCircle(ab.getFirst(), p, ab.getSecond(), h);
            }
        }
        else {
            return Math.min(p.getID(), h.getID()) < Math.min(ab.getFirst().getID(), ab.getSecond().getID());
        }

    }

    public boolean inCircle(Vertex a, Vertex b, Vertex c, Vertex d){
        double ma = a.getX() - d.getX();
        double mb = a.getY() - d.getY();
        //double mc = Math.pow(a.getX(),2) + Math.pow(a.getY(), 2);
        double mc = ((a.getX() - d.getX()) * (a.getX() - d.getX())) + ((a.getY() - d.getY()) * (a.getY() - d.getY()));
        double md = b.getX() - d.getX();
        double me = b.getY() - d.getY();
        double mf = ((b.getX() - d.getX()) * (b.getX() - d.getX())) + ((b.getY() - d.getY()) * (b.getY() - d.getY()));
        //double mf = Math.pow(b.getX(),2) + Math.pow(b.getY(), 2);
        double mg = c.getX() - d.getX();
        double mh = c.getY() - d.getY();
        double mi = ((c.getX() - d.getX()) * (c.getX() - d.getX())) + ((c.getY() - d.getY()) * (c.getY() - d.getY()));
        //double mi = Math.pow(c.getX(),2) + Math.pow(c.getY(), 2);

        double determinant = ma*me*mi + mb*mf*mg + mc*md*mh - mc*me*mg - mb*md*mi - ma*mf*mh;

        //Todo Increase precision using epsilon and checking between -epsilon and epsilon
        if(debug) System.out.println("inCircle?: " + determinant);
        return determinant > 0;

    }

    public void printIncident(Triangulation T) {
        if (debug) {
            for (Edge e : T.getEdges()) {
                ArrayList<TriangleCarrier> l = e.getTriangles();
                System.out.println("----------------------------------------------------");
                for (TriangleCarrier t : l) {
                    System.out.println("Found incident triangle " + t + " on edge " + e);
                }
                System.out.println("----------------------------------------------------");
            }
        }
    }

    public boolean isLeftUpDown(Vertex p, Vertex a, Vertex b) {
        if(a.getY() > b.getY() || (a.getY() == b.getY() && a.getX() < b.getX())) {
            return new Triangle(null,null,null).sign(p,a,b) >= 0.0d;
        } else {
            return new Triangle(null,null,null).sign(p,b,a) >= 0.0d;
        }
    }

    public boolean isRightUpDown(Vertex p, Vertex a, Vertex b) {
        if(a.getY() > b.getY() || (a.getY() == b.getY() && a.getX() < b.getX())) {
            return new Triangle(null,null,null).sign(p,a,b) <= 0.0d;
        } else {
            return new Triangle(null,null,null).sign(p,b,a) <= 0.0d;
        }
    }

    public Edge findOnEdge(Triangle t, Vertex p, Triangulation T) {

        double d1 = t.sign(p,t.getA(),t.getB());
        double d2 = t.sign(p,t.getB(),t.getC());
        double d3 = t.sign(p,t.getC(),t.getA());
        if (Math.abs(d1) < epsilon) {
            return T.findEdgeByVertices(t.getA(),t.getB());
        } else if (Math.abs(d2) < epsilon) {
            return T.findEdgeByVertices(t.getB(),t.getC());
        } else if (Math.abs(d3) < epsilon){
            return T.findEdgeByVertices(t.getC(),t.getA());
        }
        return null;
    }




}
