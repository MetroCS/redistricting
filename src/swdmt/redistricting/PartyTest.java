package swdmt.redistricting;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Test;
/**
 * Tests of Party enum.
 *
 * @author  Dr. Jody Paul
 * @version 20191006
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
}
