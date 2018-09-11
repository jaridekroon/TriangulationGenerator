package Geometry.Native;

public class HalfEdge {

    private Vertex origin;
    private HalfEdge next;
    private HalfEdge previous;
    private HalfEdge twin;
    private Face incidentFace;

    public Vertex getOrigin() {
        return origin;
    }

    public HalfEdge getNext() {
        return next;
    }

    public void setNext(HalfEdge he){
        this.next = he;
    }

    public HalfEdge getPrevious() {
        return previous;
    }

    public void setPrevious(HalfEdge he){
        this.previous = he;
    }

    public Face getIncidentFace() {
        return incidentFace;
    }

    public void setIncidentFace(Face f) { this.incidentFace = f; }



    public HalfEdge getTwin() {
        return twin;
    }

    public void setTwin(HalfEdge he){
        this.twin = he;
    }

    public HalfEdge(Vertex origin){
        this.origin = origin;
    }

    public HalfEdge(Vertex origin, HalfEdge next, HalfEdge previous, Face incidentFace, HalfEdge twin){
        this.origin = origin;
        this.next = next;
        this.previous = previous;
        this.incidentFace = incidentFace;
        this.twin = twin;
    }

    @Override
    public boolean equals(Object o) {
        HalfEdge h = (HalfEdge) o;
        return (h.getOrigin() == this.getOrigin() && h.getTwin().getOrigin() == this.getTwin().getOrigin());
    }


    @Override
    public String toString(){
        return String.format("HE(o: %d, n: %d, p: %d, t: %d, f: NI)", this.getOrigin().getID(),
                                                                        this.getTwin().getOrigin().getID(),
                                                                        this.getTwin().getOrigin().getID(),
                                                                        this.getTwin().getOrigin().getID());

    }
}

