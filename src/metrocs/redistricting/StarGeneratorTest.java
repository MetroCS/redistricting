package metrocs.redistricting;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
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
        //check if method returns null
        assertNotNull(StarGenerator.generatePattern(1));
        assertNotNull(StarGenerator.generatePattern(4));
    }

}
