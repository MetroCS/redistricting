package metrocs.redistricting;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
/**
 * Utility class for rendering regions and districts.
 * <p>Basic versions use ASCII text graphics.</p>
 * <p>Enhanced versions use Java graphical interfaces.</p>
 * <p>ASCII rendering retrieves specific affiliation display
 * character from the Party object itself.  Arbitrary content
 * is shown using '*'.</p>
 *
 * @author Dr. Jody Paul
 * @version 20211223.0
 */
public final class Renderer {
    /** Render of unknown cell content. */
    private static final String ANY = "*";
    /** Render of a corner. */
    private static final String CORNER = "+";
    /** Render of a vertical edge. */
    private static final String EDGE = "|";
    /** Render of the horizontal border of a cell. */
    private static final String CELL_BORDER = "---+";
    /** Render of the middle of an empty cell. */
    private static final String CELL_EMPTY_MIDDLE = "   |";
    /** Render of the middle of a cell with an associated resident. */
    private static final String CELL_ANY_MIDDLE = " " + ANY + " |";

    /**
     * Hide the constructor of this utility class.
     */
    private Renderer() { }

    /**
     * Renders a region as an ASCII-graphic string.
     * The region is depicted as cells within a rectangular grid,
     * with cells corresponding to locations in the region shown filled.
     * The origin is in the upper left corner.
     * @param showAffiliation true to show party affiliation; false otherwise
     * @param region the region to be rendered
     * @return the rendering of the given region
     * @TODO render non-rectangular regions
     */
    public static String renderAsASCII(final boolean showAffiliation,
                                       final Region region) {
        StringBuilder rendering = new StringBuilder();
        // Return immediately if region is null or has no locations.
        if (region == null || region.size() < 1) {
            return rendering.toString();
        }

        // Construct location:voter mapping.
        Map<Location, Voter> voterMap = new TreeMap<>();
        for (Voter vot : region.voters()) {
            voterMap.put(vot.location(), vot);
        }

        int numRows = 1 + maximumYOfLocations(region)
                        - minimumYOfLocations(region);
        int numCols = 1 + maximumXOfLocations(region)
                        - minimumXOfLocations(region);

        // Render a row border.
        StringBuilder horizontalBorder = new StringBuilder(CORNER);
        for (int i = 0; i < numCols; i++) {
            horizontalBorder.append(CELL_BORDER);
        }
        horizontalBorder.append("\n");

        // Render the region.
        Collection<Location> locs = region.locations();
        rendering = new StringBuilder(horizontalBorder);
        for (int r = 0; r < numRows; r++) {
            StringBuilder rowMiddle = new StringBuilder(EDGE);
            for (int c = 0; c < numCols; c++) {
                Location currentLoc = new Location(c, r);
                if (locs.contains(currentLoc)) {
                    if (showAffiliation) {
                        if (voterMap.keySet().contains(currentLoc)) {
                            rowMiddle.append(CELL_ANY_MIDDLE
                                             .replace('*',
                                                      voterMap
                                                      .get(currentLoc)
                                                      .affiliation()
                                                      .id()));
                        } else {
                            rowMiddle.append(CELL_ANY_MIDDLE);
                        }
                    } else {
                        rowMiddle.append(CELL_ANY_MIDDLE);
                    }
                } else {
                    rowMiddle.append(CELL_EMPTY_MIDDLE);
                }
            }
            rowMiddle.append("\n");
            (rendering.append(rowMiddle)).append(horizontalBorder);
        }

        return rendering.toString();
    }

    /**
     * Renders a region as an ASCII-graphic string.
     * Convenience method equivalent to
     * <code>renderAsASCII(false, region)</code>.
     * @param region the region to be rendered
     * @return the rendering of the given region
     */
    public static String renderAsASCII(final Region region) {
        return renderAsASCII(false, region);
    }


