import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
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
      manyLists.add(singleList);
      return manyLists;
   }

   public ArrayList<ArrayList<String>> dbScan(int radius, int minPts) {
      ArrayList<ArrayList<String>> dataset = new ArrayList<>(this.dataset);
      ArrayList<ArrayList<String>> unvisitedPoints = new ArrayList(this.dataset);
      ArrayList<ArrayList<String>> clusters = new ArrayList<>();
      ArrayList<ArrayList<String>> visitedPoints = new ArrayList<>();
      ArrayList<String> list;
      Random randGen = new Random();

      do {
         int randNum = randGen.nextInt(dataset.size());
         list = unvisitedPoints.get(randNum);
         unvisitedPoints.remove(randNum);
         visitedPoints.add(list);

         //getNeighborhood






      } while (unvisitedPoints.size() != 0);
      return null;
   }

   public int numNeighbors(ArrayList<String> list, int radius) {
      ArrayList<String> bank = new ArrayList<>();
      int level = 1;
      int numPoints = 0;

      numPoints += list.size() - 1;
      level++;


      while (level <= radius) {
         String header = bank.get(0);
         //use getList method
         bank.remove(0);
         for (int i = 1; i < list.size(); i++) {
            String point = list.get(i);
            if (!bank.contains(point)) {
               bank.add(point);
            }
         }
         level++;
      }
         //go through bank and count

      return 0;
   }

   public ArrayList<String> getList (String header) {

      for (int i = 0; i < dataset.size(); i++) {
         if (dataset.get(i).get(0).compareTo(header) == 0) {
            return dataset.get(i);
         }
      }
      //Preferably use some kind of search like binary, but I'll implement linear for now.

      return null;
   }
}
