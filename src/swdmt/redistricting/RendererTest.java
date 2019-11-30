package swdmt.redistricting;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * The test class for Renderer.
 *
 * <p>The renderAsASCII tests presume use of the following characters
 * <PRE>Symbol -- Meaning
 * * -- Arbitrary content
 * ? -- Affiliation unknown
 * U -- Unaffiliated
 * 0 -- Party 0
 * 1 -- Party 1
 * T -- Third Party
 * </PRE>
 * @author  Dr. Jody Paul
 * @version 20191127
 */
public class RendererTest {
    /** Check rendering of region of size 1. */
    @Test
    public void renderAsASCII_regionSize1_Test() {
        Region region1 = new Region(1);
        assertEquals("+---+\n"
                   + "| * |\n"
                   + "+---+\n",
                     Renderer.renderAsASCII(region1));
    }

    @Test
    public void renderAsASCII_regionSize1_Affiliation_Test() {
        Region region1 = new Region(1);
        assertEquals("+---+\n"
                   + "| ? |\n"
                   + "+---+\n",
                     Renderer.renderAsASCII(true, region1));
    }

    @Test
    public void renderAsASCII_regionSize16_Test() {
        Region region2 = new Region(16);
        assertEquals("+---+---+---+---+\n"
                   + "| * | * | * | * |\n"
                   + "+---+---+---+---+\n"
                   + "| * | * | * | * |\n"
                   + "+---+---+---+---+\n"
                   + "| * | * | * | * |\n"
                   + "+---+---+---+---+\n"
                   + "| * | * | * | * |\n"
                   + "+---+---+---+---+\n",
                     Renderer.renderAsASCII(region2));
    }

    @Test
    public void renderAsASCII_regionSize16_Affiliation_Test() {
        Region region2 = new Region(16);
        assertEquals("+---+---+---+---+\n"
                   + "| ? | ? | ? | ? |\n"
                   + "+---+---+---+---+\n"
                   + "| ? | ? | ? | ? |\n"
                   + "+---+---+---+---+\n"
                   + "| ? | ? | ? | ? |\n"
                   + "+---+---+---+---+\n"
                   + "| ? | ? | ? | ? |\n"
                   + "+---+---+---+---+\n",
                     Renderer.renderAsASCII(true, region2));
    }

    @Test
    public void renderAsASCII_regionLocations3_GridSize36_Test() {
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
                   + "| * |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   | * |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   | * |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n",
                     Renderer.renderAsASCII(region3));
    }

    @Test
    public void renderAsASCII_regionLocations3_GridSize36_Affiliation_Test() {
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
                   + "| 0 |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   | U |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   |   |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n"
                   + "|   |   | 1 |   |   |   |\n"
                   + "+---+---+---+---+---+---+\n",
                     Renderer.renderAsASCII(true, region3));
    }
}
