package swdmt.redistricting;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
/**
 * Utility class for rendering regions and districts.
 * <p>Basic versions use ASCII text graphics.</p>
 * <p>Enhanced versions use Java graphical interfaces.</p>
 * <p>ASCII rendering retrieves specific affiliation display
 * character from the Party object itself.  Arbitrary content
 * is shown using '*'.</p>
 *
 * @author Dr. Jody Paul
 * @version 20191128.1
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
     * @param showAffiliation true to show party affiliation; false otherwise
     * @param region the region to be rendered
     * @return the rendering of the given region
     * @TODO render non-rectangular regions
     */
    public static String renderAsASCII(final boolean showAffiliation,
                                       final Region region) {
        String rendering = "";
        // Return immediately if region is null or has no locations.
        if (region == null || region.size() < 1) {
            return rendering;
        }

        // Determine locations at x-coordinate and y-coordinate boundaries.
        Collection<Location> locs = region.locations();
        Location locWithMinimumX = locs
                .stream()
                .min((loc1, loc2) -> Integer.compare(loc1.xCoordinate(),
                                                     loc2.xCoordinate()))
                .orElse(null);
        Location locWithMinimumY = locs
                .stream()
                .min((loc1, loc2) -> Integer.compare(loc1.yCoordinate(),
                                                     loc2.yCoordinate()))
                .orElse(null);
        Location locWithMaximumX = locs
                .stream()
                .max((loc1, loc2) -> Integer.compare(loc1.xCoordinate(),
                                                     loc2.xCoordinate()))
                .orElse(null);
        Location locWithMaximumY = locs
                .stream()
                .max((loc1, loc2) -> Integer.compare(loc1.yCoordinate(),
                                                     loc2.yCoordinate()))
                .orElse(null);

        // Construct location:voter mapping.
        Map<Location, Voter> voterMap = new TreeMap<>();
        for (Voter vot : region.voters()) {
            voterMap.put(vot.location(), vot);
        }

        int numRows = 1 + locWithMaximumY.yCoordinate()
                        - locWithMinimumY.yCoordinate();
        int numCols = 1 + locWithMaximumX.xCoordinate()
                        - locWithMinimumX.xCoordinate();

        // Render a row border.
        String horizontalBorder = CORNER;
        for (int i = 0; i < numCols; i++) {
            horizontalBorder += CELL_BORDER;
        }
        horizontalBorder += "\n";

        // Render the region.
        rendering = horizontalBorder;
        for (int r = 0; r < numRows; r++) {
            String rowMiddle = EDGE;
            for (int c = 0; c < numCols; c++) {
                Location currentLoc = new Location(c, r);
                if (locs.contains(currentLoc)) {
                    if (showAffiliation) {
                        if (voterMap.keySet().contains(currentLoc)) {
                            rowMiddle += CELL_ANY_MIDDLE
                                         .replace('*',
                                                  voterMap
                                                  .get(currentLoc)
                                                  .affiliation()
                                                  .id());
                        } else {
                            rowMiddle += CELL_ANY_MIDDLE;
                        }
                    } else {
                        rowMiddle += CELL_ANY_MIDDLE;
                    }
                } else {
                    rowMiddle += CELL_EMPTY_MIDDLE;
                }
            }
            rowMiddle += "\n";
            rendering += rowMiddle + horizontalBorder;
        }

        return rendering;
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
}
