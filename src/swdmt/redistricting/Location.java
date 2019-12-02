package swdmt.redistricting;
/**
 * A distinguished location.
 *
 * @author Dr. Jody Paul
 * @version 20191006.1
 */
public class Location implements Comparable<Object>, java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 3L;

    /** The x-coordinate. */
    private int xCoordinate;
    /** The y-coordinate. */
    private int yCoordinate;

    /**
     * Constructs a location.
     * @param xCoord the x-coordinate value
     * @param yCoord the y-coordinate value
     */
    public Location(final int xCoord, final int yCoord) {
        this.xCoordinate = xCoord;
        this.yCoordinate = yCoord;
    }

    /**
     * Accesses the x-coordinate.
     * @return the x-coordinate value
     */
    public int xCoordinate() {
        return this.xCoordinate;
    }

    /**
     * Accesses the y-coordinate.
     * @return the y-coordinate value
     */
    public int yCoordinate() {
        return this.yCoordinate;
    }

    /**
     * Compares this location with the specified object for order.
     * Returns a negative integer, zero, or a positive integer as this
     * location is less than, equal to, or greater than the specified
     * object.
     *
     * Ordering is primarily determined by natural ordering of the
     * y-coordinate.
     * If y-coordinate values are equal, then order is determined by
     * the natural ordering of the x-coordinate.
     *
     * @param o the object to be compared
     * @return a negative integer, zero, or a positive integer as this
     *     location is less than, equal to, or greater than the
     *     specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents
     *     it from being compared to this location.
     */
    public int compareTo(final Object o) {
        if (null == o) {
            throw new NullPointerException(
                    "Attempt to compare a Location with " + o);
        }
        if (!(o instanceof Location)) {
            throw new ClassCastException(
                    "Attempt to compare a Location with " + o.getClass());
        }
        @SuppressWarnings("unchecked") // Don't warn about required type cast.
        Location that = (Location) o;
        int comparison = 0;
        if (!this.equals(that)) {
            if (this.yCoordinate > that.yCoordinate) {
                comparison = 1;
            } else if (this.yCoordinate < that.yCoordinate) {
                comparison = -1;
            } else if (this.xCoordinate > that.xCoordinate) {
                comparison = 1;
            } else if (this.xCoordinate < that.xCoordinate) {
                comparison = -1;
            } else {
                assert (this.xCoordinate != that.xCoordinate);
            }
        }
        return comparison;
    }

    /**
     * Determines whether or not the specified location
     * is adjacent to this location.
     * Note that a location is not adjacent to itself.
     * @param loc the location to be checked for adjacency
     * @return true if parameter is adjacent; false otherwise
     */
    public boolean isAdjacentTo(final Location loc) {
        boolean adjacent = false;
        if (loc != null) {
            if (Math.abs(this.xCoordinate - loc.xCoordinate) == 1
                    && this.yCoordinate - loc.yCoordinate == 0) {
                adjacent = true;
            } else if (Math.abs(this.yCoordinate - loc.yCoordinate) == 1
                       && this.xCoordinate - loc.xCoordinate == 0) {
                adjacent = true;
            }
        }
        return adjacent;
    }

    /**
     * Determines whether or not the specified Object is equal
     * to this Location.
     * The specified object is equal to this location
     * if it is an instance of Location
     * and if its x-coordinate and y-coordiante values are the same
     * as this location.
     * @param obj an Object to be compared with this Location.
     * @return true if obj is an instance of Location
     *         and has the same coordinate values; false otherwise.
     * @see #hashCode()
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        @SuppressWarnings("unchecked") // Don't warn about required type cast.
        Location that = (Location) obj;
        return (this.xCoordinate == that.xCoordinate)
            && (this.yCoordinate == that.yCoordinate);
    }

    /** Hashcode Base. */
    private static final int HASHBASE1 = 13;
    /** Hashcode Base. */
    private static final int HASHBASE2 = 23;
    /**
     * Returns the hashcode for this Location.
     * @return the hashcode for this Location
     */
    @Override
    public int hashCode() {
        return (HASHBASE1 * this.xCoordinate) + (HASHBASE2 * this.yCoordinate);
    }
}
