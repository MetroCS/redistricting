package swdmt.redistricting;

import java.util.ArrayList;

/**
 *
 * AllDistrictGen will generate all possible districts within a
 *    region.
 *
 *
 * @author Jessica Trujillo
 * @version 1.0
 */
public final class AllDistrictGen {
  /**
  * Generates all possible districts in a given grid
  * that are of a specific size.
  * @param gridWidth width of the grid
  * @param gridHeight height of grid
  * @param districtSize size of a single district
  * @return all possible districts of size n in the area: gridWidth x gridHeight
  */
  public static ArrayList<District> generateDistricts(final int gridWidth,
                                                      final int gridHeight,
                                                      final int districtSize) {
     ArrayList<ArrayList<Location>> starPattern =
                                   StarGenerator.generatePattern(districtSize);
     ArrayList<District> currentDistricts = new ArrayList<District>();
     for (int x = 0; x < gridWidth; x++) {
       for (int y = 0; y < gridHeight; y++) {
         for (ArrayList<Location> pattern : starPattern) {
           ArrayList<Location> transformed = transformPattern(pattern, x, y);
           if (!isDistrictInBounds(transformed, gridWidth, gridHeight)) {
             continue;
           }

           District transformedAsDistrict = new District(transformed);
           if (!contains(currentDistricts, transformedAsDistrict)) {
             currentDistricts.add(transformedAsDistrict);
           }
         }
       }
     }
     return currentDistricts;
  }

  /**
  * Returns Y/N on whether or not a similar district
  * (district with same locations)
  * exists inside the district collection passed in.
  * @param districts list of districts
  * @param district the district to look for inside districts
  * @return whether or not a similar district was in the districts list
  */
  private static boolean contains(final ArrayList<District> districts,
                                  final District district) {
    for (District d : districts) {
      if (equals(d, district)) {
        return true;
      }
    }
    return false;
  }

  /**
  * checks whether two districts have the same coordinates.
  * @param districtA first district to compare
  * @param districtB second district to compare
  * @return Whether or not districtA has the same Coordinates as districtB
  */
  private static boolean equals(final District districtA,
                                final District districtB) {
    if (districtA.size() != districtB.size()) {
      return false;
    }

    for (Location coordA : districtA.locations()) {
      boolean anyMatch = false;
      for (Location coordB : districtB.locations()) {
        if (coordA.equals(coordB)) {
          anyMatch = true;
        }
      }
      if (!anyMatch) {
        return false;
      }
    }
    return true;
  }

  /**
  * Creates a new pattern based off of the passed in pattern,
  * that is translated by X and Y.
  * @param pattern starting at the origin
  * @param offsetX the X translation value
  * @param offsetY the Y translation value
  * @return pattern translated by X and Y
  */
  private static ArrayList<Location> transformPattern(
                                             final ArrayList<Location> pattern,
                                             final int offsetX,
                                             final int offsetY) {
    ArrayList<Location> newPattern = new ArrayList<Location>();

    for (Location coord : pattern) {
      Location transformedCoord = new Location(coord.xCoordinate() + offsetX,
                                               coord.yCoordinate() + offsetY);
      newPattern.add(transformedCoord);
    }
    return newPattern;
  }

  /**
  * Will check if the district is within a grid defined by the origin stretching
  * to grid width and height.
  * @param district the district to check
  * @param gridWidth the upper-bound of X axis
  * @param gridHeight the upper-bound of Y axis
  * @return Whether district is in bounds or not
  */
  private static boolean isDistrictInBounds(final ArrayList<Location> district,
                                            final int gridWidth,
                                            final int gridHeight) {
    for (Location cord : district) {
      if (cord.xCoordinate() < 0) {
        return false;
      }
      if (cord.yCoordinate() < 0) {
        return false;
      }
      if (cord.xCoordinate() >= gridWidth) {
        return false;
      }
      if (cord.yCoordinate() >= gridHeight) {
        return false;
      }
    }
    return true;
  }

  /**
  * Constructor is private,  methods are static only and this class
  * should never be instantiated.
  */
  private AllDistrictGen() {
  }
}
