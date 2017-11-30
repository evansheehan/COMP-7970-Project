import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by awubis on 11/14/17.
 */
public class DBScanTest {

   @Test
   public void test1() throws FileNotFoundException {
      DBScan scan = new DBScan("CA-GrQc.txt");
      scan.dbScan(2, 3);
   }

   @Test
   public void test2() throws FileNotFoundException {
      DBScan scan = new DBScan("smallTest.txt");
      scan.dbScan(2, 3);
   }
}