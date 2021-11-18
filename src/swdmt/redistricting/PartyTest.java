package swdmt.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
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
        assertThat(Party.valueOf("NONE"), notNullValue());
        assertThat(Party.valueOf("UNAFFILIATED"), notNullValue());
    }

    /**
     * Party single-character identification requirements:
     * NONE uses '?'; UNAFFILIATED uses '*'.
     */
    @Test
    public void verifyPartyID() {
        assertThat(Party.valueOf("NONE").id(), is('?'));
        assertThat(Party.valueOf("UNAFFILIATED").id(), is('U'));
    }
}
