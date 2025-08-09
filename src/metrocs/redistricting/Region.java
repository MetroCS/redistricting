package metrocs.redistricting;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collection;
/**
 * A region represents a politically-defined area comprised
 * of locations and voters that occupy those locations.
 * The region respects the association between locations and
 * voters as given by each voters location attribute.
 * Each voter may be associated with at most one location.
 * Zero or more voters may be associated with each location.
 *
 * @author Dr. Jody Paul
 * @version 20240807.0
 */
public class Region implements java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 4L;

    /** The locations comprising this region. */
    private TreeSet<Location> locations = new TreeSet<>();

    /** The voters comprising this region. */
    private HashSet<Voter> voters = new HashSet<>();

    /** The association of locations with voters. */
    private HashMap<Location, Voter> voterMap = new HashMap<>();

    /**
     * Computes a default length of the side of the grid containing this region
     * assuming the region is a square.
     * @return the length of a side
     */
    public int sideSize() {
        int numberOfLocations = locations.size();
        int sideSize = (int) Math.round(Math.sqrt(numberOfLocations));
        return sideSize;
    }

    /**
     * Creates a zero-sized region.
     */
    public Region() {
        this.locations = new TreeSet<>();
        this.voters = new HashSet<>();
        this.voterMap = new HashMap<>();
    }

    /**
     * Creates a rectangular region of contiguous locations,
     * using the specified number of rows and columns,
     * with a location at each row-column intersection,
     * and exactly one voter at each location.
     * Location indices start at (0, 0) and increment monotonically
     * in row major order.
     * @param numberOfRows the desired number of rows
     * @param numberOfColumns the desired number of columns
     * @throws IllegalArgumentException if any parameter is negative
     */
    public Region(final int numberOfRows, final int numberOfColumns) {
        if ((numberOfRows < 0) || (numberOfColumns < 0)) {
            throw new IllegalArgumentException(
                "Invalid attempt to create rectangular region of size "
                + numberOfRows + " rows by " + numberOfColumns + " columns");
        }
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Location loc = new Location(r, c);
                this.locations.add(loc);
                this.voters.add(new Voter(Party.NONE, loc));
            }
        }
    }

    /**
     * Creates a square region of contiguous locations,
     * with the specified number of locations,
     * and exactly one voter at each location.
     * Location indices start at (0, 0) and increment monotonically.
     * @param numberOfLocations the desired number of locations
     * @throws IllegalArgumentException if the parameter is not a perfect square
     */
    public Region(final int numberOfLocations) {
        int sideSize = (int) Math.round(Math.sqrt(numberOfLocations));
        if (numberOfLocations != (int) Math.round(Math.pow(sideSize, 2.0))) {
            throw new IllegalArgumentException(
                "Invalid attempt to create square region of size "
                + numberOfLocations);
        }
        for (int i = 0; i < sideSize; i++) {
            for (int j = 0; j < sideSize; j++) {
                Location loc = new Location(i, j);
                this.locations.add(loc);
                this.voters.add(new Voter(Party.NONE, loc));
            }
        }
    }

   /**
    * Creates a region based on a Collection of Voters.
    * Uses each voters information to update location, voters and voterMap.
    * @param voterCollection is a Collection of Voters
    */
    public Region(final Collection<Voter> voterCollection) {
        this.locations = new TreeSet<>();
        this.voters = new HashSet<>();
        this.voterMap = new HashMap<>();
        for (Voter v : voterCollection) {
            this.voters.add(v);
            this.locations.add(v.location());
            this.voterMap.put(v.location(), v);
        }
    }

    /**
     * Creates a region defined by the specified set of locations
     * and specified set of voters.
     * If any voter is associated with a location not in the specified
     * set of locations, that voter is ignored (does not become a
     * member of this region).
     * @param locationSet the set of locations
     * @param voterSet the set of voters
     */
    public Region(final Set<Location> locationSet, final Set<Voter> voterSet) {
        this.locations = new TreeSet<>(locationSet);
        this.voters = new HashSet<>();
        this.voterMap = new HashMap<>();
        for (Voter v : voterSet) {
            if (this.locations.contains(v.location())) {
                this.voters.add(v);
                this.voterMap.put(v.location(), v);
            }
        }
    }

    /**
     * Accesses the number of locations in this region.
     * @return the number of locations
     */
    public int size() {
        return this.locations.size();
    }

    /**
     * Accesses the locations in this region.
     * @return the locations
     */
    public Collection<Location> locations() {
        return new TreeSet<Location>(this.locations);
    }

    /**
     * Accesses the number of voters in this region.
     * @return the number of voters
     */
    public int numberOfVoters() {
        return this.voters.size();
    }

    /**
     * Accesses the voters in this region.
     * @return the voters
     */
    public TreeSet<Voter> voters() {
        return new TreeSet<Voter>(this.voters);
    }
}
