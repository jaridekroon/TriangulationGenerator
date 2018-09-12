import Geometry.*;
import Geometry.Native.Edge;
import Geometry.Native.Vertex;
import InputGenerators.InputGenerator;
import InputGenerators.RandomInputGenerator;
import Visualization.*;
import Logging.PointSetLogger;

import java.util.*;


public class main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        /** initialize bounding box */
        BoundingBox boundingBox = new BoundingBox(400, 400);

        /** Generate and log random dataset */
        InputGenerator randGen = new RandomInputGenerator(boundingBox, 10, 0);
        Vertex[] random = randGen.generate();
        //PointSetLogger.logPointSet(random, true);

        Vertex[] issue = new Vertex[] {
                new Vertex(25,115,3),
                new Vertex(69,110,1),
                new Vertex(173,289,9),
                new Vertex(194,298,6)
        };

        /** Compute triangulation (either randomized incremental (ran = true) or incremental (ran = false) */
        boolean ran = false;
        Delaunay d;
        Triangulation t;
        if(ran) {
            d = new Delaunay(random, boundingBox);
            t = d.triangulate();
        } else {
            t = new Triangulation(random);
            d = new Delaunay(random, boundingBox, t);
        }

        /** Draw the original output */
        try {
            SVGVisualizer v = new SVGVisualizer(boundingBox);
            v.drawAndPrintDelaunay(t, "outTriangulation");
        }catch(Exception ex){
            System.out.println(ex.getStackTrace());
        }

        /** Compute number of edge flips needed, this updates the private triangulation of d */
        System.out.println(d.countLegalize());
        Triangulation flipped = d.getTri();

        /** Draw the flipped output */
        try {
            SVGVisualizer v = new SVGVisualizer(boundingBox);
            v.drawAndPrintDelaunay(flipped, "outTriangulationFlipped");
        }catch(Exception ex){
            System.out.println(ex.getStackTrace());
        }

    }


}
