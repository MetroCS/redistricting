package swdmt.redistricting;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;

import java.util.HashSet;

/**
 * Tests for District objects.
 *
 * @author Dr. Jody Paul
 * @version 20191006
 */
public class DistrictTest {
    @Test
    public void zeroSizeEmptyDistrictTest() {
        District district1 = new District();
        MatcherAssert.assertThat(district1.size(), is(0));
    }

    @Test
    public void contiguityValidEmptyDistrictTest() {
        District district1 = new District();
        Assertions.assertTrue(district1.contiguityValid());
    }

    @Test
    public void nonzeroSizeDistrictTest() {
        HashSet<Location> locations = new HashSet<>();
        locations.add(new Location(0, 0));
        District district1 = new District(locations);
        MatcherAssert.assertThat(district1.size(), is(1));
        locations.add(new Location(1, 0));
        MatcherAssert.assertThat(district1.size(), is(1));
        district1 = new District(locations);
        MatcherAssert.assertThat(district1.size(), is(2));
        locations.add(new Location(42, -5));
        MatcherAssert.assertThat(district1.size(), is(2));
        district1 = new District(locations);
        MatcherAssert.assertThat(district1.size(), is(3));
    }

    @Test
    public void contiguityValidNonemptyDistrictTest() {
        HashSet<Location> locations = new HashSet<>();
        locations.add(new Location(0, 0));
        District district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(1, 0));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(2, 0));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(1, 1));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(1, 2));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(-1, 0));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(-2, 0));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
    }

    @Test
    public void contiguityInvalidNonemptyDistrictTest() {
        HashSet<Location> locations = new HashSet<>();
        locations.add(new Location(0, 0));
        District district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(1, 1));
        district1 = new District(locations);
        Assertions.assertFalse(district1.contiguityValid());
        locations.remove(new Location(1, 1));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());
        locations.add(new Location(-2, 0));
        district1 = new District(locations);
        Assertions.assertFalse(district1.contiguityValid());
    }

    @Test
    public void contiguityValidDistrictsNotContiguous() {
        HashSet<Location> locations = new HashSet<>();
        locations.add(new Location(0, 0));
        locations.add(new Location(0, 1));
        District district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());

        locations.add(new Location(2, 0));
        district1 = new District(locations);
        Assertions.assertFalse(district1.contiguityValid());

        locations.add(new Location(2, 1));
        district1 = new District(locations);
        Assertions.assertFalse(district1.contiguityValid());

        locations.add(new Location(1, 0));
        district1 = new District(locations);
        Assertions.assertTrue(district1.contiguityValid());

    }
}
