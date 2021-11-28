package swdmt.redistricting;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import java.util.HashSet;
/**
 * Tests for class Region.
 *
 * @author  Dr. Jody Paul
 * @version 20191006
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
    @Before
    public void setUp() {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
    }

    @Test
    public void emptyRegionSizeTest() {
        assertThat((new Region()).size(), is(0));
        assertThat((new Region(0)).size(), is(0));
        assertThat((new Region(-0)).size(), is(0));
    }

    @Test
    public void emptyRegionNumberOfVotersTest() {
        assertThat((new Region()).numberOfVoters(), is(0));
        assertThat((new Region(0)).numberOfVoters(), is(0));
        assertThat((new Region(-0)).numberOfVoters(), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void squareRegionNegativeSizeTest() {
        Region r = new Region(-4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void squareRegionNonSquareSizeTest() {
        Region r = new Region(15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void squareRegionNonSquareSizeTestNum3() {
        Region r = new Region(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void squareRegionExtremeSizeTest() {
        Region r = new Region(Integer.MAX_VALUE);
    }

    @Test(timeout = MAX_TIMEOUT)
    public void squareRegionValidSizeTest() {
        int maxRegionSide = (int) Math.sqrt(MAX_REGION_SIZE);
        for (int i = 0; i < maxRegionSide; i++) {
            assertThat("Square region test failed at size " + i * i,
                       i * i, is((new Region(i * i)).size()));
        }
    }

    @Test(timeout = MAX_TIMEOUT)
    public void squareRegionNumberOfVotersTest() {
        int maxRegionSide = (int) Math.sqrt(MAX_REGION_SIZE);
        for (int i = 0; i < maxRegionSide; i++) {
            assertThat("Square region number of voters failed at size " + i * i,
                       (new Region(i * i)).numberOfVoters(), is(i * i));
        }
    }

    @Test(timeout = MAX_TIMEOUT)
    public void regionWithAllVotersInLocationSetTest() {
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
    }

    @Test(timeout = MAX_TIMEOUT)
    public void regionWithVoterNotInLocationSetTest() {
        Location location3 = new Location(0,3);
        Voter voter3 = new Voter(Party.NONE, new Location(0,2));

        Set<Location> locationSet = new HashSet<Location>();
        locationSet.add(location3);

        Set<Voter> voterSet = new HashSet<Voter>();
        voterSet.add(voter3);

        Region region = new Region(locationSet, voterSet);
        assertThat("Region with no voters in the location set test failed; given Location set size 1 and Voter set size 1 produced Region with wrong size.", region.size(), is(1));
        assertThat("Region with no voters in the location set test failed; given Location set size 1 and Voter set size 1 produced wrong number of voters in the region.", region.numberOfVoters(), is(0));
    }
}
