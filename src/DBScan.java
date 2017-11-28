import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Evan on 11/6/2017.
 */

public class DBScan {

   private ArrayList<Point> dataset;

   public DBScan(String fileName) throws FileNotFoundException {
      dataset = new ArrayList<>();
      dataset = readInput(fileName);
   }

   class Point {
      String nodeID;
      ArrayList<Point> neighbors;

      public Point() {
         throw new IllegalArgumentException();
      }

      public Point(String nodeID) {
         this.nodeID = nodeID;
      }

      public Point(String nodeID, String neighbor) {
         this.nodeID = nodeID;
         if (neighbors == null) {
            neighbors = new ArrayList<>();
         }
         neighbors.add(new Point(neighbor));
      }

      private void addNeighbor(String nodeID) {
         Point point = new Point(nodeID);
         neighbors.add(point);
      }
   }

   public ArrayList<Point> readInput(String fileName) throws FileNotFoundException {
      Scanner in = new Scanner(new FileInputStream(new File(fileName)));
      String prevPoint = "";

      while (in.hasNext()) {
         String line = in.nextLine();
         if (!line.startsWith("#")) {
            Point point = new Point(in.next(), in.next());
            if (dataset.size() == 0) {
               dataset.add(point);
            } else if (prevPoint.compareTo(point.nodeID) == 0) {
               point.addNeighbor(point.nodeID);
               in.next();
            }
            prevPoint = point.nodeID;
         }
      }
      return dataset;
   }

   public ArrayList<ArrayList<Point>> dbScanAlgorithm(ArrayList<Point> allPoints, int radius, int minPts) {
      return null;
   }
   /*public double calculateDistance(Point point1, Point point2) {
      return 0;
   }*/
}
