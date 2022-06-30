package metrocs.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.time.Duration.ofMillis;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
/**
 * Tests for class Region.
 *
 * @author  Dr. Jody Paul
 * @author  CS3250 Participants
 * @version 20220629.0
 */
public class RegionTest {
    /** The maximum size region to test. */
    private static final int MAX_REGION_SIZE = 400;
    /** The maximum time (milliseconds) to wait for a test to complete. */
    private static final long MAX_TIMEOUT = 1000L;

    /**
     * Default constructor for test class RegionTest.
     */
    public RegionTest() {
    }

    /**
    * Test case for Region(Collection<Voter>)
    */
    @Test
    public void voterRegionInputConstructorTest() {
        Voter voterA = new Voter(Party.PARTY0, new Location(0, 0));
        Voter voterB = new Voter(Party.PARTY1, new Location(1, 1));
        Collection<Voter> voters = new HashSet<Voter>();
        voters.add(voterA);
        voters.add(voterB);
        Region region = new Region(voters);
        assertThat(region.voters().size(), is(2));
        assertThat(region.locations().size(), is(2));
        for (Voter v : voters) {
            assertTrue(region.voters().contains(v));
            assertTrue(region.locations().contains(v.location()));
        }
    }

   /**
    * Test case for empty region sizes.
    * Should return 0 with various empty or 0 or 0,0 parameters.
    */
    @Test
    public void emptyRegionSizeTest() {
        assertThat((new Region()).size(), is(0));
        assertThat((new Region(0)).size(), is(0));
        assertThat((new Region(-0)).size(), is(0));
        assertThat((new Region(0, 0)).size(), is(0));
    }

    /**
    * Test case for empty number of voters in a region.
    * Should return 0 with various empty or 0 or 0,0 parameters.
    */
    @Test
    public void emptyRegionNumberOfVotersTest() {
        assertThat((new Region()).numberOfVoters(), is(0));
        assertThat((new Region(0)).numberOfVoters(), is(0));
        assertThat((new Region(-0)).numberOfVoters(), is(0));
        assertThat((new Region(0, 0)).numberOfVoters(), is(0));
    }

