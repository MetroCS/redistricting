package swdmt.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static java.time.Duration.ofMillis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.TreeSet;
/**
 * Tests for class Region.
 *
 * @author  Dr. Jody Paul
 * @version 20211130
 */
public class RegionTest {
    /** The maximum size region to test. */
    private static final int MAX_REGION_SIZE = 400; // 160000;
    /** The maximum time (milliseconds) to wait for a test to complete. */
    private static final long MAX_TIMEOUT = 1000L; // 20000L;

    /**
     * Default constructor for test class RegionTest.
     */
    public RegionTest() {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
    * Test case for empty region sizes.
    * Should return 0 with various empty or 0 inputs.
    */
    @Test
    public void emptyRegionSizeTest() {
        assertThat((new Region()).size(), is(0));
        assertThat((new Region(0)).size(), is(0));
        assertThat((new Region(-0)).size(), is(0));
    }

    /**
    * Test case for empty number of voters in a region.
    * Should return 0 with various empty or 0 inputs.
    */
    @Test
    public void emptyRegionNumberOfVotersTest() {
        assertThat((new Region()).numberOfVoters(), is(0));
        assertThat((new Region(0)).numberOfVoters(), is(0));
        assertThat((new Region(-0)).numberOfVoters(), is(0));
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
    * Test case for non-square regions.
    * When a new region is made with a non-square number,
    * throws IllegalArgumentException
    */
    @Test
    public void squareRegionNonSquareSizeTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            Region r = new Region(15);
        });
    }

    /**
    * Test case for non-square regions, specifically with 3.
    * When a new region is made with the number 3,
    * throws IllegalArgumentException.
    */
    @Test(expected = IllegalArgumentException.class)
    public void squareRegionNonSquareSizeTestNum3() {
        Region r = new Region(3);
    }
  
    /**
    * Test case for extreme region sizing.
    * When a region is created of the max value,
    * throws IllegalArgumentException.
    */
    @Test(expected = IllegalArgumentException.class)
    public void squareRegionExtremeSizeTest() {
        Region r = new Region(Integer.MAX_VALUE);

    /**
    * Test case for valid region size.
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
    * Test case for number of voters in a square region.
    * Should complete before 1000 milliseconds have passed.
    */
    @Test
    public void squareRegionNumberOfVotersTest() {
        assertTimeout(ofMillis(MAX_TIMEOUT), () -> {
            int maxRegionSide = (int) Math.sqrt(MAX_REGION_SIZE);
            for (int i = 0; i < maxRegionSide; i++) {
                assertThat("Square region number of voters failed at size " + i * i,
                           (new Region(i * i)).numberOfVoters(), is(i * i));
            }
        });
    }

    /*
    In this test we test the constructor for Region.java that uses one parameter of a collection of voters. Where
    we tested the size of the region, the number of voters and the location of voters.
     */
    @Test(timeout = MAX_TIMEOUT)
    public void voterCollectionTest() {
        Collection<Voter> regionVoter = new HashSet<>();
        Set<Location> regionLocation = new TreeSet<>();
        Location location1 = new Location(0, 0);
        Location location2 = new Location(0, 1);
        Location location3 = new Location(1, 0);
        Location location4 = new Location(1, 1);
        Voter voter1 = new Voter(Party.NONE, location1);
        Voter voter2 = new Voter(Party.PARTY0, location2);
        Voter voter3 = new Voter(Party.PARTY1, location3);
        Voter voter4 = new Voter(Party.NONE, location4);
        regionVoter.add(voter1);
        regionVoter.add(voter2);
        regionVoter.add(voter3);
        regionVoter.add(voter4);
        regionLocation.add(location1);
        regionLocation.add(location2);
        regionLocation.add(location3);
        regionLocation.add(location4);
        Region region = new Region(regionVoter);
        assertThat(4, is(region.size()));
        assertThat(4, is(region.numberOfVoters()));
        assertThat(regionLocation, is(region.locations()));
        assertThat(regionVoter, is(region.voters()));
        }

    /**
    * Test case for voters in location set.
    * Should complete before 1000 milliseconds have passed.
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
            assertThat("Region with all voters in location set test failed; Location and Voter sets of size 2 produced Region with wrong size.", region.size(), is(2));
            assertThat("Region with all voters in location set test failed; Location and Voter sets of size 2 produced Region with wrong number of voters.", region.numberOfVoters(), is(2));
        });
    }

    /**
    * Test case for voters not in the location set.
    * Should complete before 1000 milliseconds have passed.
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
            assertThat("Region with no voters in the location set test failed; given Location set size 1 and Voter set size 1 produced Region with wrong size.", region.size(), is(1));
            assertThat("Region with no voters in the location set test failed; given Location set size 1 and Voter set size 1 produced wrong number of voters in the region.", region.numberOfVoters(), is(0));
        });
    }
}
