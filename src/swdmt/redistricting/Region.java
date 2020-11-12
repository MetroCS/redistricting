package swdmt.redistricting;
import java.util.Map;
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
 * @version 20191128
 */
public class Region implements java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 4L;

    /** The locations comprising this region. */
    private Set<Location> locations = new TreeSet<>();

    /** The voters comprising this region. */
    private Set<Voter> voters = new HashSet<>();

    /** The association of locations with voters. */
    private Map<Location, Voter> voterMap = new HashMap<>();

    /**
     * Computes a default length of the side of the grid containing this region.
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
    public Set<Voter> voters() {
        return this.voters;
    }
}
