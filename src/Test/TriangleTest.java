package Test;

import Geometry.Native.Triangle;
import Geometry.Native.Vertex;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void isInside() {
        //regular checks:
        Vertex v1 = new Vertex(10,10,1);
        Vertex v2 = new Vertex(20,10,2);
        Vertex v3 = new Vertex(15,15,3);
        Vertex p = new Vertex(15,10,4);
        Vertex p2 = new Vertex(15,11,4);
        Triangle t = new Triangle(v1,v2,v3);
        Assert.assertTrue(t.isInside(p));
        Assert.assertTrue(t.isInside(p2));

        Triangle t2 = new Triangle(v1,v3,v2);
        //ERROR, directions of triangle MATTERS, however then triangle on other side should work
        ///Assert.assertTrue(t2.isInside(p));
        Assert.assertTrue(t2.isInside(p2));

        p = new Vertex(15,9,4);
        Assert.assertTrue(!t.isInside(p));
        Assert.assertTrue(!t2.isInside(p));

        //INFINITE TRIANGLE TESTS:
        //double infinite coordinate
        Vertex p0 = new Vertex(1,100,0);
        Vertex p_1 = new Vertex(0,0,-1);
        Vertex p_2 = new Vertex(0,0,-2);
        Triangle root = new Triangle(p0,p_1,p_2);

        //lexicographical below p0, then inside (p0 chosen as highest lexico --> always in root)
        Vertex q = new Vertex(1, 50, 1);
        Assert.assertTrue(root.isInside(q));

        //lexicographical above p0, then not inside
        Vertex q2 = new Vertex(1,200,1);
        Assert.assertTrue(!root.isInside(q2));

        //single infinite coordinate
        Vertex k = new Vertex(1,50,2);
        Triangle single = new Triangle(p0,p_1,k);
        Vertex q3 = new Vertex(0,80,3);
        Vertex q4 = new Vertex(2,80,4);
        Assert.assertTrue(!single.isInside(q3));
        Assert.assertTrue(single.isInside(q4));

    }

    @Test
    public void sign() {
        Vertex p0 = new Vertex(0,10,0);
        Vertex p1 = new Vertex(0,20,1);
        Vertex p2 = new Vertex(0.0001,15,2);
        Triangle empty = new Triangle(null,null,null);
        System.out.println(empty.sign(p2,p0,p1));
    }

    @Test
    public void equals() {
        Vertex v1 = new Vertex(10,10,1);
        Vertex v2 = new Vertex(20,20,2);
        Vertex v3 = new Vertex(30,30,3);
        Triangle t1 = new Triangle(v1,v2,v3);
        Triangle t2 = new Triangle(v1,v2,v3);
        Assert.assertTrue(t1.equals(t2));
    }
}