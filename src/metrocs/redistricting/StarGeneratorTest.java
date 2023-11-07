package metrocs.redistricting;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
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

    /** Verifies that locations inside pattern generated where n is = 3 are not empty */
    @Test
    public void StarGeneratorEmptyTest(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        assertFalse(sp.isEmpty());
    }
    
    /** Verifies that for each set of locations where n = 3, that it will contain the position (0,0) */
    @Test
    public void StarGeneratorLocationStartTest(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        Location l = new Location(0, 0);   
        // assertEquals(sp.get(0).toString(), l.xCoordinate, l.yCoordinate);
        for(location : sp){
            assertTrue(sp.get(i).contains(l));
        }
         
    }

    /***
     * Test for StarGenerator pattern of size n=1
     *
     * Expected behavior is that an arraylist<arraylist<location>> of size 1 should be returned
     * which has been verified by hand.
     *
     */
    @Test
    public void StarGeneratorSize1(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(1);
        assertEquals(sp.size(), 1);
    }


    /***
     * Test for StarGenerator pattern of size n=2
     *
     * Expected behavior is that an arraylist<arraylist<location>> of size 4 should be returned
     * which has been verified by hand.
     *
     */
    @Test
    public void StarGeneratorSize2(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(2);
        assertEquals(sp.size(), 4);
    }


    /***
     * Test for StarGenerator pattern of size n=3
     *
     * Expected behavior is that an arraylist<arraylist<location>> of size 12 should be returned
     * which has been verified by hand.
     *
     */
    @Test
    public void StarGeneratorSize3(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(3);
        assertEquals(sp.size(), 12);
    }


    /***
     * Test for StarGenerator pattern of size n=4
     *
     * Expected behavior is that an arraylist<arraylist<location>> of size 36 should be returned
     * which has been verified by hand.
     *
     */
    @Test
    public void StarGeneratorSize4(){
        ArrayList<ArrayList<Location>> sp = StarGenerator.generatePattern(4);
        assertEquals(sp.size(), 36);
    }
}

   

