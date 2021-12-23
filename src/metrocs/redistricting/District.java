package swdmt.redistricting;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
/**
 * A district is a contiguous group of locations.
 *
 * @author Dr. Jody Paul
 * @version 20191006
 */
public class District implements java.io.Serializable {
    /** Serialization version requirement. */
    private static final long serialVersionUID = 3L;

    /** The locations in this district. */
    private Set<Location> locations = new TreeSet<>();

    /**
     * Creates a district with no locations.
     */
    public District() {
        this.locations = new TreeSet<>();
    }

    /**
     * Creates a district comprised of the locations
     * specified by the parameter.
     * @param theLocations the locations comprising this district
     */
    public District(final Collection<Location> theLocations) {
        this();
        this.locations.addAll(theLocations);
    }

    /**
     * Accesses the number of locations in this district.
     * @return the number of locations
     */
    public int size() {
        return this.locations.size();
    }

    /**
     * Accesses the locations that comprise this district.
     * @return a collection of locations
     */
    public Collection<Location> locations() {
        return new TreeSet<Location>(this.locations);
    }

    /**
     * Verifies that all locations in this district are contiguous.
     * Contiguity is always true for districts of size 0 or 1.
     * @return true if all locations are contiguous; false otherwise
     */
     public boolean contiguityValid() {
               boolean contiguous = true;
               if (this.locations.size() > 1) {
                   Set<Location> contiguousLocations = new TreeSet<>();
                   Set<Location> filterdLoc = new TreeSet<>();
                   Location homeLoc = this.locations.iterator().next();
                   boolean filLocSet = true;
                   int conLocSize = contiguousLocations.size();
                   int thisLocSize = this.locations.size();
                   contiguousLocations.add(homeLoc);
                   contiguous = false;
                   while (conLocSize < thisLocSize && filLocSet) {
                       contiguousLocations.forEach(current ->
                                   this.locations
                                       .stream()
                                       .filter(current::isAdjacentTo)
                                       .filter(loc1 ->
                                               !contiguousLocations.contains(loc1))
                                               .forEach(filterdLoc::add));
                       if (filterdLoc.isEmpty()) {
                           filLocSet = false;
                       }
                       contiguousLocations.addAll(filterdLoc);
                       filterdLoc.clear();
                   }
                   if (this.locations.size() == contiguousLocations.size()) {
                       contiguous = true;
                   }
               }
               return contiguous;
           }

    @Override
    public String toString() {
        String retStr = "[District@" + this.hashCode() + "; ";
        retStr += "size: " + this.size();
        retStr += "]";
        return retStr;
    }
}
