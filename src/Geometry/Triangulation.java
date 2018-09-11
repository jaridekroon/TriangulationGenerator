package Geometry;

import Geometry.Native.Edge;
import Geometry.Native.Triangle;
import Geometry.Native.Vertex;

import java.util.*;

public class Triangulation {

    /** Set of vertices */
    private HashSet<Vertex> vertices;

    /** Set of edges */
    private HashSet<Edge> edges;

    /** Set of triangles */
    private List<TriangleCarrier> triangles;

    public Triangulation() {
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
        this.triangles = new ArrayList<>();
    }

    public Triangulation(Vertex[] vertices) {
        /** Initializes 'a' triangulation on the vertices,
         * based on incremental algorithm
         * https://en.wikipedia.org/wiki/Point_set_triangulation
         */
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
        this.triangles = new ArrayList<>();

        Vertex[] temp = vertices;
        if (temp.length >= 3) {
            /** Sort vertices on x coordinate */
            Arrays.sort(temp, (a, b) -> a.getX() < b.getX() ? -1
                    : a.getX() > b.getX() ? 1
                    : a.getY() < b.getY() ? -1 : 0);
            /** Start with a triangle of the first three vertices */
            TriangleCarrier first = new TriangleCarrier(temp[0],temp[1],temp[2]);
            TriangleDAG newNode = new TriangleDAG(first);
            first.setTriangleDAG(newNode);
            this.addTriangle(first);
            for(int j = 3; j < temp.length; j++) {
                /** add triangle temp[h],temp[i],temp[j] for visible edges hi*/
                HashSet<Edge> edge_temp = new HashSet<>(this.edges);
                for(Edge e : edge_temp) {
                    boolean valid = true;
                    Vertex h = e.getFirst();
                    Vertex i = e.getSecond();
                    Edge hj = new Edge(h,temp[j]);
                    Edge ij = new Edge(i,temp[j]);
                    TriangleCarrier next = new TriangleCarrier(h,temp[j],i);
                    for(Edge f : edge_temp) {
                        if(!e.equals(f)) {
                            /** Edges should not intersect, interior of new triangle should be empty */
                            if(f.intersection(hj) != null || f.intersection(ij) != null || next.isStrictlyInside(f.getFirst()) || next.isStrictlyInside(f.getSecond())) {
                                /** Edge not visible, do not create triangle */
                                valid = false;
                                break;
                            }
                        }
                    }
                    if(valid) {
                        TriangleDAG newNode2 = new TriangleDAG(next);
                        first.setTriangleDAG(newNode2);
                        this.addTriangle(next);
                    }
                }
            }
        }
    }

    public void addTriangle(TriangleCarrier t) {
        if(!triangles.contains(t)) {
            triangles.add(t);
            Edge ab = new Edge(t.getA(), t.getB());
            Edge bc = new Edge(t.getB(), t.getC());
            Edge ca = new Edge(t.getC(), t.getA());
            if (!edges.contains(ab)) {
                edges.add(ab);
            }
            if (!edges.contains(bc)) {
                edges.add(bc);
            }
            if (!edges.contains(ca)) {
                edges.add(ca);
            }
            if (!vertices.contains(t.getA())) {
                vertices.add(t.getA());
            }
            if (!vertices.contains(t.getB())) {
                vertices.add(t.getB());
            }
            if (!vertices.contains(t.getC())) {
                vertices.add(t.getC());
            }

            findEdgeByVertices(ab.getFirst(), ab.getSecond()).addIncidentTriangle(t);
            findEdgeByVertices(bc.getFirst(), bc.getSecond()).addIncidentTriangle(t);
            findEdgeByVertices(ca.getFirst(), ca.getSecond()).addIncidentTriangle(t);
        } else {
            System.out.println("ALREADY CONTAINED");
        }

    }

    public void removeTriangle(TriangleCarrier t) {
        triangles.remove(t);
        for(Vertex v: t.getAllVertices()){
            for(Vertex vv: t.getAllVertices()){
                if(!v.equals(vv)){
                    Edge e = findEdgeByVertices(v,vv);
                    if(e!=null){
                        e.removeIncidentTriangle(t);
                        if(e.getTriangles().size() == 0) {
                            edges.remove(e);
                        }
                    }
                }
            }
        }

    }

    public List<TriangleCarrier> getTriangles() {
        return this.triangles;
    }

    public void addVertex(Vertex v) {

    }

    public HashSet<Vertex> getVertices() {
        return vertices;
    }

    public void removeVertex(Vertex v) {
        this.vertices.remove(v);
    }

    public void addEdge(Edge e) {

    }

    public HashSet<Edge> getEdges() {
        return edges;
    }

    public void removeEdge(Edge e) {
        edges.remove(e);
    }

    public Edge findEdgeByVertices(Vertex v1, Vertex v2){
        for(Edge edge: edges){
            if(edge.contains(v1) && edge.contains(v2)){
                return edge;
            }
        }
        return null;
    }
}
