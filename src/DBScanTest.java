import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by awubis on 11/14/17.
 */
public class DBScanTest {

   @Test
   public void evaluationTestForSymmetric() throws FileNotFoundException {
      double startTime = System.nanoTime();
      DBScan scan = new DBScan("CA-GrQc.txt", "symmetric");
      ArrayList<ArrayList<String>> clusters = scan.dbScan(1, 20);
      int averageClusterSize = scan.avgClusterSize(clusters);
      ArrayList<String> largestCluster = scan.largestCluster(clusters);
      double endTime = System.nanoTime();
      double elapsedTime = endTime - startTime;
      System.out.println("Largest Cluster Was: " + largestCluster.get(0) + ". Its Size Was: " + largestCluster.size());
      System.out.println("Average Cluster Size Was: " + averageClusterSize);
      System.out.println("Total Runtime Was: " + (elapsedTime*(100000000)) + " Seconds");
   }

   @Test
   public void evaluationTestForAsymmetric() throws FileNotFoundException {
      double startTime = System.nanoTime();
      DBScan scan = new DBScan("com-dblp.ungraph.txt", "asymmetric");
      ArrayList<ArrayList<String>> clusters = scan.dbScan(1, 5);
      int averageClusterSize = scan.avgClusterSize(clusters);
      ArrayList<String> largestCluster = scan.largestCluster(clusters);
      double endTime = System.nanoTime();
      double elapsedTime = endTime - startTime;
      System.out.println("Largest Cluster Was: " + largestCluster.get(0) + ". Its Size Was: " + largestCluster.size());
      System.out.println("Average Cluster Size Was: " + averageClusterSize);
      System.out.println("Total Runtime Was: " + (elapsedTime*(100000000)) + " Seconds");
   }

   @Test
   public void evaluationTestForAsymmetricSmaller() throws FileNotFoundException {
      double startTime = System.nanoTime();
      DBScan scan = new DBScan("smallTest.txt", "asymmetric");
      ArrayList<ArrayList<String>> clusters = scan.dbScan(1, 15);
      int averageClusterSize = scan.avgClusterSize(clusters);
      ArrayList<String> largestCluster = scan.largestCluster(clusters);
      double endTime = System.nanoTime();
      double elapsedTime = endTime - startTime;
      System.out.println("Largest Cluster Was: " + largestCluster.get(0) + ". Its Size Was: " + largestCluster.size());
      System.out.println("Average Cluster Size Was: " + averageClusterSize);
      System.out.println("Total Runtime Was: " + (elapsedTime*(100000000)) + " Seconds");
   }

   /*@Test
   public void test3() throws FileNotFoundException {
      DBScan scan = new DBScan("smallTest.txt");
      scan.dbScan(2, 3);
   }*/

   /*@Test
   public void quickTest() {
      String num1 = "1000";
      String num2 = "210";
      int compare = num1.compareTo(num2);
   }*/
}