    /**
    * Test case for negative square regions.
    * When a negative square region is created,
    * throws IllegalArgumentException.
    */
    @Test
    public void squareRegionNegativeSizeTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(-4);
        });
    }

    /**
    * Test case for negative rectangular regions.
    * When a negative parameter for a region number of rows or columns is passed,
    * throws IllegalArgumentException.
    */
    @Test
    public void rectangularRegionNegativeSizeTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(-4, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(5, -6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(-7, -8);
        });
    }

    /**
     * Test case for non-square regions using square constructor.
     * When a new region is requested with a non-square number,
     * throws IllegalArgumentException
     */
    @Test
    public void squareRegionNonSquareSizeTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(15);
        });
    }

    /**
    * Test case for non-square regions using square constructor,
    * specifically with parameter of 3.
    * When a new region is made with the number 3,
    * throws IllegalArgumentException.
    */
    @Test
    public void squareRegionNonSquareSizeTestNum3() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(3);
        });
    }

    /**
    * Test case for extreme region sizing.
    * When a region is created of the max value,
    * throws IllegalArgumentException.
    */
    @Test
    public void squareRegionExtremeSizeTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(Integer.MAX_VALUE);
        });
    }


    /**
    * Test case for valid rectangular region size.
    * Should complete before 1000 milliseconds have passed.
    */
    @Test
    public void rectangularRegionValidSizeTest() {
        assertTimeout(ofMillis(MAX_TIMEOUT), () -> {
            int maxRegionSide = (int) Math.sqrt(MAX_REGION_SIZE);
            // Test an arbitrary case.
            assertThat("Rectangular region 5 x 4 wrong size",
                       ((new Region(5, 4)).size()), is(5 * 4));
            // Test regions with same length sides.
            for (int i = 0; i < maxRegionSide; i++) {
                assertThat("Rectangular region test failed for ("
                           + i + "," + i + ")",
                           i * i, is((new Region(i, i)).size()));
            }
            // Test regions with differing length sides.
            for (int r = 0; r < maxRegionSide / 2; r++) {
                assertThat("Rectangular region test failed for ("
                           + r + "," + (r * 2) + ")",
                           r * r * 2, is((new Region(r, r * 2)).size()));
            }
        });
    }

    /**
    * Test case for valid square region size.
    * Should complete before 1000 milliseconds have passed.
    */
    @Test
    public void squareRegionValidSizeTest() {
        assertTimeout(ofMillis(MAX_TIMEOUT), () -> {
            int maxRegionSide = (int) Math.sqrt(MAX_REGION_SIZE);
            for (int i = 0; i < maxRegionSide; i++) {
                assertThat("Square region test failed at size " + i * i,
                           i * i, is((new Region(i * i)).size()));
            }
        });
    }

    /**
     * Test case for number of voters in a rectangular region.
     * Should complete before MAX_TIMEOUT expires.
     */
    @Test
    public void rectangularRegionNumberOfVotersTest() {
        assertTimeout(ofMillis(MAX_TIMEOUT), () -> {
            int maxRegionSide = (int) Math.sqrt(MAX_REGION_SIZE);
            // Test an arbitrary case.
            assertThat("Rectangular region 5 x 4 wrong number of voters",
                       ((new Region(5, 4)).numberOfVoters()), is(5 * 4));
            // Test regions with same length sides.
            for (int i = 0; i < maxRegionSide; i++) {
                assertThat("Rectangular region number of voters failed for ("
                           + i + "," + i + ")",
                           i * i, is((new Region(i, i)).numberOfVoters()));
            }
            // Test regions with differing length sides.
            for (int r = 0; r < maxRegionSide / 2; r++) {
                assertThat("Rectangular region number of voters failed for ("
                           + r + "," + (r * 2) + ")",
                           r * r * 2, is((new Region(r, r * 2)).numberOfVoters()));
            }
        });
    }

    /**
    * Test case for number of voters in a square region.
    * Should complete before MAX_TIMEOUT expires.
    */
    @Test
    public void squareRegionNumberOfVotersTest() {
        assertTimeout(ofMillis(MAX_TIMEOUT), () -> {
            int maxRegionSide = (int) Math.sqrt(MAX_REGION_SIZE);
            for (int i = 0; i < maxRegionSide; i++) {
                assertThat("Square region number of voters failed at size "
                           + i * i,
                           (new Region(i * i)).numberOfVoters(), is(i * i));
            }
        });
    }

    /**
    * Test case for voters in location set.
    * Should complete before MAX_TIMEOUT expires.
    */
    @Test
    public void regionWithAllVotersInLocationSetTest() {
        assertTimeout(ofMillis(MAX_TIMEOUT), () -> {
            Location location1 = new Location(3, 3);
            Location location2 = new Location(0, 1);
            Voter voter1 = new Voter(Party.NONE, location1);
            Voter voter2 = new Voter(Party.PARTY0, location2);

            Set<Location> locationSet = new HashSet<Location>();
            locationSet.add(location1);
            locationSet.add(location2);

            Set<Voter> voterSet = new HashSet<Voter>();
            voterSet.add(voter1);
            voterSet.add(voter2);

            Region region = new Region(locationSet, voterSet);
            assertThat("Region with all voters in location set test failed; "
                           + "Location and Voter sets of size 2 produced Region "
                           + "with wrong size.",
                       region.size(), is(2));
            assertThat("Region with all voters in location set test failed; "
                           + "Location and Voter sets of size 2 produced Region "
                           + "with wrong number of voters.",
                       region.numberOfVoters(), is(2));
        });
    }

    /**
    * Test case for voters not in the location set.
    * Should complete before MAX_TIMEOUT expires.
    */
    @Test
    public void regionWithVoterNotInLocationSetTest() {
        assertTimeout(ofMillis(MAX_TIMEOUT), () -> {
            Location location3 = new Location(0, 3);
            Voter voter3 = new Voter(Party.NONE, new Location(0, 2));

            Set<Location> locationSet = new HashSet<Location>();
            locationSet.add(location3);

            Set<Voter> voterSet = new HashSet<Voter>();
            voterSet.add(voter3);

            Region region = new Region(locationSet, voterSet);
            assertThat("Region with no voters in the location set test failed; "
                           + "given Location set size 1 and Voter set size 1 produced "
                           + "Region with wrong size.",
                       region.size(), is(1));
            assertThat("Region with no voters in the location set test failed; "
                           + "given Location set size 1 and Voter set size 1 produced "
                           + "wrong number of voters in the region.",
                       region.numberOfVoters(), is(0));
        });
    }
}
