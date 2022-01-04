package metrocs.redistricting;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
 * @author CS3250 Participants
 * @author Nicholas Matthews
 * @version 20220103.3
 */
public final class Redistrictor implements java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 4L;

    /** Region associated with this redistrictor. */
    private final Region region;

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
     * @param theRegion the region to be redistricted
     * @param numDistricts the number of districts for the region;
     *        defaults to 1 if value is less-than or equal to 0
     * @return a set of districts matching the parameters, if feasible;
     *         an empty set if no feasible solution is found.
     */
    public static Set<District> generateDistricts(final Region theRegion,
                                                  final int numDistricts) {
        Set<District> districts = new HashSet<District>();
        HashMap<Location, HashSet<Location>> graph
            = generateGraphFromRegion(theRegion);
        HashSet<Location> graphMembers = new HashSet<Location>();
        graphMembers.addAll(graph.keySet());
        try {
            HashSet<HashSet<Location>> locationSet
                = recursiveRedistrict(graph,
                                      graphMembers,
                                      graph.keySet().size() / numDistricts,
                                      numDistricts);
            for (HashSet<Location> element : locationSet) {
                districts.add(new District(element));
            }
        } catch (NoSuchElementException e) {
            districts.add(new District());
            return districts;
        }
        return districts;
    }


    /**
     * Utility: Apply a "snaking" algorithm to search for
     * a feasible redistricting solution.
     * Note that contiguity for non rectangluar regions is not considered!
     * @param theRegion the region to be redistricted
     * @param numDistricts the number of districts for the region;
     *        defaults to 1 if value is less-than or equal to 0
     * @return a set of districts matching the parameters, if feasible;
     *         an empty set if no feasible solution is found.
     */
    public static Set<District> generateDistrictsUsingSnake(
                                    final Region theRegion,
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

        // Create covering districts with the proper number of locations.
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

    /**
     * Recursive implementation of a redistricting algorithm.
     * <p>
     * Uses a graph to represent connections between locations,
     * thus any location which is connected to another is considered
     * adjacent and a contiguous path can be made between them.
     * </p>
     * <p>
     * Attempts to find a valid district using the flood method
     * and then will remove that district from the input graph
     * and call itself again on the remaining graph until no locations
     * remain and the desired number of districts has been found.
     * </p>
     * @param inputGraph A map representing connections between locations
     * @param inputSet A set that defines membership in the graph
     * @param districtSize The desired size of the districts
     * @param desiredDistricts The number of districts to be found
     * @return A set of sets of contiguous locations
     * @throws NoSuchElementException If no possible districtings can be found
     */
    private static HashSet<HashSet<Location>> recursiveRedistrict(
                        final HashMap<Location, HashSet<Location>> inputGraph,
                        final Set<Location> inputSet,
                        final int districtSize,
                        final int desiredDistricts)
            throws NoSuchElementException {
        if (inputSet.isEmpty() && desiredDistricts == 0) {
            return new HashSet<HashSet<Location>>();
        }
        if ((inputSet.isEmpty() && desiredDistricts != 0)
            || (desiredDistricts == 0 && !inputSet.isEmpty())) {
            throw new NoSuchElementException();
        }
        if (inputSet.size() > desiredDistricts * (districtSize + 1)) {
            throw new NoSuchElementException();
        }
        if (subDivideGraph(inputGraph, inputSet).size() > desiredDistricts) {
            throw new NoSuchElementException();
        }
        HashSet<Location> potentialDistrict = null;
        HashSet<Location> potentialStart = new HashSet<Location>();
        potentialStart.addAll(inputSet);
        for (int sizeDifference = 0; sizeDifference < 2; sizeDifference++) {
            for (int differencePass = 0; differencePass < 2; differencePass++) {
                for (Location startingLoc: potentialStart) {
                    try {
                        if (differencePass == 0) {
                            potentialDistrict = flood(
                                                 inputGraph,
                                                 inputSet,
                                                 startingLoc,
                                                 districtSize + sizeDifference);
                        } else {
                            if (districtSize - sizeDifference > 0) {
                                potentialDistrict = flood(
                                                     inputGraph,
                                                     inputSet,
                                                     startingLoc,
                                                 districtSize - sizeDifference);
                            } else {
                                potentialDistrict = flood(
                                                     inputGraph,
                                                     inputSet,
                                                     startingLoc,
                                                     1);
                            }
                        }
                        inputSet.removeAll(potentialDistrict);
                        try {
                            HashSet<HashSet<Location>> output
                                    = recursiveRedistrict(inputGraph,
                                                          inputSet,
                                                          districtSize,
                                                          desiredDistricts - 1);
                            output.add(potentialDistrict);
                            return output;
                        } catch (NoSuchElementException e) {
                            inputSet.addAll(potentialDistrict);
                        }
                    } catch (NoSuchElementException f) {
                        continue;
                    }
                }
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Creates a graph out of a given region,
     * using locations as vertices and connecting
     * locations to any other location for which
     * Location.isAdjacentTo is true.
     * <p>
     * Returns the graph in the form of a HashMap with locations
     * as keys and the set of locations they are connected to
     * as values.
     * </p>
     * @TODO Implement functionality as described
     * @param input The region to generate a graph from.
     * @return A HashMap of locations, with values of the locations
     *         they are connected to.
     */
    private static HashMap<Location, HashSet<Location>> generateGraphFromRegion(
                                                          final Region input) {
        HashMap<Location, HashSet<Location>> map = new HashMap<>();
        for (Location locA : input.locations()) {
            HashSet<Location> edges = new HashSet<Location>();
            for (Location locB: input.locations()) {
                if (locA.isAdjacentTo(locB)) {
                    edges.add(locB);
                }
            }
            map.put(locA, edges);
        }
        return map;
    }

    /**
     * Returns a set of all contiguous sets of locations
     * contained within a given graph.
     * Consequently, if the entire graph is contiguous
     * the returned set will only have one element,
     * which is the set of all locations in the graph.
     * <p>
     * In practice, inputSet represents a graph with the
     * same connections as specified in inputGraph, except
     * only connections that connect locations within
     * inputSet are considered valid.
     * </p>
     * @TODO Consider faster way to assign remaining set
     * @param inputGraph The graph of which inputSet represents a subgraph
     * @param inputSet The locations that defines a subgraph of inputGraph
     * @return A set containing all contiguous sets of locations within
     *         the graph represented by inputSet.
     */
    private static HashSet<HashSet<Location>> subDivideGraph(
                       final HashMap<Location, HashSet<Location>> inputGraph,
                       final Set<Location> inputSet) {
        if (inputSet.isEmpty()) {
            return new HashSet<HashSet<Location>>();
        }
        Location start = inputSet.iterator().next();
        HashSet<Location> searched = new HashSet<Location>();
        HashSet<Location> searchList = new HashSet<Location>();
        searched.add(start);
        searchList.add(start);
        while (!searchList.isEmpty()) {
            Location pointer = searchList.iterator().next();
            for (Location connection: inputGraph.get(pointer)) {
                if (!(searched.contains(connection))
                      && inputSet.contains(connection)) {
                    searched.add(connection);
                    searchList.add(connection);
                }
            }
            searchList.remove(pointer);
        }
        HashSet<Location> remainingSet = new HashSet<Location>();
        remainingSet.addAll(inputSet);
        remainingSet.removeAll(searched);
        HashSet<HashSet<Location>> output = subDivideGraph(inputGraph,
                                                           remainingSet);
        output.add(searched);
        return output;
    }

    /**
     * Finds a possible configuration of locations of a specified
     * size by flooding out from an initial location until a specified
     * size is reached. Uses a graph to define connections to navigate.
     * Throws a NoSuchElementException if no flood pattern of a specific
     * length can be found.
     * @param inputGraph The map to use as reference for connections
     * @param inputSet The set which defines membership of the graph
     * @param start The location to start the search from
     * @param size The desired size of the returned set
     * @return A contiguous set of locations of a specified size
     * @throws NoSuchElementException if no valid pattern found
     */
    private static HashSet<Location> flood(
                       final HashMap<Location, HashSet<Location>> inputGraph,
                       final Set<Location> inputSet,
                       final Location start,
                       final int size)
        throws NoSuchElementException {
        if (size == 0) {
            return new HashSet<Location>();
        }
        ArrayList<Location> locationQueue = new ArrayList<Location>();
        HashSet<Location> outputSet = new HashSet<Location>();
        locationQueue.add(start);
        while (outputSet.size() < size && !locationQueue.isEmpty()) {
            Location currentLocation = locationQueue.remove(0);
            if (!outputSet.contains(currentLocation)) {
                outputSet.add(currentLocation);
                for (Location neighbor: inputGraph.get(currentLocation)) {
                    if (!outputSet.contains(neighbor)
                          && inputSet.contains(neighbor)) {
                        locationQueue.add(neighbor);
                    }
                }
            }
        }
        if (outputSet.size() < size) {
            throw new NoSuchElementException();
        }
        return outputSet;
    }
}
