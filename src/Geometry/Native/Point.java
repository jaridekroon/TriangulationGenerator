package Geometry.Native;

public class Point implements Comparable<Point>{
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public int compareTo(Point o) {
        if(this.getY() > o.getY() || (this.getY() == o.getY() && this.getX() < o.getX())){
            return 1;
        }
        else if(this.getY() == o.getY() && this.getX() == o.getY()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
