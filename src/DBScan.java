import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by awubis on 11/6/17.
 */
public class DBScan {

   private ArrayList<Point> dataset;

   public DBScan(String fileName) throws FileNotFoundException {
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

      private ArrayList<Point> getNeighbors(int nodeID) {
         return null;
      }
   }

   public ArrayList<Point> readInput(String fileName) throws FileNotFoundException {

      Scanner in = new Scanner(new FileInputStream(new File(fileName)));

      while (in.hasNext()) {
         Point point = new Point(in.next());
         if (!dataset.contains(point)) {
            dataset.add(point);
            in.next();
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
