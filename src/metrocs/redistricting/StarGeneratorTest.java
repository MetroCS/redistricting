package metrocs.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
/**
 * Tests for Voter objects.
 *
 * @author Jada, David, Colin, John, Daniel, Ali
 * @version
 */
public class StarGeneratorTest {
    @Test
    public void generatePatternTest() {
        ArrayList<ArrayList<Location>> firstLocation = new ArrayList<ArrayList<Location>>();
        ArrayList<ArrayList<Location>> secondLocation = new ArrayList<ArrayList<Location>>();
        //check if method returns null
        assertNotNull(StarGenerator.generatePattern(1));
        assertNotNull(StarGenerator.generatePattern(4));
        //assign same parameter, test if same result
        firstLocation = StarGenerator.generatePattern(4);
        secondLocation = StarGenerator.generatePattern(4);
        assertEquals(firstLocation,secondLocation);
        //assign different parameter, test if different result

    }

}