    /**
     * Renders a region and its districts as an ASCII-graphic string.
     * The origin is in the upper left corner.
     * @param showAffiliation true to show party affiliation; false otherwise
     * @param region the region to be rendered
     * @param districts the districts within the region
     * @return the rendering of the given region showing districts
     */
    public static String renderAsASCII(final boolean showAffiliation,
                                       final Region region,
                                       final Set<District> districts) {
        String renderedRegion = renderAsASCII(showAffiliation, region);
        char[] arrayRender = renderedRegion.toCharArray();

        int rowLength = renderedRegion.indexOf('\n') + 1;
        int colWidth = CELL_BORDER.length();

        // Remove borders between contiguous locations in a district.
        for (District d : districts) {
            for (Location l : d.locations()) {
                Set<Location> others = new TreeSet<Location>(d.locations());
                others.remove(l);
                for (Location o : others) {
                    if (l.isAdjacentTo(o)) {
                        Location lesser = l.compareTo(o) < 0 ? l : o;
                        if (l.yCoordinate() == o.yCoordinate()) {
                            // Remove border in same row.
                            int col = lesser.xCoordinate();
                            int row = lesser.yCoordinate();
                            int modX = ((1 + col) * colWidth);
                            int modY = (row * 2) + 1;
                            int offsetToRow = modY * rowLength;
                            arrayRender[offsetToRow + modX] = ' ';
                        } else if (l.xCoordinate() == o.xCoordinate()) {
                            // Remove border in same column.
                            int col = lesser.xCoordinate();
                            int row = lesser.yCoordinate();
                            int modX = (col * colWidth) + 1;
                            int modY = (row * 2) + 2;
                            int offsetToRow = modY * rowLength;
                            for (int i = 0; i < colWidth - 1; i++) {
                                arrayRender[offsetToRow + modX + i] = ' ';
                            }
                        }
                    }
                }
            }
        }
        return new String(arrayRender);
    }

    /**
     * Renders a region and its districts as an ASCII-graphic string.
     * Convenience method equivalent to
     * <code>renderAsASCII(false, region, districts)</code>.
     * @param region the region to be rendered
     * @param districts the districts within the region
     * @return the rendering of the given region showing districts
     */
    public static String renderAsASCII(final Region region,
                                       final Set<District> districts) {
        return renderAsASCII(false, region, districts);
    }


    /**
     * Determines the minimum x-coordinate value of a region's locations.
     * @param region the region to be interrogated
     * @return the minimum x-coordinate value
     */
    private static int minimumXOfLocations(final Region region) {
        Location locWithMinimumX = region.locations()
                .stream()
                .min((loc1, loc2) -> Integer.compare(loc1.xCoordinate(),
                                                     loc2.xCoordinate()))
                .orElse(null);
        return locWithMinimumX.xCoordinate();
    }

    /**
     * Determines the minimum y-coordinate value of a region's locations.
     * @param region the region to be interrogated
     * @return the minimum y-coordinate value
     */
    private static int minimumYOfLocations(final Region region) {
        Location locWithMinimumY = region.locations()
                .stream()
                .min((loc1, loc2) -> Integer.compare(loc1.yCoordinate(),
                                                     loc2.yCoordinate()))
                .orElse(null);
        return locWithMinimumY.yCoordinate();
    }

    /**
     * Determines the maximum x-coordinate value of a region's locations.
     * @param region the region to be interrogated
     * @return the maximum x-coordinate value
     */
    private static int maximumXOfLocations(final Region region) {
        Location locWithMaximumX = region.locations()
                .stream()
                .max((loc1, loc2) -> Integer.compare(loc1.xCoordinate(),
                                                     loc2.xCoordinate()))
                .orElse(null);
        return locWithMaximumX.xCoordinate();
    }

    /**
     * Determines the maximum y-coordinate value of a region's locations.
     * @param region the region to be interrogated
     * @return the maximum y-coordinate value
     */
    private static int maximumYOfLocations(final Region region) {
        Location locWithMaximumY = region.locations()
                .stream()
                .max((loc1, loc2) -> Integer.compare(loc1.yCoordinate(),
                                                     loc2.yCoordinate()))
                .orElse(null);
        return locWithMaximumY.yCoordinate();
    }
}
