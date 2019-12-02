package swdmt.redistricting;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * The test class for Renderer.
 *
 * @author  Dr. Jody Paul
 * @version 20191127.1
 */
public class RendererTest {
    /** Render of an arbitrary location. */
    private static final char R_AR = '*';
    /** Render of a location with NONE voter. */
    private static final char R_NO = Party.NONE.id();
    /** Render of a location with UNAFFILIATED voter. */
    private static final char R_UN = Party.UNAFFILIATED.id();
    /** Render of a location with PARTY0 voter. */
    private static final char R_P0 = Party.PARTY0.id();
    /** Render of a location with PARTY1 voter. */
    private static final char R_P1 = Party.PARTY1.id();
    /** Render of a location with THIRDPARTY voter. */
    private static final char R_TP = Party.THIRDPARTY.id();

    /** Check rendering of region of size 1, no affiliations. */
    @Test
    public void renderAsASCIIRegionSize1Test() {
        Region region1 = new Region(1);
        assertEquals("+---+\n"
                   + "| " + R_AR + " |\n"
                   + "+---+\n",
                     Renderer.renderAsASCII(region1));
    }

    /** Check rendering of region of size 1 with affiliation. */
    @Test
    public void renderAsASCIIRegionSize1AffiliationTest() {
        Region region1 = new Region(1);
        assertEquals("+---+\n"
                   + "| " + R_NO + " |\n"
                   + "+---+\n",
                     Renderer.renderAsASCII(true, region1));
    }

    /** Check rendering of region of size 16, no affiliations. */
    @Test
    public void renderAsASCIIRegionSize16Test() {
        Region region2 = new Region(16);
        assertEquals("+---+---+---+---+\n"
                   + "| " + R_AR + " | " + R_AR + " | " + R_AR + " | " + R_AR + " |\n"
                   + "+---+---+---+---+\n"
                   + "| " + R_AR + " | " + R_AR + " | " + R_AR + " | " + R_AR + " |\n"
                   + "+---+---+---+---+\n"
                   + "| " + R_AR + " | " + R_AR + " | " + R_AR + " | " + R_AR + " |\n"
                   + "+---+---+---+---+\n"
                   + "| " + R_AR + " | " + R_AR + " | " + R_AR + " | " + R_AR + " |\n"
                   + "+---+---+---+---+\n",
                     Renderer.renderAsASCII(region2));
    }

    /** Check rendering of region of size 16 with affiliation. */
    @Test
    public void renderAsASCIIregionSize16AffiliationTest() {
        Region region2 = new Region(16);
        assertEquals("+---+---+---+---+\n"
                   + "| " + R_NO + " | " + R_NO + " | " + R_NO + " | " + R_NO + " |\n"
                   + "+---+---+---+---+\n"
                   + "| " + R_NO + " | " + R_NO + " | " + R_NO + " | " + R_NO + " |\n"
                   + "+---+---+---+---+\n"
                   + "| " + R_NO + " | " + R_NO + " | " + R_NO + " | " + R_NO + " |\n"
                   + "+---+---+---+---+\n"
                   + "| " + R_NO + " | " + R_NO + " | " + R_NO + " | " + R_NO + " |\n"
                   + "+---+---+---+---+\n",
                     Renderer.renderAsASCII(true, region2));
    }

    /** Check rendering of region of size 36, with 3 locations, no affiliations. */
    @Test
    public void renderAsASCIIRegionLocations3GridSize36Test() {
        Location location00 = new Location(0, 0);
        Location location25 = new Location(2, 5);
        Location location53 = new Location(5, 3);
        Set<Location> testLocs = new TreeSet<Location>();
        testLocs.add(location00);
        testLocs.add(location25);
        testLocs.add(location53);
        Voter voter1 = new Voter(Party.PARTY0, location00);
        Voter voter2 = new Voter(Party.PARTY1, location25);
        Voter voter3 = new Voter(Party.PARTY1, location53);
        Set<Voter> testVoters = new HashSet<Voter>();
        testVoters.add(voter1);
        testVoters.add(voter2);
        testVoters.add(voter3);
        Region region3 = new Region(testLocs, testVoters);
        assertEquals("+---+---+---+---+---+---+\n"
                   + "| " + R_AR + " |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   | " + R_AR + " |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   | " + R_AR + " |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n",
                     Renderer.renderAsASCII(region3));
    }

    /**
     * Check rendering of region of size 36,
     * with 3 locations, showing affiliations.
     */
    @Test
    public void renderAsASCIIRegionLocations3GridSize36AffiliationTest() {
        Location location00 = new Location(0, 0);
        Location location25 = new Location(2, 5);
        Location location53 = new Location(5, 3);
        Set<Location> testLocs = new TreeSet<Location>();
        testLocs.add(location00);
        testLocs.add(location25);
        testLocs.add(location53);
        Voter voter1 = new Voter(Party.PARTY0, location00);
        Voter voter2 = new Voter(Party.PARTY1, location25);
        Voter voter3 = new Voter(Party.UNAFFILIATED, location53);
        Set<Voter> testVoters = new HashSet<Voter>();
        testVoters.add(voter1);
        testVoters.add(voter2);
        testVoters.add(voter3);
        Region region3 = new Region(testLocs, testVoters);
        assertEquals("+---+---+---+---+---+---+\n"
                   + "| " + R_P0 + " |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   | " + R_UN + " |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   | " + R_P1 + " |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n",
                     Renderer.renderAsASCII(true, region3));
    }
}
