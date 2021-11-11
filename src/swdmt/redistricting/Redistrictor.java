package swdmt.redistricting;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * A redistrictor attempts to determine a set of districts
 * for a given region under specified constraints.
 * Typical constraints may include:
 * achieving a specified number of districts,
 * achieving the same number of voters per district,
 * creating intra-district parity by party,
 * creating region-level parity by party,
 * favoring a party at region-level.
 *
 * Most functionality is available via utility methods.
 *
 * @author Dr. Jody Paul
 * @version 20191201
 */
public final class Redistrictor implements java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 3L;

    /** Region associated with this redistrictor. */
    private Region region;

    /**
     * Establishes a specific region as associated with
     * the redistrictor.
     * @param theRegion region associated with this redistrictor
     * @throws illegalArgumentException if the region is null
     */
    public Redistrictor(final Region theRegion) {
        if (null == theRegion) {
            throw new IllegalArgumentException(
                    "Cannot associate null region with new Redistrictor");
        }
        this.region = theRegion;
    }

    /**
     * Accesses this redistrictor's region.
     * @return the region
     */
    public Region region() {
        return this.region;
    }

    /**
     * Utility: Apply a generate-and-test algorithm to search for
     * any feasible redistricting solution.
     * @TODO Contiguity for non rectangluar districts are NOT yet considered!
     * @param theRegion the region to be redistricted
     * @param numDistricts the number of districts for the region;
     *        defaults to 1 if value is less-than or equal to 0
     * @return a set of districts matching the parameters, if feasible;
     *         an empty set if no feasible solution is found.
     */
    public static Set<District> generateDistricts(final Region theRegion,
                                                  final int numDistricts) {
        Set<District> districts = new HashSet<District>();
        List<List<Location>> districtLocs = new ArrayList<List<Location>>();
        int numberOfDistricts = (numDistricts < 1) ? 1 : numDistricts;
        int minimumNumberOfVotersPerDistrict
                = theRegion.numberOfVoters() / numDistricts;
        int numberOfAugmentedDistricts
                = theRegion.numberOfVoters() % numDistricts;
        Iterator<Location> locit = theRegion.locations().iterator();

        Location[] snakingLocations =
          new Location[theRegion.locations().size()];
        for (int i = 0; i < theRegion.locations().size(); i++) {
          snakingLocations[i] = locit.next();
        }

        Arrays.sort(snakingLocations, new SnakingLocationComparer());

        int currentLocation = 0;

        // Create covering districts with the proper
        // number of locations.
        // TODO: Contiguity for non rectangluar districts
        // are NOT yet considered.
        for (int i = 0; i < numberOfDistricts; i++) {
            List<Location> locList = new ArrayList<Location>();
            for (int vi = 0; vi < minimumNumberOfVotersPerDistrict; vi++) {
              locList.add(snakingLocations[currentLocation++]);
            }
            if (i < numberOfAugmentedDistricts) {
              locList.add(snakingLocations[currentLocation++]);
            }

            districtLocs.add(new ArrayList<Location>(locList));
        }

        for (List<Location> locs : districtLocs) {
            districts.add(new District(locs));
        }
        return districts;
    }

    /**
     * Utility: Generates all possible districts of the
     * specified size from a given region.
     * If the region is smaller than the specified size,
     * then a single district is returned.
     * Otherwise, a set is created that contains all 
     * districts of the specified size.
     * @param theRegion the region
     * @param districtSize the size of the districts
     * @return a set of all districts of the specified size
     */
    public static Set<District> allDistrictsOfSpecificSize(
                                    final Region theRegion,
                                    final int districtSize) {
        Set<District> districts = new HashSet<District>();
        if (districtSize > 0 && theRegion.size() > 0) {
            if (theRegion.size() <= districtSize) {
                districts.add(new District(theRegion.locations()));
            } else if (districtSize == 1) {
                for (Location loc : theRegion.locations()) {
                    List<Location> locList = new ArrayList<Location>(1);
                    locList.add(loc);
                    districts.add(new District(locList));
                }
            } else {
              int size = theRegion.sideSize();
              ArrayList<District> allDistricts =
                AllDistrictGen.generateDistricts(size,
                                                 size,
                                                 districtSize);
              for (District d : allDistricts) {
                districts.add(d);
              }
            }
        }
        return districts;
    }

    /**
     * Utility: Iterator over all districts of the specified size
     * for a given region.
     * @param theRegion the region
     * @param districtSize the size of the districts
     * @return a set of all districts of the specified size
     */
    public static Iterator<District> allDistrictsOfSpecificSizeIterator(
                                    final Region theRegion,
                                    final int districtSize) {
        return allDistrictsOfSpecificSize(theRegion, districtSize).iterator();
    }
}
