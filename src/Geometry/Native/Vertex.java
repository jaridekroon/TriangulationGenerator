package Geometry.Native;

import Geometry.Native.Point;

import java.util.ArrayList;
import java.util.List;

public class Vertex extends Point {
    private int ID;
    private int player;

    public Vertex(double x, double y){
        super(x,y);
    }

    public Vertex(double x, double y, int ID)
    {
        super(x,y);
        this.ID = ID;
    }

    public Vertex(double x, double y, int ID, int player){
        super(x,y);
        this.ID = ID;
        this.player = player;
    }

    public double distance(Vertex v){
        double width = (v.getX() - this.getX());
        double height = (v.getY() - this.getY());
        return Math.sqrt(width * width + height * height);
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }


    public int getPlayer(){
        return this.player;
    }

    public void setPlayer(int player){
        this.player = player;
    }

    @Override
    public int hashCode() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        Vertex v = (Vertex) o;
        return this.getID() == v.getID();
    }

    @Override
    public String toString(){
        return String.format("ID: %d X: %f Y: %f", this.getID(), this.getX(), this.getY());
    }
}
