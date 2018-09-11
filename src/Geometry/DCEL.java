package Geometry;

import Geometry.Native.Face;
import Geometry.Native.HalfEdge;
import Geometry.Native.Vertex;

import java.lang.reflect.Array;
import java.util.*;

public class DCEL {

    private List<Vertex> vertices;
    private ArrayList<HalfEdge> halfEdges;
    private List<Face> faces;
    //private HashMap<Vertex, List<HalfEdge>> incidentEdges;
    private HashMap<Integer, List<HalfEdge>> incidentEdges;

    public DCEL(){
        this.halfEdges = new ArrayList<>();
        this.vertices = new ArrayList<>();
        this.faces = new ArrayList<>();
        this.incidentEdges = new HashMap<>();
    }

    public DCEL(List<Vertex> vertices, ArrayList<HalfEdge> halfEdges, List<Face> faces){
        this.vertices = vertices;
        this.halfEdges = halfEdges;
        this.faces = faces;
        this.incidentEdges = new HashMap<>();
    }

    public HashMap<Integer, List<HalfEdge>> getIncidentEdges() {
        return incidentEdges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<HalfEdge> getHalfEdges() {
        return halfEdges;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public void setHalfEdges(ArrayList<HalfEdge> halfEdges) {
        this.halfEdges.clear();
        for(HalfEdge h: halfEdges){
            addHalfEdge(h);
        }
    }

    public void setVertices(List<Vertex> vertices) {
        this.incidentEdges.clear();
        this.vertices.clear();
        for(Vertex v: vertices){
            addVertex(v);
        }
    }

    public void addVertex(Vertex v){
        //todo if statement not allowed due to uniform case, however not checked if it breaks anything else
//        if(!this.vertices.contains(v)) {
        this.vertices.add(v);
        if(!incidentEdges.containsKey(v.getID())) {
            this.incidentEdges.put(v.getID(), new ArrayList<HalfEdge>());
        }
//        }
    }

    public void removeVertex(Vertex v){
        this.vertices.remove(v);
        this.incidentEdges.remove(v.getID());
    }

    public void removeHalfEdge(HalfEdge h){
        this.halfEdges.remove(h);
        if(incidentEdges.containsKey(h.getOrigin().getID())) {
            this.incidentEdges.get(h.getOrigin().getID()).remove(h);
        }
    }

    public void addHalfEdge(HalfEdge h){
        this.halfEdges.add(h);
        if(!this.incidentEdges.get(h.getOrigin().getID()).contains(h)) {
            this.incidentEdges.get(h.getOrigin().getID()).add(h);
        }
    }

    public void addFace(Face f){
        this.faces.add(f);
    }

    /** Sort the incident edges in clockwise order for each vertex */
    public void sortIncidentEdges() {
        for(List<HalfEdge> lhe : this.incidentEdges.values()) {
            Collections.sort(lhe, (a,b) -> less(a.getTwin().getOrigin(),b.getTwin().getOrigin(),a.getOrigin()) ? -1 : less(b.getTwin().getOrigin(),a.getTwin().getOrigin(), a.getOrigin()) ? 1 : 0);
        }
    }

    /** Assume a.getOrigin() == b.getOrigin() (since all halfedges start in the same vertex, this should be fine)*/
    public boolean less(Vertex aa, Vertex bb, Vertex center) {
        if (aa.getX() - center.getX() >= 0 && bb.getX() - center.getX() < 0)
            return true;
        if (aa.getX() - center.getX() < 0 && bb.getX() - center.getX() >= 0)
            return false;
        if (aa.getX() - center.getX() == 0 && bb.getX() - center.getX() == 0) {
            if (aa.getY() - center.getY() >= 0 || bb.getY() - center.getY() >= 0)
                return aa.getY() > bb.getY();
            return bb.getY() > aa.getY();
        }

        // compute the cross product of vectors (center -> a) x (center -> b)
        double det = (aa.getX() - center.getX()) * (bb.getY() - center.getY()) - (bb.getX() - center.getX()) * (aa.getY() - center.getY());
        if (det < 0)
            return true;
        if (det > 0)
            return false;

        // points a and b are on the same line from the center
        // check which point is closer to the center
        double d1 = (aa.getX() - center.getX()) * (aa.getX() - center.getX()) + (aa.getY() - center.getY()) * (aa.getY() - center.getY());
        double d2 = (bb.getX() - center.getX()) * (bb.getX() - center.getX()) + (bb.getY() - center.getY()) * (bb.getY() - center.getY());
        return d1 > d2;
    }

    public void computeNextPrev() {
        this.sortIncidentEdges();
        //todo for each clockwise pair, add prevs and nexts
        //for(Vertex v : this.vertices) {
        for(int i = 0; i < this.vertices.size(); i++) {
            Vertex v = vertices.get(i);
            Iterator<HalfEdge> itr = incidentEdges.get(v.getID()).iterator();
            if(incidentEdges.get(v.getID()) == null || incidentEdges.get(v.getID()).size() == 0) {
                Math.abs(1);
            }
            if (itr.hasNext()) {
                HalfEdge first = itr.next();
                HalfEdge e1 = first;
                while (itr.hasNext()) {
                    HalfEdge e2 = itr.next();
                    e1.getTwin().setNext(e2);
                    e2.setPrevious(e1.getTwin());
                    e1 = e2;
                }
                e1.getTwin().setNext(first);
                first.setPrevious(e1.getTwin());
            } else {
                System.out.println("THIS VERTEX HAS NO OUTGOING EDGES! " + v);
            }
        }

        //todo add faces for each cycle
        ArrayList<HalfEdge> copy = new ArrayList<>(this.halfEdges);
        Iterator<HalfEdge> itr = copy.iterator();
        while(itr.hasNext()) {
            HalfEdge e = itr.next();
            if(e.getIncidentFace() == null) {
                Face f = new Face(e);
                this.faces.add(f);
                HalfEdge l = e;
                do {
                    l.setIncidentFace(f);
                    if (l.getNext() == null) {
                        System.out.println("THIS SHOULD NOT HAPPEN");
                    }
                    l = l.getNext();
                } while (l.getOrigin().getID() != e.getOrigin().getID());
            }
        }
    }

    private void printList(List<HalfEdge> halfEdges, String title){
        System.out.println(title);
        for(HalfEdge he: halfEdges){
            System.out.println(he);
        }
        System.out.println("--------------------");
    }

    public Vertex findByCoordinates(Vertex v) {
        for(Vertex p : this.vertices) {
            if(p.getX() == v.getX() && p.getY() == v.getY()) {
                return p;
            }
        }
        return null;
    }

}
