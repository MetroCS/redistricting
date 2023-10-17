package metrocs.redistricting;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
/**
 * Tests for objects of type Location in Star Generator.
 *
 * @authors  Colin K, Jesus AE, Thomas M, Robert B
 * @version 20231012.1
 */

class StarGeneratorTest{

    /** Verifies that locations inside pattern generated are not empty */
    @Test
    public void StarGeneratorEmptyTest(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        assertFalse(sp.isEmpty());
    }

    /** Verifies that the Star Generator method is not randomly generating a pattern */
    @Test
    public void StarGeneratorEqualTest(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        ArrayList<ArrayList<Location>> sp2 = StarGenerator.generatePattern(3);
        assertEquals(sp, sp2);
    }
    
    /** Verifies that the intial location start point for the pattern is 0,0 */
    @Test
    public void StarGeneratorLocationStartTest(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        Location l = new Location(0, 0);   
        // assertEquals(sp.get(0).toString(), l.xCoordinate, l.yCoordinate);
        assertEquals(sp.get(0).get(0).xCoordinate(), l.xCoordinate());   
        assertEquals(sp.get(0).get(0).yCoordinate(), l.yCoordinate()); 
    }

    /** Verifies the StarGenerator utilites limit with Java's max integer value */
    //@Test
    public void StarGeneratorMaxN(){

    }
}