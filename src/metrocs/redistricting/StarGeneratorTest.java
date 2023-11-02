package metrocs.redistricting;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
/**
 * Tests for objects of type Location in Star Generator.
 *
 * @authors  Colin K, Jesus AE, Thomas M, Robert B
 * @version 20231012.1
 */

class StarGeneratorTest{

    /** Verifies that locations inside pattern generated where n is >= 1 are not empty */
    @Test
    public void StarGeneratorEmptyTest(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        assertFalse(sp.isEmpty());
    }
    
    /** Verifies that for each set of locations, that it will contain the position (0,0) */
    @Test
    public void StarGeneratorLocationStartTest(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        Location l = new Location(0, 0);   
        // assertEquals(sp.get(0).toString(), l.xCoordinate, l.yCoordinate);
        for(int i = 0; i < sp.size(); i++){
            assertTrue(sp.get(i).contains(l));
        }
         
    }
}