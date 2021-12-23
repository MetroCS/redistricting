package metrocs.redistricting;

import java.util.Comparator;

/**
 * Snaking Location will compare locations in a snaking fashion
 * meaning if all locations are in a grid any sequential block of locations
 * read will be contiguous.
 *
 * @author Jessica Trujillo
 * @author Wiwi Veratiwi
 * @author Mike Walker
 * @author Zach Morlan
 * @author Fred Buker
 * @version 20191201.1
 */
public class SnakingLocationComparer implements Comparator<Location> {
  /**
  * comparing each location to one another to ensure contiguity.
  *
  * @param locationA the first location to be compared
  * @param locationB the second location to be compared
  * @return 1 if greater, -1 if less than item being compared to, 0 if equal
  */
  @Override
  public int compare(final Location locationA, final Location locationB) {
    if (locationA.xCoordinate() > locationB.xCoordinate()) {
      return 1;
    }
    if (locationA.xCoordinate() < locationB.xCoordinate()) {
      return -1;
    }
    int x = locationA.xCoordinate();
    if (locationA.yCoordinate() == locationB.yCoordinate()) {
      return 0;
    }
    if (x % 2 == 0) {
      if (locationA.yCoordinate() < locationB.yCoordinate()) {
        return -1;
      }
      return 1;
    }
    if (locationA.yCoordinate() < locationB.yCoordinate()) {
      return 1;
    }
    return -1;
  }
}
