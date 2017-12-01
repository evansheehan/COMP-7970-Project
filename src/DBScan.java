import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Evan Sheehan & Branson Popp & Jordan Peterson on 11/6/2017.
 */

public class DBScan {

   private ArrayList<ArrayList<String>> dataset;
   private ArrayList<String> unvisitedPoints;
   private ArrayList<String> visitedPoints;
   private ArrayList<ArrayList<String>> clusters;
   private ArrayList<String> noise;

   public DBScan(String fileName, String symmetry) throws FileNotFoundException {
      visitedPoints = new ArrayList<>();
      clusters = new ArrayList<>();
      noise = new ArrayList<>();
      unvisitedPoints = new ArrayList<>();
      if (symmetry.toLowerCase().compareTo("asymmetric") == 0) {
         dataset = readInputAsymmetric(fileName);
      } else if (symmetry.toLowerCase().compareTo("symmetric") == 0) {
         dataset = readInput(fileName);
      } else {
         System.out.println("Specify 'symmetric' or 'asymmetric'");
      }

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

      }
      manyLists.add(singleList);
      return manyLists;
   }

   public ArrayList<ArrayList<String>> dbScan(int radius, int minPts) {
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
                  //System.out.println(unvisitedPoints.size());
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

         } else {
            noise.add(list.get(0));
         }

      } while (unvisitedPoints.size() != 0);
      System.out.println("Number of Clusters Produced: " + clusters.size());
      return clusters;
   }


   public ArrayList<String> getNeighborhood(ArrayList<String> list, int radius) {
      ArrayList<String> bank = new ArrayList<>();
      ArrayList<String> temp;
      ArrayList<String> neighbors = new ArrayList<>();
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

         while (levelSize != 0) {
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

      if (neighbors != null) {
         return neighbors;
      }

      return null;
   }


   public ArrayList<String> getList(String header) {

      if (dataset != null) {
         for (int i = 0; i < dataset.size(); i++) {
            if (dataset.get(i).get(0).compareTo(header) == 0) {
               return dataset.get(i);
            }
         }
      }
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
      return null;
   }


   //Unused
   public ArrayList<String> getListBinary(ArrayList<ArrayList<String>> dataset, String header) {
      if (dataset.size() != 0) {
         int left = 0;
         int right = dataset.size() - 1;
         while (left <= right) {
            int mid = left + ((right - left) / 2);
            if (Integer.parseInt(dataset.get(mid).get(0)) > Integer.parseInt(header)) {
               right = mid - 1;
            } else if (Integer.parseInt(dataset.get(mid).get(0)) < Integer.parseInt(header)) {
               left = mid + 1;
            } else {
               return dataset.get(mid);
            }
         }
      }
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

   public ArrayList<ArrayList<String>> readInputAsymmetric(String fileName) throws FileNotFoundException {
      Scanner in = new Scanner(new FileInputStream(new File(fileName)));
      ArrayList<ArrayList<String>> manyLists = new ArrayList<>();
      ArrayList<String> singleList = new ArrayList<>();
      ArrayList<String> possibleList;

      while (in.hasNext()) {
         String line = in.nextLine();
         Scanner inLine = new Scanner(line);
         possibleList = new ArrayList<>();


         if (!line.startsWith("#")) {
            String point = inLine.next();
            String secondPoint = inLine.next();
            if (getList(manyLists, point) == null) {
               singleList.add(point);
               singleList.add(secondPoint);
               manyLists.add(singleList);
               unvisitedPoints.add(point);
               singleList = new ArrayList<>();
            } else if (!getList(manyLists, point).contains(secondPoint)) {
               getList(manyLists, point).add(secondPoint);
            }

            if (getList(manyLists, secondPoint) == null) {
               possibleList.add(secondPoint);
               possibleList.add(point);
               manyLists.add(possibleList);
               unvisitedPoints.add(secondPoint);
            } else {
               getList(manyLists, secondPoint).add(point);
            }

         }
         System.out.println(manyLists.size());
      }
      manyLists.add(singleList);
      return manyLists;
   }

}


