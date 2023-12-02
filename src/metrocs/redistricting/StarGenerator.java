package metrocs.redistricting;

import java.util.ArrayList;

/**
 * Generates a list of possible districts of a specified size
 * originating from an given origin point.
 *
 * @author Jessica
 * @author CS3250 Participants
 * @version 20211223.1
 */
public final class StarGenerator {
  /**
   * Returns all collections of Locations of a specified length
   * that originate from the origin Location(0,0).
   * @param n length of patterns
   * @return generated pattern
   */
  public static ArrayList<ArrayList<Location>> generatePattern(final int n) {
    ArrayList<ArrayList<Location>> builtPatterns =
        new ArrayList<ArrayList<Location>>();
    if (n == 0) {
    	return builtPatterns;
    }
    ArrayList<Location> visited = new ArrayList<Location>();
    Location currentCoord = new Location(0, 0);

    traverse(new ArrayList<Location>(), currentCoord, n, builtPatterns);

    return builtPatterns;
  }

  /**
  * Will recursively build all possible size and patterns starting from
  * the origin.
  * @param visited all locations already visited
  * @param current current location on grid during traversal
  * @param moves the number of remaining traversals
  * @param builtPatterns all patterns that are fully built
  */
  private static void traverse(final ArrayList<Location> visited,
                               final Location current,
                               final int moves,
                               final ArrayList<ArrayList<Location>>
                                     builtPatterns) {
    int remainingMoves = moves - 1;

    ArrayList<Location> newChain = new ArrayList<Location>(visited);
    newChain.add(current);

    if (remainingMoves == 0) {
      builtPatterns.add(newChain);
      return;
    }

    Location northCoordinate = new Location(current.xCoordinate(),
                                            current.yCoordinate() - 1);
    if (!contains(visited, northCoordinate)) {
      traverse(newChain, northCoordinate, remainingMoves, builtPatterns);
    }

    Location southCoordinate = new Location(current.xCoordinate(),
                                            current.yCoordinate() + 1);
    if (!contains(visited, southCoordinate)) {
      traverse(newChain, southCoordinate, remainingMoves, builtPatterns);
    }

    Location eastCoordinate = new Location(current.xCoordinate() + 1,
                                           current.yCoordinate());
    if (!contains(visited, eastCoordinate)) {
      traverse(newChain, eastCoordinate, remainingMoves, builtPatterns);
    }

    Location westCoordinate = new Location(current.xCoordinate() - 1,
                                           current.yCoordinate());
    if (!contains(visited, westCoordinate)) {
      traverse(newChain, westCoordinate, remainingMoves, builtPatterns);
    }
  }

  /**
  * Checks if location is within the Location List.
  * @param coordinates location within List
  * @param coordinate location to check
  * @return Whether the given location lives in the List
  */
  private static boolean contains(final ArrayList<Location> coordinates,
                                  final Location coordinate) {
    for (Location cord : coordinates) {
      if (cord.equals(coordinate)) {
        return true;
      }
    }
    return false;
  }

  /**
  * Constructor is private,  methods are static only and this class
  * should never be instantiated.
  */
  private StarGenerator() {
  }
}
