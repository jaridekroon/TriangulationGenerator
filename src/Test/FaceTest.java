package Test;

import Geometry.Native.Face;
import Geometry.Native.HalfEdge;
import Geometry.Native.Vertex;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FaceTest {

    @Test
    public void area() {
        Vertex p1 = new Vertex(0,0,0);
        Vertex p2 = new Vertex(20,0,0);
        Vertex p3 = new Vertex(20,20,0);
        Vertex p4 = new Vertex(0,20,0);

        HalfEdge e1 = new HalfEdge(p1);
        HalfEdge e2 = new HalfEdge(p2);
        HalfEdge e3 = new HalfEdge(p3);
        HalfEdge e4 = new HalfEdge(p4);

        e1.setNext(e2);
        e2.setNext(e3);
        e3.setNext(e4);
        e4.setNext(e1);

        Face f = new Face(e1);
        Assert.assertTrue(400d == f.area());
    }
}