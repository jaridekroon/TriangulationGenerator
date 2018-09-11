package Geometry.Native;

import java.util.ArrayList;
import java.util.Iterator;

public class Face {

    private HalfEdge outerComponent;
    public HalfEdge getOuterComponent() {
        return outerComponent;
    }

    private Vertex origin;

    private double area;

    public Face(HalfEdge outer){
        this.outerComponent = outer;
        area = area();
    }

    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public double area() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        HalfEdge he = outerComponent;

        do {
            vertices.add(he.getOrigin());
            he = he.getNext();
        } while (he != outerComponent);

        double result = 0.0d;

        Iterator<Vertex> itr = vertices.iterator();
        if(itr.hasNext()) {
            Vertex first = itr.next();
            Vertex f = first;
            while(itr.hasNext()) {
                Vertex s = itr.next();
                result += f.getX()*s.getY() - f.getY()*s.getX();
                f = s;
            }
            result += f.getX()*first.getY() - f.getY()*first.getX();

            return Math.abs(result/2);
        } else {
            return 0.0d;
        }
    }

    @Override
    public String toString(){
        String res = "Face: ";
        HalfEdge l = this.outerComponent;
        do {
            res = res + " " + l.getOrigin().getID();
            l = l.getNext();
            if (l == null) {
                System.out.println("THIS SHOULD NOT HAPPEN");
            }
        } while (l.getOrigin().getID() != this.outerComponent.getOrigin().getID());
        return "Vertex: " + this.getOrigin() +" " + res;
    }

    public boolean isOrigin(Vertex v){
//        HalfEdge comp = this.outerComponent;
//        Vertex[] o1 = comp.getDualEdgeVertices();
//        if(v.getID() == 0){
//            System.out.println("null");
//        }
//        Vertex[] o2 = comp.getNext().getDualEdgeVertices();
//
//        while(o1 == null || o2 == null){
//            comp = comp.getNext();
//            o1 = comp.getDualEdgeVertices();
//            o2 = comp.getNext().getDualEdgeVertices();
//            if(Arrays.equals(this.outerComponent.getDualEdgeVertices(), o2)){
//                System.out.println("ERROR");
//                break;
//            }
//        }
//
//        return allEqual(o1[0], o2[0], v) || allEqual(o1[0], o2[1], v) || allEqual(o1[1], o2[0], v) | allEqual(o1[1], o2[1], v);
//        HalfEdge h1 = this.outerComponent;
//        HalfEdge h2 = this.outerComponent.getNext();
//
//        boolean inside = true;
//
//        do {
//            if(cross(h1.getOrigin(), h2.getOrigin(), v) > 0){
//                inside = false;
//            }
//        } while (!h1.equals(outerComponent));

        Vertex[] vertices = createVertexArray();
        return contains(vertices, v);
    }

    private boolean contains(Vertex[] hull, Vertex test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = hull.length - 1; i < hull.length; j = i++) {
            if ((hull[i].getY() > test.getY()) != (hull[j].getY() > test.getY()) &&
                    (test.getX() < (hull[j].getX() - hull[i].getX()) * (test.getY() - hull[i].getY()) / (hull[j].getY() - hull[i].getY()) + hull[i].getX())) {
                result = !result;
            }
        }
        return result;
    }

    private Vertex[] createVertexArray(){
        HalfEdge h1 = outerComponent;
        Vertex[] res = new Vertex[this.getEdgeCount()];
        for(int i = 0; i < res.length; i++){
            res[i] = h1.getOrigin();
            h1 = h1.getNext();
        }
        return res;
    }

    private int getEdgeCount(){
        int c = 0;
        HalfEdge h1 = outerComponent;
        do{
            c++;
            h1 = h1.getNext();
        } while(!h1.equals(outerComponent));
        return c;
    }
}
