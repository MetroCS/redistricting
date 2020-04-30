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
    public void votersOnlySquareRegionTest() {
        int numVotersSquare = 4;
        Set<Voter> voters = new HashSet<>();
        for (int i = 0; i < numVotersSquare; i++) {
            for (int k = 0; k < numVotersSquare; k++){
                voters.add(new Voter(null, new Location(i, k)));
            }
        }
        Region newRegion = new Region(voters);
    }

    @Test(timeout = MAX_TIMEOUT)
    public void votersOnlyNotSquareRegionTest() {
        int numVoters = 5;
        Set<Voter> voters = new HashSet<>();
        for (int i = 0; i < numVoters; i++) {
            for (int k = 0; k < numVoters; k++) {
                voters.add(new Voter(null, new Location(i, k)));
            }
        }
        Region newRegion = new Region(voters);
    }
}
