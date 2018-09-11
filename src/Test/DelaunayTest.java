package Test;

import Geometry.Delaunay;
import Geometry.Native.Vertex;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DelaunayTest {

    @Test
    public void shuffle() {
        Vertex[] player = {
                new Vertex(290,250,3),
                new Vertex(150, 150,8),
                new Vertex(200,10,1),
                new Vertex(50,30,2),
        };
        Vertex[] shuffled = new Delaunay(player).shuffle(player);
        for (Vertex v : shuffled) {
            System.out.print(v.getID() + ",\t");
        }
    }

    @Test
    public void onEdge() {

    }

    @Test
    public void inCircle(){
        Vertex[] player = {
                new Vertex(10,10,0),
                new Vertex(15, 15,1),
                new Vertex(15,50,2),
                new Vertex(10,15,3),
        };
        //Point is inside the circle
        Assert.assertTrue(new Delaunay(player).inCircle(player[0], player[1], player[2], player[3]));
        //Point is outside the circle
        Assert.assertFalse(new Delaunay(player).inCircle(player[0], player[1], player[3], player[2]));

        Vertex[] player2 = {
                new Vertex(50,30,2),
                new Vertex(150,150,8),
                new Vertex(290,250,3),
                new Vertex(200,10,1),
        };

        //Point 1 is inside the circle defined by 2,3,8
        Assert.assertTrue(new Delaunay(player2).inCircle(player2[2], player2[1], player2[0], player2[3]));
        //order matters again?
        //Assert.assertTrue(new Delaunay(player2).inCircle(player2[0], player2[1], player2[2], player2[3]));

    }
}