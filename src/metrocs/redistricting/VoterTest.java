package swdmt.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
/**
 * Tests for Voter objects.
 *
 * @author  Dr. Jody Paul
 * @version 20211120
 */
public class VoterTest {
    /**
    * Test case for affliliation attributes.
    * When passed 'Party.NONE', the same should be returned.
    */
    @Test
    public void affiliationAttributeSpecifiedTest() {
        Voter voter1 = new Voter(Party.NONE, new Location(0, 0));
        assertThat(voter1.affiliation(), is(Party.NONE));
    }

    /**
    * Test case for affliliation attributes when passed null.
    * When passed null, 'Party.NONE' should be returned.
    */
    @Test
    public void affiliationAttributeNullTest() {
        Voter voter2 = new Voter(null,
                                 new Location(Integer.MIN_VALUE,
                                              Integer.MAX_VALUE));
        assertThat(voter2.affiliation(), is(Party.NONE));
    }

    /**
    * Test case for invalid locations.
    * When a new voter is created with no location,
    * throws IllegalArgumentException
    */
    @Test
    public void invalidLocationTest() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            Voter voter1 = new Voter(Party.NONE, null);
        });
    }

    /**
    * Test case for various location attributes.
    * When created with various locations, the passed value should be returned
    */
    @Test
    public void locationAttributeTest() {
        Voter voter1 = new Voter(Party.NONE, new Location(0, 0));
        assertThat(voter1.location(), is(new Location(0, 0)));
        Voter voter2 = new Voter(null,
                                 new Location(Integer.MIN_VALUE,
                                              Integer.MAX_VALUE));
        assertThat(voter2.location().xCoordinate(), is(Integer.MIN_VALUE));
        assertThat(voter2.location().yCoordinate(), is(Integer.MAX_VALUE));
    }
}
