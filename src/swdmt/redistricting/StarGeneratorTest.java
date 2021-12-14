package swdmt.redistricting;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class StarGeneratorTest {
    /**
     * Ensure each pattern generated has the correct number of Locations,
     * and there are no duplicates in any of the generated patterns.
     */
    @Test
    public void generatePatternTest() {
        int n = 100;
        ArrayList<ArrayList<Location>> patterns = StarGenerator.generatePattern(n);
        for(ArrayList<Location> pattern: patterns) {
            assertEquals(pattern.size(), n);
            Set<Location> patternSet = new HashSet<>();
            Location previousDistrict = null;
            for(Location currentDistrict: pattern) {
                if(previousDistrict != null) {
                    assertTrue(
                            currentDistrict.xCoordinate() + 1 == previousDistrict.xCoordinate() ||
                                    currentDistrict.xCoordinate() - 1 == previousDistrict.xCoordinate() ||
                                    currentDistrict.yCoordinate() + 1 == previousDistrict.yCoordinate() ||
                                    currentDistrict.yCoordinate() - 1 == previousDistrict.yCoordinate()
                    );
                }
                previousDistrict = currentDistrict;
            }
            pattern.forEach(location -> patternSet.add(location));
            assertEquals(patternSet.size(), pattern.size());
        }
    }
}
