**MetroCS/redistricting**
# Create and Display a Region (Example)

This example demonstrates creating and displaying a region with parameters entered at the console.

Each location in the region is associated with a voter whose party affiliation is arbitrarily assigned.

A default redistricting algorithm is used to create the specified number of districts.

### Source Code
```java
// Example use of redistricting package utilities.
import swdmt.redistricting.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Scanner;
/**
 * Demonstration of redistricting package utilities from MetroCS/redistricting.
 * @version 20211203.2
 */
public class Example1 {

    public static void main(final String[] args) {
        int rows, columns, numLocations, numDistricts;
        Scanner console = new Scanner(System.in);

        System.out.print("Enter the number of rows: ");
        rows = Integer.parseInt(console.next());
        System.out.print("Enter the number of columns: ");
        columns = Integer.parseInt(console.next());
        System.out.print("Enter the number of districts: ");
        numDistricts = Integer.parseInt(console.next());
        numLocations = rows * columns;

        // Create a region of specified size to establish locations.
        Region demoRegion = new Region(rows * columns);

        // Create one voter with arbitrary party for each location.
        Set<Voter> voterSet = new HashSet<Voter>();
        Random rng = new Random();
        int numberOfParties = Party.values().length;
        for (Location loc : demoRegion.locations()) {
            voterSet.add(new Voter(Party.values()[rng.nextInt(numberOfParties)],
                                   loc));
        }

        // Create a region with the established voter set.
        demoRegion = new Region((Set<Location>) demoRegion.locations(),
                                voterSet);

        // Render voter-filled region to console.
        System.out.println("Rendering voter-identified region with "
                           + demoRegion.locations().size()
                           + " locations.");
        System.out.print(Renderer.renderAsASCII(true, demoRegion));
        System.out.print("Key: ");
        for (Party p : Party.values()) {
            System.out.print(p.id() + "=" + p + "  ");
        }
        System.out.println();

        // Redistrict the region.
        Set<District> districts
                = Redistrictor.generateDistricts(demoRegion, numDistricts);
        System.out.println("Created set of districts " + districts);
    }
}
```
### Sample Run (Transcript)

```
% **javac Example1.java**
% **java Example1**
Enter the number of rows: **7**
Enter the number of columns: **7**
Enter the number of districts: **3**
Rendering voter-identified region with 49 locations.
+---+---+---+---+---+---+---+
| T | 0 | T | 0 | ? | 0 | 0 |
+---+---+---+---+---+---+---+
| 1 | U | T | ? | U | U | U |
+---+---+---+---+---+---+---+
| T | 0 | 1 | T | 0 | 0 | U |
+---+---+---+---+---+---+---+
| U | U | T | U | U | 1 | U |
+---+---+---+---+---+---+---+
| U | 0 | 1 | U | 0 | T | ? |
+---+---+---+---+---+---+---+
| 0 | T | T | 0 | ? | ? | 0 |
+---+---+---+---+---+---+---+
| ? | U | ? | 0 | 0 | 1 | ? |
+---+---+---+---+---+---+---+
Key: ?=NONE  U=UNAFFILIATED  0=PARTY0  1=PARTY1  T=THIRDPARTY  
Created set of districts [[District@731395981; size: 17], [District@1196765369; size: 16], [District@486898233; size: 16]]
```

_Example - Create and Display a Region_ (v1.0)

MetroCS/redistricting: [Website](https://metrocs.github.io/redistricting/) | [Repository](https://github.com/MetroCS/redistricting)
