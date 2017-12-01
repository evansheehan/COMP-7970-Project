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
   private ArrayList<String> datasetIDs;
   private ArrayList<String> unvisitedPoints;
   private ArrayList<String> visitedPoints;
   private ArrayList<ArrayList<String>> clusters;
   private ArrayList<String> noise;

   public DBScan(String fileName) throws FileNotFoundException {
      unvisitedPoints = new ArrayList<>();
      visitedPoints = new ArrayList<>();
      dataset = readInput(fileName);
      clusters = new ArrayList<>();
      noise = new ArrayList<>();
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
            String fromNode = inLine.next();
            String toNode = inLine.next();
            if (singleList.size() == 0) {
               singleList.add(fromNode);
               singleList.add(toNode);
               prevNode = singleList.get(0);
            } else if (prevNode.compareTo(fromNode) == 0) {
               singleList.add(toNode);
               prevNode = singleList.get(0);
            } else if (prevNode.compareTo(fromNode) != 0) {
               manyLists.add(singleList);
               unvisitedPoints.add(singleList.get(0));
               singleList = new ArrayList<>();
               singleList.add(fromNode);
               singleList.add(toNode);
               prevNode = singleList.get(0);
            }
         }


            /*if (getList(manyLists, toNode) == null) {
               ArrayList<String> temp = new ArrayList<>();
               temp.add(toNode);
               temp.add(fromNode);
               manyLists.add(temp);
            } else {
               getList(manyLists, toNode).add(fromNode);
            }*/


      }manyLists.add(singleList);
      return manyLists;
   }

   public ArrayList<ArrayList<String>> dbScan(int radius, int minPts) {
      ArrayList<ArrayList<String>> dataset = new ArrayList<>(this.dataset);
      ArrayList<String> list;
      Random randGen = new Random();

      do {
         int randNum = randGen.nextInt(unvisitedPoints.size());
         list = getList(unvisitedPoints.get(randNum));
         unvisitedPoints.remove(randNum);
         visitedPoints.add(list.get(0));

         ArrayList<String> neighborhood = getNeighborhood(list, radius);
         if (neighborhood.size() >= minPts) {
            ArrayList<String> cluster = new ArrayList<>();
            cluster.add(list.get(0));
            ArrayList<String> tempNeighborhood = new ArrayList<>(neighborhood);

            for (int i = 0; i < tempNeighborhood.size(); i++) {
               String point = neighborhood.get(i);
               if (unvisitedPoints.contains(point)) {
                  unvisitedPoints.remove(point);
                  System.out.println(unvisitedPoints.size());
                  visitedPoints.add(point);
                  //System.out.println(point);
                  ArrayList<String> n = getNeighborhood(neighborhood, radius);
                  if (n.size() >= minPts) {
                     for (String p : n) {
                        //System.out.println(p);
                        if (!neighborhood.contains(p)) {
                           neighborhood.add(p);
                        }
                        //System.out.println(neighborhood.size());
                     }
                  }
               }
               if (!isInCluster(point)) {
                  cluster.add(point);
               }
            }
            clusters.add(cluster);

         }
         else {
            noise.add(list.get(0));
         }


      } while (unvisitedPoints.size() != 0);
      return clusters;
   }


   public ArrayList<String> getNeighborhood(ArrayList<String> list, int radius) {
      ArrayList<String> bank = new ArrayList<>();
      ArrayList<String> temp = new ArrayList<>();
      ArrayList<String> neighbors = new ArrayList<String>();
      int levelSize = 0;
      int level = 1;

      if (radius != 1) {
         for (int i = 1; i < list.size(); i++) {
            bank.add(list.get(i));
            neighbors.add(list.get(i));
            levelSize = bank.size();
         }
         level++;
      } else {
         for (int i = 1; i < list.size(); i++) {
            neighbors.add(list.get(i));
         }
      }

      while (level <= radius) {

         while (levelSize != 0) {    // bull
            String header = bank.get(0);
            temp = getList(header);
            bank.remove(0);
            levelSize--;

            for (int i = 1; i < temp.size(); i++) {
               String point = temp.get(i);
               if (!bank.contains(point)) {
                  bank.add(point);
                  if (!neighbors.contains(point)) {
                     neighbors.add(point);
                  }
               }
            }
         }
         levelSize = bank.size();
         level++;
      }
      //go through bank and count
      if (neighbors != null) {
         return neighbors;
      }

      return null;
   }


   /*public ArrayList<String> epNeighborhood(ArrayList<String> list, int radius) {
      ArrayList<String> bank = new ArrayList<>();
      ArrayList<String> neighbors = new ArrayList<>();
      int level = 1;

      if (radius != 1) {
         for (int i = 1; i < list.size(); i++) {
            bank.add(list.get(i));
         }
      }
      level++;

      int i = 1;
      while (level <= radius) {

         String id = bank.get(i);
         i++;

         ArrayList<String> temp = getList(id);
         for (int j = 1; j < temp.size(); j++) {
            String point = temp.get(j);
            if (!bank.contains(point)) {
               bank.add(point);
            }
         }
         level++;
      }
      //go through bank and count

      return bank;
   }*/

   public ArrayList<String> getList(String header) {

      if (dataset != null) {
         for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i).get(0).compareTo(header) == 0) {
               return dataset.get(i);
            }
         }
      }
      //Preferably use some kind of search like binary, but I'll implement linear for now.
      return null;
   }

   public ArrayList<String> getList(ArrayList<ArrayList<String>> dataset, String header) {
      if (dataset != null) {
         for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i).get(0).compareTo(header) == 0) {
               return dataset.get(i);
            }
         }
      }
      //Preferably use some kind of search like binary, but I'll implement linear for now.
      return null;
   }

   public boolean isInCluster(String id) {
      for (ArrayList<String> cluster : clusters) {
         for (String point : cluster) {
            if (id.compareTo(point) == 0) {
               return true;
            }
         }
      }
      return false;
   }
}
