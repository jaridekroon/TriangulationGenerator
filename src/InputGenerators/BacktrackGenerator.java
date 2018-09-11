package InputGenerators;

import Geometry.Native.Vertex;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class BacktrackGenerator{
    private double box_size;
    private int width;
    private int nof_points;
    private ArrayList<Vertex[]> plays;

    public BacktrackGenerator(double box_size)
    {
        this.box_size = box_size;
        this.plays = new ArrayList<Vertex[]>();
    }

    public int getNof_points() {
        return nof_points;
    }

    public void setNof_points(int nof_points) {
        this.nof_points = nof_points;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ArrayList<Vertex[]> getPlays() {
        return plays;
    }

    public void generate() {
        Vertex[] possibilities = this.generate_possible_vertices();
        int indices[][] = generate_possible_indices();
        this.plays.clear();
        for(int i = 0;i<indices.length;i++)
        {
            if(IntStream.of(indices[i]).distinct().toArray().length == indices[i].length)
            {
                Vertex v[] = new Vertex[this.nof_points];
                for(int j = 0;j<v.length;j++)
                {
                    v[j] = possibilities[indices[i][j]];
                }
                this.plays.add(v);
            }
        }
    }

    private int[][] generate_possible_indices() {
        int length = this.width * this.width;
        int size = (int)Math.pow((double)length, ((double)this.nof_points));
        int result[][] = new int[size][this.nof_points];
        for(int i = 0;i<this.nof_points;i++){
            for(int j = 0;j<size;j++)
            {
                result[j][i] = (int)Math.floor(((double)j) / Math.pow(((double)length),((double)i))) % length;
            }
        }
        return result;
    }
    private Vertex[] generate_possible_vertices()
    {
        Vertex possibilities[] = new Vertex[this.width * this.width];
        int i = 0;
        for(int x = 0;x < this.width;x++) {
            for(int y = 0;y < this.width;y++) {
                possibilities[i++] = new Vertex(
                        ((double)this.box_size) / ((double)this.width) * (((double)x) + 0.5f),
                        ((double)this.box_size) / ((double)this.width) * (((double)y) + 0.5f)
                );
            }
        }
        return possibilities;
    }
}
