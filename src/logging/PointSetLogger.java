package Logging;

import Geometry.Native.Vertex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PointSetLogger {

    private static final String defaultTitle = "Vertices";

    private static final String dirname = "LoggedPoints";
    private static final File filepath = new File(dirname);

    private static final String separator = File.separator;

    static {
        if(!filepath.exists()){
            filepath.mkdir();
        }
    }

    public static void logPointSet(Vertex[] vertices){
        try {
            FileWriter fileWriter = new FileWriter(new File(buildFilePath(defaultTitle)), false);
            for(Vertex v: vertices){
                fileWriter.write(String.format("new Vertex(%f, %f, %d, %d),\n", v.getX(), v.getY(), v.getID(), v.getPlayer()));
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logPointSet(Vertex[] vertices, String title){
        try {
            FileWriter fileWriter = new FileWriter(new File(buildFilePath(title)), false);
            for(Vertex v: vertices){
                fileWriter.write(String.format("new Vertex(%f, %f, %d, %d),\n", v.getX(), v.getY(), v.getID(), v.getPlayer()));
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logPointSet(Vertex[] vertices, boolean makeNewFile){
        try {
            File file;
            if(makeNewFile){
                DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
                Date date = new Date();
                String filename = dateFormat.format(date);
                file  = new File(buildFilePath(filename));
            }
            else file = new File(buildFilePath(defaultTitle));
            FileWriter fileWriter = new FileWriter(file, false);
            for(Vertex v: vertices){
                fileWriter.write(String.format("new Vertex(%f, %f, %d, %d),\n", v.getX(), v.getY(), v.getID(), v.getPlayer()));
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildFilePath(String title){
        return filepath + separator + title + ".txt";
    }
}
