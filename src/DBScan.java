import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Evan on 11/6/2017.
 */

public class DBScan {

   private ArrayList<ArrayList<String>> dataset;

   public DBScan(String fileName) throws FileNotFoundException {
      dataset = readInput(fileName);
   }

   public ArrayList<ArrayList<String>> readInput(String fileName) throws FileNotFoundException {
      Scanner in = new Scanner(new FileInputStream(new File(fileName)));
      ArrayList<ArrayList<String>> manyLists = new ArrayList<>();
      ArrayList<String> singleList = new ArrayList<>();
      String prevNode = "";

      while (in.hasNext()) {
         String line = in.nextLine();
         Scanner inLine = new Scanner(line);

         if (!line.startsWith("#")) {
            String point = inLine.next();
            if (singleList.size() == 0) {
               singleList.add(point);
               singleList.add(inLine.next());
               prevNode = singleList.get(0);
            } else if (prevNode.compareTo(point) == 0) {
               singleList.add(inLine.next());
               prevNode = singleList.get(0);
            } else if (prevNode.compareTo(point) != 0) {
               manyLists.add(singleList);
               singleList = new ArrayList<>();
               singleList.add(point);
               singleList.add(inLine.next());
               prevNode = singleList.get(0);
            }
         }
      }
      return manyLists;
   }

   public ArrayList<ArrayList<String>> dbScan(int radius, int minPts) {
      ArrayList<ArrayList<String>> dataset = new ArrayList<>(this.dataset);
      ArrayList<ArrayList<String>> unvisitedPoints = new ArrayList<>(dataset);
      ArrayList<String> list;
      Random randGen = new Random();

      do {
         int randNum = randGen.nextInt(dataset.size());
         list = dataset.get(randNum);

      }
      while (unvisitedPoints.size() > 0);

      return null;
   }
}
