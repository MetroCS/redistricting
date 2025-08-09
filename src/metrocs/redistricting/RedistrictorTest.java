package metrocs.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.HashSet;
/**
 * Tests for {@link Redistrictor}.
 *
 * @version 20220103.2
 */
public class RedistrictorTest {
    @Test
    public void invalidRegionParameterConstructorShouldRaiseAnException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Redistrictor(null);
        });
    }

    /**
     * Checks snake-based generation of districts for a square region,
     * verifying only the contiguity of locations in each district.
     */
    @Test
    public void generateDistrictsUsingSnakeTest() {
        Region region;
        Set<District> districtSet;
        region = new Region();
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 1);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
        region = new Region(1);
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 1);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
        region = new Region(4);
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 2);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
        region = new Region(9);
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 1);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 2);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 3);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 4);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
        districtSet = Redistrictor.generateDistrictsUsingSnake(region, 5);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(),
                       "Contiguity error of snaked district " + d);
        }
    }

    @Test
    public void generateDistrictsSquareSingleDistrictTest() {
        Region region;
        Set<District> districtSet;
        region = new Region();
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertThat(districtSet.iterator().next().size(), is(0));
        region = new Region(1);
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertThat(districtSet.iterator().next().size(), is(1));
        region = new Region(4);
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertThat(districtSet.iterator().next().size(), is(4));
        region = new Region(9);
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertThat(districtSet.iterator().next().size(), is(9));
        region = new Region(16);
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertThat(districtSet.iterator().next().size(), is(16));
    }

    /**
     * Checks generation of districts for a square region,
     * verifying only the number of districts, the number of
     * locations per district, and the uniqueness of locations
     * per district.
     * N.B. The contiguity of locations in each district is NOT
     * verified.
     */
    @Test
    public void generateDistrictsSquareAppropriateNumberAndSizeTest() {
        Region region;
        Set<District> districtSet;
        region = new Region();
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertTrue(locationsUnique(districtSet));
        assertThat(districtSet.iterator().next().size(), is(0));

        region = new Region(1);
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertTrue(locationsUnique(districtSet));
        assertThat(districtSet.iterator().next().size(), is(1));

        region = new Region(4);
        districtSet = Redistrictor.generateDistricts(region, 2);
        assertThat(districtSet.size(), is(2));
        assertTrue(locationsUnique(districtSet));
        assertThat(districtSet.iterator().next().size(), is(2));

        region = new Region(9);
        districtSet = Redistrictor.generateDistricts(region, 1);
        assertThat(districtSet.size(), is(1));
        assertTrue(locationsUnique(districtSet));
        assertThat(districtSet.iterator().next().size(), is(9));

        districtSet = Redistrictor.generateDistricts(region, 3);
        assertThat(districtSet.size(), is(3));
        assertTrue(locationsUnique(districtSet));
        assertThat(districtSet.iterator().next().size(), is(3));
        for (District d : districtSet) {
            assertThat(d.size(), is(3));
        }

        districtSet = Redistrictor.generateDistricts(region, 2);
        assertThat(districtSet.size(), is(2));
        assertTrue(locationsUnique(districtSet));
        for (District d : districtSet) {
            assertThat(d.size(), anyOf(is(4), is(5)));
        }
        int numLocations = 0;
        for (District d : districtSet) {
            numLocations += d.size();
        }
        assertThat(numLocations, is(9));

        districtSet = Redistrictor.generateDistricts(region, 4);
        assertThat(districtSet.size(), is(4));
        assertTrue(locationsUnique(districtSet));
        for (District d : districtSet) {
            assertThat(d.size(), anyOf(is(2), is(3)));
        }
        numLocations = 0;
        for (District d : districtSet) {
            numLocations += d.size();
        }
        assertThat(numLocations, is(9));

        districtSet = Redistrictor.generateDistricts(region, 5);
        assertThat(districtSet.size(), is(5));
        assertTrue(locationsUnique(districtSet));
        for (District d : districtSet) {
            assertThat(d.size(), anyOf(is(1), is(2)));
        }
        numLocations = 0;
        for (District d : districtSet) {
            numLocations += d.size();
        }
        assertThat(numLocations, is(9));
    }

    /**
     * Utility to verify that all locations in a collection
     * of districts are unique; that is, no two districts
     * contain the same location.
     * @param districts the districts to consider
     * @return true if all locations are unique; false otherwise
     */
    private static boolean locationsUnique(final Set<District> districts) {
        boolean allUnique = true;
        for (District d : districts) {
            for (Location loc : d.locations()) {
                for (District other : districts) {
                    if (d != other) {
                        allUnique &= !other.locations().contains(loc);
                    }
                }
            }
        }
        return allUnique;
    }

    /**
     * Checks cases where a single district is the
     * appropriate response for all districts of a specified size.
     */
    @Test
    public void allDistrictsOfSpecificSizeSingleDistrictTest() {
        Region region;
        region = new Region();
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 1).size(), is(0));
        region = new Region(1);
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 1).size(), is(1));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 2).size(), is(1));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 4).size(), is(1));
        region = new Region(4);
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 4).size(), is(1));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 9).size(), is(1));
    }

    @Test
    public void allDistrictsOfSpecificSizeTest() {
        Region region;
        region = new Region(4);
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 2).size(), is(4));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 3).size(), is(4));
        region = new Region(9);
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 2).size(), is(12));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 3).size(), is(22));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 4).size(), is(28));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 8).size(), is(5));
        region = new Region(16);
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 2).size(), is(24));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 3).size(), is(52));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 4).size(), is(89));
        region = new Region(25);
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 2).size(), is(40));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 3).size(), is(94));
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 4).size(), is(180));
        region = new Region(64);
        assertThat(Redistrictor.allDistrictsOfSpecificSize(region, 2).size(), is(112));
    }

    /**
     * Checks generation of districts for a square region,
     * verifying only the contiguity of locations in each
     * district.
     */
    @Test
    public void generateDistrictsSquareContiguityTest() {
        Region region;
        Set<District> districtSet;
        region = new Region();
        districtSet = Redistrictor.generateDistricts(region, 1);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Checking contiguity of district " + d);
        }
        region = new Region(1);
        districtSet = Redistrictor.generateDistricts(region, 1);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Checking contiguity of district " + d);
        }

        region = new Region(4);
        districtSet = Redistrictor.generateDistricts(region, 2);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Checking contiguity of district " + d);
        }

        region = new Region(9);
        districtSet = Redistrictor.generateDistricts(region, 1);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Contiguity error for district " + d);
        }
        districtSet = Redistrictor.generateDistricts(region, 2);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Contiguity error for district " + d);
        }
        districtSet = Redistrictor.generateDistricts(region, 3);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Contiguity error for district " + d);
        }
        districtSet = Redistrictor.generateDistricts(region, 4);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Contiguity error for district " + d);
        }
        districtSet = Redistrictor.generateDistricts(region, 5);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Contiguity error for district " + d);
        }
    }

    /**
     * Checks to see that the generateDistricts produces
     * a valid set of districts for a region which should
     * be redistrictable, but is not one contiguous region
     * and has separate components on the same x and y levels.
     */
    @Test
    public void generateDistrictsNonContiguousRegionTest() {
        Region region;
        Set<District> districtSet;
        Set<Voter> voterSet = new HashSet<Voter>();
        Location locationA = new Location(0, 0);
        Location locationB = new Location(1, 0);
        Location locationC = new Location(0, 1);
        Location locationD = new Location(4, 0);
        Location locationE = new Location(5, 0);
        Location locationF = new Location(4, 1);
        voterSet.add(new Voter(null, locationA));
        voterSet.add(new Voter(null, locationB));
        voterSet.add(new Voter(null, locationC));
        voterSet.add(new Voter(null, locationD));
        voterSet.add(new Voter(null, locationE));
        voterSet.add(new Voter(null, locationF));
        region = new Region(voterSet);
        districtSet = Redistrictor.generateDistricts(region, 2);
        for (District d : districtSet) {
            assertTrue(d.contiguityValid(), "Contiguity error for district " + d);
        }
    }
}
