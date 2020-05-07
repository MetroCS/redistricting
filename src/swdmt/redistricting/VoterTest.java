package swdmt.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
/**
 * Tests for Voter objects.
 *
 * @author  Dr. Jody Paul
 * @version 20191006
 */
public class VoterTest {
    @Test
    public void affiliationAttributeSpecifiedTest() {
        Voter voter1 = new Voter(Party.NONE, new Location(0, 0));
        assertThat(voter1.affiliation(), is(Party.NONE));
    }

    @Test
    public void affiliationAttributeNullTest() {
        Voter voter2 = new Voter(null,
                                 new Location(Integer.MIN_VALUE,
                                              Integer.MAX_VALUE));
        assertThat(voter2.affiliation(), is(Party.NONE));
    }

    @Test
    public void invalidLocationTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Voter voter1 = new Voter(Party.NONE, null);
        });
    }

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
