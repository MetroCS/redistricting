package swdmt.redistricting;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import javax.swing.JOptionPane;
/**
 * I/O driver for Redistricting application.
 *
 * @author Dr. Jody Paul
 * @version 20191130
 */
public final class RedistrictingDriver {
    /** Display name of this project. */
    private static final String PROJECT_NAME = "Redistricting";
    /** Default number of rows in new region. */
    private static final int DEFAULT_NUM_ROWS = 5;
    /** Default number of columns in new region. */
    private static final int DEFAULT_NUM_COLUMNS = 5;
    /** Default number of locations in new region. */
    private static final int DEFAULT_NUM_LOCATIONS = 25;
    /** Default number of districts. */
    private static final int DEFAULT_NUM_DISTRICTS = 5;

    /** Hidden constructor. */
    private RedistrictingDriver() { }

    /**
     * Main driver for redistricting application.
     * @param args ignored
     */
    public static void main(final String[] args) {
        int rows = DEFAULT_NUM_ROWS,
            columns = DEFAULT_NUM_COLUMNS,
            numLocations = DEFAULT_NUM_LOCATIONS,
            numDistricts = DEFAULT_NUM_DISTRICTS;
        rows = Integer.parseInt(askUser("Please enter the number of rows "));
        columns = Integer.parseInt(
                           askUser("Please enter the number of columns "));
        numDistricts = Integer.parseInt(
                           askUser("Please enter the number of districts "));
        System.out.println("Size of region: " + rows + " x " + columns);
        System.out.println("Number of districts: " + numDistricts);
        numLocations = rows * columns;
        // Create region of appropriate size.
        Region workingRegion = new Region(rows * columns);
        System.out.println("Created region " + workingRegion + " with "
                           + numLocations + " locations");
        System.out.println(Renderer.renderAsASCII(false, workingRegion));
        // Create one voter for each location.
        Set<Voter> voterSet = new HashSet<Voter>();
        Random rng = new Random();
        for (Location loc : workingRegion.locations()) {
            voterSet.add(new Voter(
                    rng.nextInt(2) == 0 ? Party.PARTY0 : Party.PARTY1, loc));
        }
        // Create region with specified voters
        Region region = new Region((Set<Location>) workingRegion.locations(),
                                    voterSet);
        System.out.println(
                "Assigned voters with arbitrary parties to locations.");
        System.out.println(Renderer.renderAsASCII(true, region));
        Set<District> districts
                = Redistrictor.generateDistricts(region, numDistricts);
        System.out.println("Created set of districts " + districts);
    }

    /**
     * Prompt the user to enter a response at the console.
     * @param prompt the prompt to provide to the user
     * @return string entered by user
     */
    private static String askUser(final String prompt) {
        Scanner console = new Scanner(System.in);
        System.out.print(prompt);
        return console.next();
    }

    /**
     * Prompt the user to enter a response using GUI.
     * @param prompt the prompt to provide to the user
     * @return string entered by user
     */
    private static String askUserGUI(final String prompt) {
        String response = (String) JOptionPane.showInputDialog(
                null,
                prompt,
                PROJECT_NAME,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
            );
        if (null == response) {
            System.exit(-1);
        }
        return response;
    }

}
