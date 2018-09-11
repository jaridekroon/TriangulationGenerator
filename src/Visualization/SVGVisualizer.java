package Visualization;
import Geometry.BoundingBox;
import Geometry.DCEL;
import Geometry.Native.Edge;
import Geometry.Native.Face;
import Geometry.Native.HalfEdge;
import Geometry.Native.Vertex;
import Geometry.Triangulation;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SVGVisualizer {
    private BoundingBox boundingBox;

    private final int diameter = 10;
    private int graphicHeight;
    private int graphicWidth;

    private final int offset = 40;
    private int xoffset;
    private int yoffset;

    private Color player1Color = Color.RED;
    private Color player2Color = Color.BLUE;
    private Color delaunayEdgeColor = Color.BLACK;

    public SVGVisualizer(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
        graphicHeight = (int)(boundingBox.getHeight() + boundingBox.getWidth()/2 + 2*offset);
        graphicWidth = (int)(2*boundingBox.getHeight() + boundingBox.getWidth() + 2*offset);
        xoffset = (int)(offset + boundingBox.getHeight());
        yoffset = offset;

    }

    public SVGVisualizer() {
        graphicHeight = 700;
        graphicWidth = 700;
        xoffset = offset;
        yoffset = offset;
    }


    public SVGGraphics2D drawDelaunay(Triangulation t){
        SVGGraphics2D g2 = new SVGGraphics2D(graphicWidth, graphicHeight);
        drawInputVertices(g2, t.getVertices());
        drawEdges(g2, t.getEdges(), delaunayEdgeColor);
        return g2;
    }

    public void createSVGOutput(SVGGraphics2D g2, File file) throws  IOException {
        SVGUtils.writeToSVG(file, g2.getSVGElement());
    }

    public void drawAndPrintDelaunay(Triangulation t, String filename){
        try {
            createSVGOutput(drawDelaunay(t), new File(filename+".svg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawInputVertices(SVGGraphics2D g2, HashSet<Vertex> vertices){
        Color color;
        for (Vertex v: vertices) {
            if(v.getPlayer() == 1){
                color = this.player1Color;
            }
            else color = this.player2Color;
            drawVertex(g2, v,color);
        }
    }

    private void drawVertex(SVGGraphics2D g2, Vertex v, Color color){
        g2.setPaint(color);
        g2.fillOval(((int)v.getX() - diameter/2)  +xoffset, ((int)v.getY() - diameter/2) + yoffset, diameter, diameter);
        drawCoordinate(g2, v, color);
    }

    private void drawEdges(SVGGraphics2D g2, HashSet<Edge> edges, Color color){
        g2.setPaint(color);
        for(Edge e: edges){
            int x1 = (int)e.getFirst().getX() + xoffset;
            int y1 = (int)e.getFirst().getY() + yoffset;
            int x2 = (int)e.getSecond().getX() + xoffset;
            int y2 = (int)e.getSecond().getY() + yoffset;
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawCoordinate(SVGGraphics2D g2, Vertex v, Color color){
        g2.setPaint(color);
        int x = (int)v.getX();
        int y = (int)v.getY();
        String coordinates = String.format("(%d)", v.getID());

        g2.drawString(coordinates, x + 5 + xoffset, y + yoffset);
    }
}
