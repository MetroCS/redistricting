package swdmt.redistricting;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests of Party enum.
 *
 * @author  Dr. Jody Paul
 * @version 20191129
 */
public class PartyTest {
    /**
     * Party must include NONE and UNAFFILIATED.
     */
    @Test
    public void verifyPartyBasics() {
        MatcherAssert.assertThat(Party.valueOf("NONE"), notNullValue());
        MatcherAssert.assertThat(Party.valueOf("UNAFFILIATED"), notNullValue());
    }

    /**
     * Party single-character identification requirements:
     * NONE uses '?'; UNAFFILIATED uses '*'.
     */
    @Test
    public void verifyPartyID() {
        MatcherAssert.assertThat(Party.valueOf("NONE").id(), is('?'));
        MatcherAssert.assertThat(Party.valueOf("UNAFFILIATED").id(), is('U'));
    }
}
