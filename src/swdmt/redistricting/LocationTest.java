package swdmt.redistricting;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

/**
 * Tests for objects of type Location.
 *
 * @author  Dr. Jody Paul
 * @version 20191006
 */
public class LocationTest {
    /**
     * Verifies correct behavior for sample coordinate values.
     * Checks some positive, negative, zero, and extreme values.
     */
    @Test
    public void coordinateValuesTest() {
        Location location1 = new Location(4, 2);
        assertThat(location1.xCoordinate(), is(4));
        assertThat(location1.yCoordinate(), is(2));
        Location location2 = new Location(0, 0);
        assertThat(location2.xCoordinate(), is(0));
        assertThat(location2.yCoordinate(), is(0));
        Location location3 = new Location(-42, Integer.MAX_VALUE);
        assertThat(location3.xCoordinate(), is(-42));
        assertThat(location3.yCoordinate(), is(Integer.MAX_VALUE));
    }

    @Test
    public void equalsSelfTest() {
        Location location1 = new Location(17, 23);
        assertTrue(location1.equals(location1));
    }

    @Test
    public void equalsNullTest() {
        Location location1 = new Location(7, 13);
        assertFalse(location1.equals(null));
    }

    @Test
    public void adjacencyTest() {
        Location location1 = new Location(4, 9);
        Location location2 = new Location(5, 9);
        Location location3 = new Location(3, 9);
        Location location4 = new Location(4, 8);
        Location location5 = new Location(4, 10);
        assertTrue(location1.isAdjacentTo(location2));
        assertTrue(location1.isAdjacentTo(location3));
        assertTrue(location1.isAdjacentTo(location4));
        assertTrue(location1.isAdjacentTo(location5));
        Location location6 = new Location(2, 9);
        Location location7 = new Location(6, 9);
        assertFalse(location1.isAdjacentTo(location6));
        assertFalse(location1.isAdjacentTo(location7));
        Location location8 = new Location(4, 7);
        Location location9 = new Location(4, 11);
        assertFalse(location1.isAdjacentTo(location8));
        assertFalse(location1.isAdjacentTo(location9));
    }

    @Test
    public void adjacencyNullTest() {
        Location location1 = new Location(3, 7);
        assertFalse(location1.isAdjacentTo(null));
    }

    @Test
    public void adjacencySelfTest() {
        Location location1 = new Location(3, 7);
        assertFalse(location1.isAdjacentTo(location1));
        Location location2 = new Location(3, 7);
        assertFalse(location1.isAdjacentTo(location2));
    }

    @Test
    public void hashCodeTest() {
        Location location1 = new Location(51, 31);
        int hashCode1 = location1.hashCode();
        assertThat("Returned different hashCode in subsequent calls",
                     location1.hashCode(), is(hashCode1));
        Location location2 = new Location(51, 31);
        assertThat(location2.hashCode(), is(hashCode1));
    }

    @Test
    public void verifyCompareToEqualsTest() {
        Location location1 = new Location(10, 20);
        Location location2 = new Location(10, 20);
        assertThat(location1.compareTo(location2), is(0));
        assertThat(location2.compareTo(location1), is(0));
        location1 = new Location(0, 0);
        location2 = new Location(0, 0);
        assertThat(location1.compareTo(location2), is(0));
        assertThat(location2.compareTo(location1), is(0));
        Location locationXtremeA
                = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE);
        Location locationXtremeB
                = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(locationXtremeA.compareTo(locationXtremeB), is(0));
        assertThat(locationXtremeB.compareTo(locationXtremeA), is(0));
        locationXtremeA = new Location(Integer.MAX_VALUE, Integer.MIN_VALUE);
        locationXtremeB = new Location(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertThat(locationXtremeA.compareTo(locationXtremeB), is(0));
        assertThat(locationXtremeB.compareTo(locationXtremeA), is(0));
    }

    @Test
    public void verifyCompareToSelfTest() {
        Location location1 = new Location(10, 20);
        assertThat(location1.compareTo(location1), is(0));
        location1 = new Location(0, 0);
        assertThat(location1.compareTo(location1), is(0));
        location1 = new Location(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertThat(location1.compareTo(location1), is(0));
    }

    @Test
    public void verifyCompareToLessThanYTest() {
        Location location1 = new Location(10, 20);
        Location location2 = new Location(10, 50);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(50, 30);
        location2 = new Location(10, 31);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE - 1);
        location2 = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(99, Integer.MIN_VALUE);
        location2 = new Location(88, Integer.MAX_VALUE);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(Integer.MAX_VALUE - 1, Integer.MIN_VALUE);
        location2 = new Location(Integer.MAX_VALUE, Integer.MIN_VALUE + 1);
        assertThat(location1.compareTo(location2), lessThan(0));
    }

    @Test
    public void verifyCompareToGreaterThanYTest() {
        Location location1 = new Location(100, 42);
        Location location2 = new Location(100, -42);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(50, 103);
        location2 = new Location(99, 102);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE);
        location2 = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE - 1);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(99, Integer.MAX_VALUE);
        location2 = new Location(88, Integer.MIN_VALUE);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1);
        location2 = new Location(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertThat(location1.compareTo(location2), greaterThan(0));
    }

    @Test
    public void verifyCompareToLessThanXTest() {
        Location location1 = new Location(20, 10);
        Location location2 = new Location(50, 10);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(30, 0);
        location2 = new Location(31, 0);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE);
        location2 = new Location(Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(Integer.MIN_VALUE, 42);
        location2 = new Location(Integer.MAX_VALUE, 42);
        assertThat(location1.compareTo(location2), lessThan(0));
        location1 = new Location(Integer.MAX_VALUE - 1, Integer.MIN_VALUE);
        location2 = new Location(Integer.MAX_VALUE, Integer.MIN_VALUE);
        assertThat(location1.compareTo(location2), lessThan(0));
    }

    @Test
    public void verifyCompareToGreaterThanXTest() {
        Location location1 = new Location(42, 100);
        Location location2 = new Location(-42, 100);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(199, 0);
        location2 = new Location(198, 0);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
        location2 = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(Integer.MAX_VALUE, 99);
        location2 = new Location(Integer.MIN_VALUE, 99);
        assertThat(location1.compareTo(location2), greaterThan(0));
        location1 = new Location(Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
        location2 = new Location(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertThat(location1.compareTo(location2), greaterThan(0));
    }
}

