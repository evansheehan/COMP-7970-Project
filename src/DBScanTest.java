import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by awubis on 11/14/17.
 */
public class DBScanTest {

   @Test
   public void test1() throws FileNotFoundException{
      DBScan scan = new DBScan("com-dblp.ungraph.txt");
   }
}