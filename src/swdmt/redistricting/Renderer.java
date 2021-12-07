package swdmt.redistricting;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Comparator;
import java.util.Set;

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

    /**
     * Displays the voter map within a JFrame window.
     *
     * @param region   Region object defining the area being divided into districts
     * @param district Set of District objects typically provided by
     *                 the Redistrictor class generateDistricts() method
     * @author Henry Callin, Joe Gunter
     */
    public static void renderAsGUI(Region region, Set<District> district) {
        //Finds the number of rows and columns(in case they change in the future).
        Collection<Location> locs = region.locations();
        Location locWithMinimumX = locs
                .stream()
                .min(Comparator.comparingInt(Location::xCoordinate))
                .orElse(null);
        Location locWithMinimumY = locs
                .stream()
                .min(Comparator.comparingInt(Location::yCoordinate))
                .orElse(null);
        Location locWithMaximumX = locs
                .stream()
                .max(Comparator.comparingInt(Location::xCoordinate))
                .orElse(null);
        Location locWithMaximumY = locs
                .stream()
                .max(Comparator.comparingInt(Location::yCoordinate))
                .orElse(null);


        // Construct location:voter mapping.
        Map<Location, Voter> voterMap = new TreeMap<>();
        for (Voter vot : region.voters()) {
            voterMap.put(vot.location(), vot);
        }

        //Assigns number of rows and columns.
        assert locWithMaximumY != null;
        int numRows = 1 + locWithMaximumY.yCoordinate()
                - locWithMinimumY.yCoordinate();
        int numCols = 1 + locWithMaximumX.xCoordinate()
                - locWithMinimumX.xCoordinate();

        //Define Initial Window Size based on number of rows and cols
        int windowInitHeight = numRows * 60;
        int windowInitWidth = numCols * 60;

        //Creates an array to represent the region then loops across the array to
        //define which voter is assigned to which district, numbering them accordingly.
        Integer[][] districtArray = new Integer[numRows][numCols];
        int districtNum = 1;
        for (District dist : district) {
            for (Location locations : dist.locations()) {
                districtArray[locations.xCoordinate()][locations.yCoordinate()] = districtNum;
            }
            districtNum++;
        }

        //Initializes JFrame display and squares.
        JFrame frame = new JFrame();
        GridLayout display = new GridLayout(numRows, numCols);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(display);

        //Assigns red or blue backgrounds to the squares depending on
        //Voter affiliation and displays a number indicating
        //to which district a voter belongs.
        frame = combineFrameParts(frame, numRows, numCols, voterMap, districtArray);
        frame.setSize(windowInitWidth, windowInitHeight);
        frame.setVisible(true);
    }

    /**
     * Helper method to combine all relevant data within GenerateAsGUI
     * into a JFrame for display.
     *
     * @param frame         JFrame object being built upon
     * @param numRows       number of rows in the region
     * @param numCols       number of columns in the region
     * @param voterMap      map defining voter affiliation of each location in the region
     * @param districtArray 2d array containing district information for the region
     * @return JFrame with all voters, voter affiliations, and districts assigned
     */
    private static JFrame combineFrameParts(JFrame frame, int numRows, int numCols, Map<Location, Voter> voterMap, Integer[][] districtArray) {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Location currentLoc = new Location(c, r);
                if (voterMap.containsKey(currentLoc)) {
                    JButton b = new JButton(districtArray[r][c].toString());
                    b.setFont(new Font("Arial", Font.BOLD, 30));
                    b.setForeground(Color.WHITE);
                    if (voterMap.get(currentLoc).affiliation().id() == '1') {
                        b.setBackground(Color.RED);
                    } else {
                        b.setBackground(Color.BLUE);
                    }
                    b.setOpaque(true);
                    frame.add(b);
                }
            }
        }
        return frame;
    }
}
